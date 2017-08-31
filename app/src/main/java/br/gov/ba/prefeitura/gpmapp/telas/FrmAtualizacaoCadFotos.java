package br.gov.ba.prefeitura.gpmapp.telas;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Pair;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.ca.mas.core.MobileSso;
import com.ca.mas.core.MobileSsoFactory;
import com.ca.mas.foundation.MAS;
import com.ca.mas.foundation.MASRequest;
import com.ca.mas.foundation.MASRequestBody;
import com.ca.mas.foundation.MASResponse;
import com.ca.mas.foundation.MASResponseBody;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Calendar;
import br.gov.ba.prefeitura.gpmapp.R;
import br.gov.ba.prefeitura.gpmapp.adapters.AdapterFotos;
import br.gov.ba.prefeitura.gpmapp.base.ActivityBase;
import br.gov.ba.prefeitura.gpmapp.util.Apoio;
import br.gov.ba.prefeitura.gpmapp.util.DialogAlerta;
import br.gov.ba.prefeitura.gpmapp.util.IRetornoMASCallbackString;
import br.gov.ba.prefeitura.gpmapp.util.LogTrace;
import br.gov.ba.prefeitura.gpmapp.util.RetornoMASCallbackString;
import br.gov.ba.prefeitura.gpmapp.util.RetornoMASConnectionListener;

/**
 * Classe FrmAtualizacaoCadFotos
 */
public class FrmAtualizacaoCadFotos extends ActivityBase implements View.OnClickListener, DialogInterface.OnClickListener, IRetornoMASCallbackString, Runnable
{
    //Controles da classe
    private CoordinatorLayout coordinatorLayoutAtualizacaoCadFotos = null;
    private Toolbar toolbar = null;
    private Button cmdGaleria = null;
    private Button cmdCapturar = null;
    private RecyclerView rcvFotos = null;
    private Button cmdFinalizar = null;
    private AlertDialog dlgSair = null;
    private AlertDialog dlgExcluir = null;
    private AlertDialog dlgSucesso = null;
    private ProgressDialog progressDialogAtualizacaoCadastralFotos = null;

    //Variaveis da classe
    private int iTipoComunicacao = 0;
    private AdapterFotos adapterFotos = null;
    private ArrayList<String> arrFotos = null;
    private boolean bRetornoGaleria = false;
    private Uri uriCapturaImage = null;
    private String sProtocolo = "";
    private int iNumFotoEnvio = 0;
    private boolean bUltimaFoto = false;
    private int iPosicao = 0;

    @Override
    protected void onCreate(Bundle bundleSavedInstanceState)
    {
        // Carrega a tela
        setContentView(R.layout.frm_atualizacao_cad_fotos);

        //chama os métodos automáticos da classe pai
        super.onCreate(bundleSavedInstanceState);
    }

    @Override
    public void iniciaControles() throws Exception
    {
        Drawable drawableArrow = null;

        // Obtem os controles
        coordinatorLayoutAtualizacaoCadFotos = (CoordinatorLayout) findViewById(R.id.coordinatorLayoutAtualizacaoCadFotos);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        cmdGaleria = (Button) findViewById(R.id.cmdGaleria);
        cmdCapturar = (Button) findViewById(R.id.cmdCapturar);
        rcvFotos = (RecyclerView) findViewById(R.id.rcvFotos);
        cmdFinalizar = (Button) findViewById(R.id.cmdFinalizar);

        //Muda a cor da flecha padrão do Android para branco
        drawableArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_material);
        drawableArrow.setColorFilter(ContextCompat.getColor(this, R.color.branco), PorterDuff.Mode.SRC_ATOP);

        //Seta o navigation item
        toolbar.setNavigationIcon(drawableArrow);

        //Troca a cor do botao de voltar
        toolbar.setNavigationContentDescription(Apoio.TOOLBAR_VOLTAR);

        //Seta o listener dos controles
        cmdGaleria.setOnClickListener(this);
        cmdCapturar.setOnClickListener(this);
        cmdFinalizar.setOnClickListener(this);

