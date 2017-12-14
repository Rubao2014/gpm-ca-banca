package br.gov.al.itec.gpmapp.util;

import android.text.format.DateFormat;
import java.io.File;
import java.io.FileWriter;
import java.util.Calendar;

/**
 * Log do sistema
 */
public class LogTrace
{
    
    /**
     * Loga o erro ocorrido no sistema, nao deve ser chamado em projeto javaSe
     * pois possui chamada a classes especificas do android.
     * @param sTexto Texto a ser logado
     * @param sPath Path do arquivo de log.
     * @param sArquivo  Nome do arquivo.
     */
    public static void escreve(String sTexto, String sPath, String sArquivo)
    {
        FileWriter fwArq = null;
        File fArq = null;
        File fDir = null;
        Calendar calSis = null;
        String sAuxi = "";
        StringBuilder sbAuxi = new StringBuilder();

        try
        {
            //Monta a data
            calSis = Calendar.getInstance();
            
            //Formata data e hora usando DateFormat, classe do android
            //kk e para hora em formato 24 horas
            sbAuxi.append(DateFormat.format("dd/MM/yyyy kk:mm:ss", calSis).toString());
            sbAuxi.append(":");

            //Recupera os milisegundos utilizando Calendar, e formata usando StringBuilder, 
            //porque alem de ser mais rapido, a classe DateFormat nao tem pattern para milisegundos
            sbAuxi.append(calSis.get(Calendar.MILLISECOND));
            sbAuxi.append(" ");
            
            // Concatena os dados, e insere uma quebra de linha
            sbAuxi.append(sTexto);
            
            // Guarda a composicao do texto na variavel
            sAuxi = sbAuxi.toString();
            
            // Se nao existe o arquivo de script de dados, porque continuar
            fDir = new File(sPath);
            fArq = new File(fDir, sArquivo);

            //Se não existir, cria
            if ( !fArq.exists() )
            {
                fArq.createNewFile();
                Apoio.chmod(fArq, 0777);
            }

            //Abre o arquivo
            fwArq = new FileWriter(fArq, true);

            //Grava dados
            fwArq.write(sAuxi);
            fwArq.write("\r\n");

        	// Fecha o arquivo
        	fwArq.flush();
            fwArq.close();
        }
        catch (Exception err)
        {
        }
    } 
}