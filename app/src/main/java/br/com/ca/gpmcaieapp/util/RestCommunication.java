package br.com.ca.gpmcaieapp.util;

import android.content.Context;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

public class RestCommunication
{
    // Enderecos das tabelas rest
    public static final String RESTPOST = "POST";
    public static final String RESTDELETE = "DELETE";
    public static final String RESTGET = "GET";
    public static final String RESTPUT = "PUT";

    /**
     * Efetua a operacao solicitada com o servidor REST
     */
    public static RestReturn efetuaOperacaoRest(String sCaminhoRest, String sOperacao, String sBody, int iTimeOut, boolean bAutorizacao, String sChaveAutorizacao, Context context) throws  Exception {


        return (efetuaOperacaoRest(sCaminhoRest, sOperacao, sBody, iTimeOut, bAutorizacao, sChaveAutorizacao, context, "application/x-www-form-urlencoded"));

    }


    public static RestReturn efetuaOperacaoRest(String sCaminhoRest, String sOperacao, String sBody, int iTimeOut, boolean bAutorizacao, String sChaveAutorizacao, Context context, String sContentType) throws  Exception
    {
        URL url = null;
        HttpsURLConnection httpsConn = null;
        OutputStream streamGravacao = null;
        InputStream streamLeitura = null;
        ByteArrayOutputStream streamBinaria = null;
        RestReturn restReturn = null;
        byte[] btDados = null;
        int iBytesRec = 0;
        int iCertificadoRaw = 0;

        try
        {
            //*************************************************************
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            // From https://www.washington.edu/itconnect/security/ca/load-der.crt

            //Pego o id
            iCertificadoRaw = context.getResources().getIdentifier("apigatewaygpmsolutions", "raw", context.getPackageName());
            InputStream caInput = new BufferedInputStream(context.getResources().openRawResource(iCertificadoRaw));
            Certificate ca;
            try {
                ca = cf.generateCertificate(caInput);
                System.out.println("ca=" + ((X509Certificate) ca).getSubjectDN());
            } finally {
                caInput.close();
            }

            // create a KeyStore containing our trusted CAs
            String keyStoreType = KeyStore.getDefaultType();
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", ca);

            // create a TrustManager that trusts the CAs in our KeyStore
            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(keyStore);

            // create an SSLContext that uses our TrustManager
            SSLContext sslcontext = SSLContext.getInstance("TLS");
            sslcontext.init(null, tmf.getTrustManagers(), null);
            //***********************************************************************

            // Instancia a conexao http e o objeto de url
            sCaminhoRest = sCaminhoRest.replace(" ", "%20");

            //Monta a URL e conecta
            url = new URL(sCaminhoRest);
            httpsConn = (HttpsURLConnection) url.openConnection();
            httpsConn.setSSLSocketFactory(sslcontext.getSocketFactory());

            //Monta os parâmetros para envio
            httpsConn.setRequestMethod(sOperacao);
            httpsConn.setUseCaches(false);
            httpsConn.setAllowUserInteraction(false);
            httpsConn.setRequestProperty("Content-Type", sContentType);

                //Se for para realizar a autorização
                if ( bAutorizacao )
                {
                httpsConn.setRequestProperty("Authorization", "Bearer " + sChaveAutorizacao);
            }

            //Seta o timeout
            httpsConn.setReadTimeout(iTimeOut*1000);
            httpsConn.setConnectTimeout(iTimeOut*1000);

            // Caso a requisicao seja diferente de um GET
            if ( sOperacao.equals(RestCommunication.RESTPOST) || sOperacao.equals(RestCommunication.RESTPUT) )
            {
                //Seta que tem dados de envio
                httpsConn.setDoOutput(true);

                // Escreve apenas se houver conteudo para ele
                if ( !sBody.equals("") )
                {
                    // Obtem os dados binarios do que foi enviado ao body
                    btDados = sBody.getBytes(Charset.forName("UTF-8"));

                    // Seta o cabecalho com o tamanho do que sera enviado
                    httpsConn.setRequestProperty("Content-Length", String.valueOf(btDados.length));

                    // Obtem a stream de gravacao do http, grava os dados,
                    // 'commita' a gravacao dos dados, e fecha a stream
                    streamGravacao = httpsConn.getOutputStream();
                    streamGravacao.write(btDados);
                    streamGravacao.flush();
                    streamGravacao.close();
                }
                // Se nao tem nada pra enviar
                else
                {
                    httpsConn.setRequestProperty("Content-Length", "0");
                }
            }

            // Se recebemos sucesso do servidor
            if ( httpsConn.getResponseCode() < 400 )
            {
                // Recria a stream binaria assionada a stream de recebimento dos dados
                streamBinaria = new ByteArrayOutputStream();
                streamGravacao = new DataOutputStream(streamBinaria);

                // Recupera a stream de leitura binaria
                streamLeitura = httpsConn.getInputStream();

                // Aloca memoria pra receber os dados
                btDados = new byte[1024];

                // Entra em laco
                do
                {
                    // Le dados do tamanho do buffer
                    iBytesRec = streamLeitura.read(btDados, 0, btDados.length);

                    // Se nao tem mais anda pra ler, saimos
                    if ( iBytesRec <= 0 )
                    {
                        break;
                    }

                    // Grava os bytes lidos, na quantidade lida, na stream de gravacao
                    streamGravacao.write(btDados, 0, iBytesRec);
                }
                while ( true );

                // 'Commita' os dados na stream
                streamGravacao.flush();

                // Recupera os bytes recebidos
                btDados = streamBinaria.toByteArray();

                // Fecha as streams
                Apoio.closeStream(streamLeitura);
                Apoio.closeStream(streamBinaria);
                Apoio.closeStream(streamGravacao);

                //Anula streams
                streamLeitura = null;
                streamBinaria = null;
                streamGravacao = null;

                // Instancia o retorno que sera enviado ao client
                restReturn = new RestReturn(httpsConn.getResponseCode(), "", new String(btDados, "ISO_8859-1"));
            }
            else if ( httpsConn.getResponseCode() == 500 )
            {
                // Instancia o retorno que sera enviado ao client
                restReturn = new RestReturn(httpsConn.getResponseCode(), "Erro interno no servidor!", null);
            }
            else if ( httpsConn.getResponseCode() == 400 )
            {
                // Instancia o retorno que sera enviado ao client
                restReturn = new RestReturn(httpsConn.getResponseCode(), "Caminho não encontrado!", null);
            }
            else if ( httpsConn.getResponseCode() == 403 )
            {
                // Instancia o retorno que sera enviado ao client
                restReturn = new RestReturn(httpsConn.getResponseCode(), "Acesso no autorizado!", null);
            }
            else if ( httpsConn.getResponseCode() == 404 )
            {
                // Instancia o retorno que sera enviado ao client
                restReturn = new RestReturn(httpsConn.getResponseCode(), "Caminho não encontrado!", null);
            }
            else
            {
                // Instancia o retorno que sera enviado ao client
                restReturn = new RestReturn(httpsConn.getResponseCode(), "Erro ao tentar comunicar com o servidor!", null);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally
        {
            // Fecha as streams
            Apoio.closeStream(streamLeitura);
            Apoio.closeStream(streamBinaria);
            Apoio.closeStream(streamGravacao);

            //Fecha a conexão
            if ( httpsConn != null )
            {
                httpsConn.disconnect();
            }
        }

        // Retorna os dados recebidos do servidor
        return restReturn;
    }
}