        //Coloca a toolbar como actionbar e adiciona as opções de menu
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public void carregaDados() throws Exception
    {
        //Limpa as fotos
        limpaFotos();
    }

    @Override
    public void obtemParametros() throws Exception
    {
        super.obtemParametros();

        //Obtem parâmetros
        sProtocolo = getIntent().getStringExtra(Apoio.TELA_FOTOS_PROTOCOLO);
    }

    /**
     * Preenche a lista de fotos
     */
    public void preencheListaFotos() throws Exception
    {
        File fileLista = null;
        File[] fileArqs = null;
        int iCont = 0;

        //instancia o array
        arrFotos = new ArrayList<String>();

        // Crio um file do caminho informado.
        fileLista = new File(Apoio.getPathImagensSDCard(this));

        // lista os arquivo contidos na pasta.
        fileArqs = fileLista.listFiles();

        // Varre a lista de arquivos
        for (iCont = 0; iCont < fileArqs.length; iCont++)
        {
            // Copiamos apenas se for um arquivo
            if ( fileArqs[iCont].isFile() )
            {
                //Adiciona no array o nome
                arrFotos.add(fileArqs[iCont].getName());
            }
        }

        //seta o adapter e carrega os itens
        adapterFotos = new AdapterFotos(this, this, arrFotos);
        rcvFotos.setLayoutManager(new GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false));
        rcvFotos.setAdapter(adapterFotos);
        rcvFotos.setNestedScrollingEnabled(false);

        //Atualize a lista
        adapterFotos.notifyDataSetChanged();
    }

    /**
     * Limpa as fotos
     */
    public void limpaFotos() throws Exception
    {
        File fileLista = null;
        File[] fileArqs = null;
        File fileArq = null;
        int iCont = 0;

        // Crio um file do caminho informado.
        fileLista = new File(Apoio.getPathImagensSDCard(this));

        // lista os arquivo contidos na pasta.
        fileArqs = fileLista.listFiles();

        // Varre a lista de arquivos
        for (iCont = 0; iCont < fileArqs.length; iCont++)
        {
            // Copiamos apenas se for um arquivo
            if ( fileArqs[iCont].isFile() )
            {
                //Monta o path e exclui
                fileArq = new File(fileArqs[iCont].getAbsolutePath());
                fileArq.delete();
            }
        }
    }

    /**
     * Captura a imagem
     */
    private void capturaImagem() throws Exception
    {
        PackageManager packageManager = null;
        Calendar calendar = null;
        File file = null;
        Intent intent = null;

        //verifco se esta vindo pela galeria ou para capturar a imagem
        if( !bRetornoGaleria )
        {
            //instancia o package Maneger para fazer a busca se existe uma camera no aparelho
            packageManager = this.getPackageManager();

            //verifica se o aparelho contem camera
            if (!packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA))
            {
                DialogAlerta.show(this, getString(R.string.msg_atualizacao_cad_fotos_sem_camera), getString(R.string.atencao), getString(R.string.ok));
                return;
            }

            //cria um diretorio  para imagem e nomeia a imagem  com a data
            calendar = Calendar.getInstance();
            file = new File(Apoio.getPathImagensSDCard(this), (calendar.getTimeInMillis() + ".jpg"));

            //Verifica se o arquivo existe, se não existir
            if (file.exists())
            {
                //Deleta o arquivo
                file.delete();
            }

            //cria um novo arquivo
            file.createNewFile();

            //Captura a imagem
            uriCapturaImage = Uri.fromFile(file);
            intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uriCapturaImage);
            this.startActivityForResult(intent, Apoio.RETORNO_TELA_CAMERA);
        }
        else
        {
            intent = new Intent (Intent.ACTION_PICK);
            intent.setType("image/");
            startActivityForResult(Intent.createChooser(intent, getString(R.string.msg_atualizacao_cad_fotos_selecione_imagem)), Apoio.RETORNO_TELA_CAMERA);
        }
    }

    /**
     * Grava a imagem da galeria no arquivo da pasta de imagens
     */
    private void gravaImagemArquivoPasta(Uri uriImagem) throws Exception
    {
        Calendar calendar = null;
        File file = null;
        FileOutputStream fileOutputStream = null;
        AssetFileDescriptor fileDescriptor = null;
        Bitmap bitmap = null;
        BitmapFactory.Options optionsBitMap = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        byte[] bDadosImagem = null;

        //cria um diretorio  para imagem e nomeia a imagem  com a data
        calendar = Calendar.getInstance();
        file = new File(Apoio.getPathImagensSDCard(this), (calendar.getTimeInMillis() + ".jpg"));

        //Verifica se o arquivo existe, se não existir
        if (file.exists())
        {
            //Deleta o arquivo
            file.delete();
        }

        //cria um novo arquivo
        file.createNewFile();

        //instancia a DAO
        optionsBitMap = new BitmapFactory.Options();

        //pega o tamanho da imagem
        optionsBitMap.inSampleSize = 4;

        //obtem o tamanho da imagem e faz a leitura da imagem
        fileDescriptor = getContentResolver().openAssetFileDescriptor(uriImagem, "r");

        //obtema imagem
        bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor.getFileDescriptor(), null, optionsBitMap);

        //Gera o tipo da imagem em PNG
        byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);

        //faz a leitura da imagem
        bDadosImagem = byteArrayOutputStream.toByteArray();

        //Grava os dados no arquivo
        fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(bDadosImagem);
        fileOutputStream.flush();
        fileOutputStream.close();

        //Preenche a lista de fotos
        preencheListaFotos();
    }

    /**
     * Inicia o envio de fotos
     */
    private void iniciaEnvioFotos() throws Exception
    {
        Snackbar snackbarErro = null;

        //Se não tiver fotos
        if ( arrFotos == null )
        {
            //Monta snackbar com erro
            snackbarErro = Snackbar.make(coordinatorLayoutAtualizacaoCadFotos, getString(R.string.msg_atualizacao_cad_fotos_sem_fotos), Snackbar.LENGTH_LONG);
            snackbarErro.show();
            return;
        }

        //Se não tiver fotos
        if ( arrFotos.size() <= 0 )
        {
            //Monta snackbar com erro
            snackbarErro = Snackbar.make(coordinatorLayoutAtualizacaoCadFotos, getString(R.string.msg_atualizacao_cad_fotos_sem_fotos), Snackbar.LENGTH_LONG);
            snackbarErro.show();
            return;
        }

        //Se tiver mais do que 15 fotos
        if ( arrFotos.size() >= 15 )
        {
            //Monta snackbar com erro
            snackbarErro = Snackbar.make(coordinatorLayoutAtualizacaoCadFotos, getString(R.string.msg_atualizacao_cad_fotos_limite_fotos), Snackbar.LENGTH_LONG);
            snackbarErro.show();
            return;
        }

        //Zera o contador e flag de envio da última foto
        iNumFotoEnvio = 0;
        bUltimaFoto = false;

        //Cria o dialogo e exibe mensagem
        progressDialogAtualizacaoCadastralFotos = Apoio.criarProgressDialog(this);
        Apoio.progressDialogMensagem(progressDialogAtualizacaoCadastralFotos, getString(R.string.msg_atualizacao_cad_fotos_enviando));

        //Chama o metódo de envio
        enviaFotosServidor();
    }

    /**
     * Envia as fotos para o servidor
     */
    private void enviaFotosServidor() throws Exception
    {
        MobileSso mobileSso = null;
        URI uri = null;
        MASRequest masrequestDados = null;
        MASRequest.MASRequestBuilder builder = null;
        MASRequestBody masrequestBody = null;
        String sPathFoto = "";
        File fileArq = null;
        ArrayList<Pair<String, String>> arrParamEnvio = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        Bitmap bitmapImagem = null;
        byte[] bImagem = null;
        byte[] bArrDadosBase64 = null;
        String sImgBase64 = "";

        //Se o indice é o último no array
        if ( iNumFotoEnvio == arrFotos.size()-1 )
        {
            //Seta como última foto
            bUltimaFoto = true;
        }

        //Obtem a foto do array e monta o path
        sPathFoto = Apoio.getPathImagensSDCard(this) + "/" + arrFotos.get(iNumFotoEnvio);

        //Verifica se existe
        fileArq = new File(sPathFoto);
        if ( !fileArq.exists() )
        {
            //Se não for a última
            if ( !bUltimaFoto )
            {
                //Passa para a próxima
                iNumFotoEnvio++;
                enviaFotosServidor();
            }
            else
            {
                // fecha o dialog
                Apoio.fecharProgressDialog(progressDialogAtualizacaoCadastralFotos);
            }

            return;
        }

        //Define o tipo da comunicação
        iTipoComunicacao = Apoio.TIPO_COMUNICACAO_DADOS_CADASTRAIS_ENVIO_FOTOS;

        //Obtem a instancia da comunicação via SSO
        mobileSso = MobileSsoFactory.getInstance(this);

        //Realiza a chamada da comunicação na URL de obtenção das chaves de permissão
        uri = mobileSso.getURI(getString(R.string.url_conexao_gateway) + "governo/v1/servidores/eu/comprovantes");
        builder = new MASRequest.MASRequestBuilder(uri);
        builder.header("Content-Type", "application/x-www-form-urlencoded");

        //Obtem a imagem
        bitmapImagem = BitmapFactory.decodeFile(sPathFoto);

        //Gera o tipo da imagem em PNG
        byteArrayOutputStream = new ByteArrayOutputStream();
        bitmapImagem.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);

        //faz a leitura da imagem
        bImagem = byteArrayOutputStream.toByteArray();

        //Converte a imagem para base64
        bArrDadosBase64 = Base64.encode(bImagem, 0);
        sImgBase64 = new String(bArrDadosBase64);

        //Adiciona os parametros de envio
        arrParamEnvio = new ArrayList<Pair<String, String>>();
        arrParamEnvio.add(new Pair<String, String>("protocolo", sProtocolo));
        arrParamEnvio.add(new Pair<String, String>("documento", sImgBase64));
        arrParamEnvio.add(new Pair<String, String>("nomeImagem", arrFotos.get(iNumFotoEnvio)));
        arrParamEnvio.add(new Pair<String, String>("ultimo", (bUltimaFoto ? "S" : "N")));

        //Monta o body codificado para URL
        masrequestBody = MASRequestBody.urlEncodedFormBody(arrParamEnvio);

        //Constroi a URL de envio e faz a chamada
        masrequestDados = builder.responseBody(MASResponseBody.stringBody()).post(masrequestBody).connectionListener(new RetornoMASConnectionListener()).build();
        MAS.invoke(masrequestDados, new RetornoMASCallbackString(this));
    }

    /**
     * Remove a foto do array e da pasta
     */
    private void removeFoto() throws Exception
    {
        String sPathFoto = "";
        File fileArq = null;

        //Monta o path da foto
        sPathFoto = Apoio.getPathImagensSDCard(this) + "/" + arrFotos.get(iPosicao);
        fileArq = new File(sPathFoto);

        //Se existir, elimina
        if ( fileArq.exists() )
        {
            //deleta o arquivo
            fileArq.delete();
        }

        //Remove do array
        arrFotos.remove(iPosicao);

        //Preenche a lista
        preencheListaFotos();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem mnuItem)
    {
        // Instanciando os itens passados no menu inflate
        super.onOptionsItemSelected(mnuItem);

        try
        {
            // Pego o id da opcao selecionada
            switch (mnuItem.getItemId())
            {
                //Verifica se clicou no botao de return da actionbar/toolbar
                case android.R.id.home:
                {
                    //Cria um dialogAlert com as opções
                    dlgSair = Apoio.criarAlertDialog(this, getString(R.string.sim), getString(R.string.nao), getString(R.string.atencao), getString(R.string.msg_atualizacao_cad_fotos_sair), this);
                    dlgSair.show();
                    Apoio.trocaCoresBotoesDialog(dlgSair, this);
                    break;
                }
            }
        }
        catch (Exception err)
        {
            LogTrace.escreve(Apoio.localErro(getClass(), err), Apoio.getPathLogsSDCard(this), Apoio.getArqErr());
            DialogAlerta.show(this, Apoio.getMsgErr(getClass(), err), getString(R.string.atencao), getString(R.string.ok));
        }

        return true;
    }

    @Override
    public void onClick(View view)
    {
        try
        {
            //Quando apertar o botão finalizar
            if (view == cmdFinalizar)
            {
                //Inicia o envio de fotos
                iniciaEnvioFotos();
            }
            //Quando apertar o botão galeria
            else if (view == cmdGaleria)
            {
                //seto a flag como true
                bRetornoGaleria = true;

                //abro a intent da galeria para seleção da imagem
                capturaImagem();
            }
            //Quando apertar o botão capturar
            else if (view == cmdCapturar)
            {
                //seto a flag como falso
                bRetornoGaleria = false;

                //caputura a imagem da receita
                capturaImagem();
            }
        }
        catch (Exception err)
        {
            LogTrace.escreve(Apoio.localErro(getClass(), err), Apoio.getPathLogsSDCard(this), Apoio.getArqErr());
            DialogAlerta.show(this, Apoio.getMsgErr(getClass(), err), getString(R.string.atencao), getString(R.string.ok));
        }
    }

    @Override
    protected void onActivityResult(int iRequestCode, int iResultado, Intent intent)
    {
        try
        {
            //verifica se esta vindo da camera o resultado
            if (iRequestCode == Apoio.RETORNO_TELA_CAMERA)
            {
                //Verifica se o resultado for OK
                if (iResultado == RESULT_OK)
                {
                    //verifico se o retorna é pela galeria
                    if( bRetornoGaleria )
                    {
                        //Grava a imagem no arquivo da pasta
                        gravaImagemArquivoPasta(intent.getData());
                    }
                    else
                    {
                        //Preenche a lista de fotos
                        preencheListaFotos();
                    }
                }
            }
        }
        catch (Exception err)
        {
            LogTrace.escreve(Apoio.localErro(getClass(), err), Apoio.getPathLogsSDCard(this), Apoio.getArqErr());
            DialogAlerta.show(this, Apoio.getMsgErr(getClass(), err), getString(R.string.atencao), getString(R.string.ok));
        }
    }

    @Override
    public void onClick(DialogInterface dialog, int iEscolha)
    {
        try
        {
            //Se for o dialog de sair
            if ( dialog == dlgSair )
            {
                //Se tocou no botão sim
                if (iEscolha == DialogInterface.BUTTON_POSITIVE)
                {
                    //Limpa fotos
                    limpaFotos();

                    // Seta o resultado e finaliza
                    Apoio.finalizaActivity(this, RESULT_CANCELED);
                }
            }
            //Se for o dialog de excluir
            else if ( dialog == dlgExcluir )
            {
                //Se tocou no botão sim
                if (iEscolha == DialogInterface.BUTTON_POSITIVE)
                {
                    //Remove a foto do array
                    removeFoto();
                }
            }
            //Se for o dialog de sucesso
            else if ( dialog == dlgSucesso )
            {
                //Finaliza para voltar para o login
                Apoio.finalizaActivity(this, RESULT_OK);
            }
        }
        catch(Exception err)
        {
            LogTrace.escreve(Apoio.localErro(getClass(), err), Apoio.getPathLogsSDCard(this), Apoio.getArqErr());
            DialogAlerta.show(this, Apoio.getMsgErr(getClass(), err), getString(R.string.atencao), getString(R.string.ok));
        }
    }

    @Override
    public void onSuccess(MASResponse<String> masresponseResultado)
    {
        try
        {
            //Se for o tipo de comunicacao de envio de fotos
            if ( iTipoComunicacao == Apoio.TIPO_COMUNICACAO_DADOS_CADASTRAIS_ENVIO_FOTOS )
            {
                //Se for a última
                if ( bUltimaFoto )
                {
                    // fecha o dialog
                    Apoio.fecharProgressDialog(progressDialogAtualizacaoCadastralFotos);

                    //Chama o preenchimento para rodar em thread, pois no retorno do sucesso, não conseguimos acessar os objetos de tela
                    this.runOnUiThread(this);
                }
                else
                {
                    //Faz o envio da próxima foto
                    iNumFotoEnvio++;
                    enviaFotosServidor();
                }
            }
        }
        catch (Exception err)
        {
            LogTrace.escreve(Apoio.localErro(getClass(), err), Apoio.getPathLogsSDCard(this), Apoio.getArqErr());
            Toast.makeText(this, Apoio.getMsgErr(this.getClass(), err), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onError(Throwable throwable)
    {
        Snackbar snackbarErro = null;

        try
        {
            // fecha o dialog
            Apoio.fecharProgressDialog(progressDialogAtualizacaoCadastralFotos);

            //Loga o erro retornado pela CA
            LogTrace.escreve(Apoio.localErro(getClass(), throwable), Apoio.getPathLogsSDCard(this), Apoio.getArqErr());

            //Se for o tipo de comunicacao de envio de fotos
            if ( iTipoComunicacao == Apoio.TIPO_COMUNICACAO_DADOS_CADASTRAIS_ENVIO_FOTOS )
            {
                //Monta snackbar com erro
                snackbarErro = Snackbar.make(coordinatorLayoutAtualizacaoCadFotos, getString(R.string.msg_atualizacao_cad_impossivel_enviar_fotos), Snackbar.LENGTH_LONG);
                snackbarErro.show();
            }
        }
        catch(Exception err)
        {
            LogTrace.escreve(Apoio.localErro(getClass(), err), Apoio.getPathLogsSDCard(this), Apoio.getArqErr());
            DialogAlerta.show(this, Apoio.getMsgErr(getClass(), err), getString(R.string.atencao), getString(R.string.ok));
        }
    }

    @Override
    public void onReceiveData(Class classe, int iId, boolean bResultado, Object... oObjetos) throws Exception
    {
        try
        {
            //Zera a posicao
            iPosicao = -1;

            //Se for o adapter de fotos
            if ( classe == AdapterFotos.class )
            {
                //Se existir o objeto
                if (oObjetos != null)
                {
                    iPosicao = (int) oObjetos[0];
                }

                //Se tiver uma posição
                if (iPosicao >= 0)
                {
                    //Se for a exclusão
                    if ( iId == R.id.rtlExcluir )
                    {
                        //Cria um dialogAlert com as opções
                        dlgExcluir = Apoio.criarAlertDialog(this, getString(R.string.sim), getString(R.string.nao), getString(R.string.atencao), getString(R.string.msg_atualizacao_cad_fotos_excluir), this);
                        dlgExcluir.show();
                        Apoio.trocaCoresBotoesDialog(dlgExcluir, this);
                    }
                }
            }
        }
        catch (Exception err)
        {
            LogTrace.escreve(Apoio.localErro(getClass(), err), Apoio.getPathLogsSDCard(this), Apoio.getArqErr());
            DialogAlerta.show(this, Apoio.getMsgErr(getClass(), err), getString(R.string.atencao), getString(R.string.ok));
        }
    }

    @Override
    public void run()
    {
        try
        {
            //Se for o tipo de comunicacao de envio de fotos
            if ( iTipoComunicacao == Apoio.TIPO_COMUNICACAO_DADOS_CADASTRAIS_ENVIO_FOTOS )
            {
                //Cria um dialogAlert com as opções
                dlgSucesso = Apoio.criarAlertDialog(this, getString(R.string.ok), null, getString(R.string.sucesso), getString(R.string.msg_atualizacao_cad_sucesso), this);
                dlgSucesso.show();
                Apoio.trocaCoresBotoesDialog(dlgSucesso, this);
            }
        }
        catch(Exception err)
        {
            LogTrace.escreve(Apoio.localErro(getClass(), err), Apoio.getPathLogsSDCard(this), Apoio.getArqErr());
            DialogAlerta.show(this, Apoio.getMsgErr(getClass(), err), getString(R.string.atencao), getString(R.string.ok));
        }
    }
}