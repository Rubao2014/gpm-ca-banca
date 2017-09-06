package br.gov.ba.prefeitura.gpmapp.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.ParseException;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import com.ca.mas.foundation.MASUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import br.gov.ba.prefeitura.gpmapp.R;
import br.gov.ba.prefeitura.gpmapp.models.Combos;

/**
 * Classe de Apoio
 */
public class Apoio
{
    // Constantes de preferencias
    public static final String PREFS_MANTER_CONECTADO = "prefs_manter_conectado";
    public static final String PREFS_TOKEN = "prefs_token";

    // Constantes de preferencias de cadastro
    public static final String PREFS_CADASTRO_NOME = "prefs_cadastro_nome";
    public static final String PREFS_CADASTRO_CPF = "prefs_cadastro_cpf";
    public static final String PREFS_CADASTRO_DATA_NASC = "prefs_cadastro_data_nasc";
    public static final String PREFS_CADASTRO_CELULAR = "prefs_cadastro_celular";
    public static final String PREFS_CADASTRO_EMAIL = "prefs_cadastro_email";
    public static final String PREFS_CADASTRO_SENHA = "prefs_cadastro_senha";
    public static final String PREFS_CADASTRO_SEXO = "prefs_cadastro_sexo";


    //Constantes de preferencia Sefaz
    public static final String PREFS_SEFAZ_DATA_VENCIMENTO = "prefs_sefaz_data_vencimento";
    public static final String PREFS_SEFAZ_CODIGO_TRIBUTARIO = "prefs_sefaz_codigo_tributario";
    public static final String PREFS_SEFAZ_CNPJ_ORGAO = "prefs_sefaz_CNPJ_orgao";
    public static final String PREFS_SEFAZ_RAZAO_SOCIAL = "prefs_sefaz_razao_social";
    public static final String PREFS_SEFAZ_TIPO = "prefs_sefaz_tipo";
    public static final String PREFS_SEFAZ_NUMERO_DOC = "prefs_sefaz_numero_doc";
    public static final String PREFS_SEFAZ_VALOR_DOC = "prefs_sefaz_valor_doc";
    public static final String PREFS_SEFAZ_MULTA_DOC = "prefs_sefaz_multa_doc";
    public static final String PREFS_SEFAZ_JUROS_DOC = "prefs_sefaz_juros_doc";
    public static final String PREFS_SEFAZ_CODIGO_BARRAS = "prefs_sefaz_codigo_barras";
    public static final String PREFS_SEFAZ_MENSAGEM_BOLETO = "prefs_sefaz_mensagem_boleto";
    public static final String PREFS_SEFAZ_MENSAGEM_RETORNO_SERVICO = "prefs_sefaz_mensagem_retorno_servico";


    //Constantes de preferencia Seduc
    public static final String PREFS_SEDUC_INSCRICAO = "prefs_seduc_inscricao";

    // Contastantes referente a data
    public static final int DATA_COMPLETA = 1;
    public static final int DATA_HORA = 2;
    public static final int DATA_COMPLETA_HORA = 3;
    public static final int DATA_INVERTIDA = 4;
    public static final int DATA_COMPLETA_HORA_COM_SEGUNDOS = 5;
    public static final int DATA_COMPLETA_HORA_COM_SEGUNDOS_BANCO = 6;

    // Contastantes referentes a chave do app
    public static final String CLIENT_KEY = "4f7da722-23ac-4c88-983d-8e57e02459c4";
    public static final String CLIENT_SECRET = "42d46c1d-259a-4f01-88c1-2a29cc8fce67";

    //Constantes para comunicação da tela
    public static final int TIPO_COMUNICACAO_LOGOUT = 0;
    public static final int TIPO_COMUNICACAO_SERVICOS = 1;
    public static final int TIPO_COMUNICACAO_MEUS_DADOS = 2;
    public static final int TIPO_COMUNICACAO_MEUS_DADOS_EDITAR = 3;
    public static final int TIPO_COMUNICACAO_MEUS_DADOS_TROCAR_SENHA = 4;
    public static final int TIPO_COMUNICACAO_TOKEN = 5;
    public static final int TIPO_COMUNICACAO_DADOS_CARGA = 6;
    public static final int DADOS_CADASTRAIS_GRAVACAO = 7;
    public static final int TIPO_COMUNICACAO_DADOS_CADASTRAIS_ENVIO_FOTOS = 8;
    public static final int TIPO_COMUNICACAO_CARTA_SERVICOS = 9;
    public static final int TIPO_COMUNICACAO_EDUCACAO = 10;

    //Constantes para timeout de comunicação
    public static final int TIME_OUT_COMUNICACAO = 30;

    //Constantes do menu
    public static final int MENU_MEUS_DADOS = 0;
    //public static final int MENU_CONFIGURACOES = 1;
    //public static final int MENU_ULTIMOS_ACESSOS = 2;
    //public static final int MENU_SUPORTE = 3;
    public static final int MENU_SAIR = 1;

    //Define  o voltar do toolbar
    public static final String TOOLBAR_VOLTAR = "back";

    //Requisições de permissão
    public static final int REQUISICAO_PERMISSAO_GRAVACAO_SD_CARD = 1;

    //Define para o tempo de duração do splash
    public static final int TEMPO_DISPLAY_SPLASH = 1500;

    //Constante para as preferencias
    public static final String PREFS_NAME = "preferencias";

    //Constantes da tela de sexo
    public static final int TELA_CADASTRO_SEXO_MASCULINO = 1;
    public static final int TELA_CADASTRO_SEXO_FEMININO = 2;

    //Constantes de retorno de tela
    public static final int RETORNO_TELA_DETRAN = 1;
    public static final int RETORNO_TELA_SEFAZ = 11;
    public static final int RETORNO_TELA_SEDUC = 12;
    public static final int RETORNO_TELA_ESQUECI_MINHA_SENHA = 2;
    public static final int RETORNO_TELA_DASHBOARD = 3;
    public static final int RETORNO_TELA_ATUALIZACAO_CURSOS_CADASTRO = 4;
    public static final int RETORNO_TELA_ATUALIZACAO_DEPENDENTES_CADASTRO = 5;
    public static final int RETORNO_TELA_LOGIN = 6;
    public static final int RETORNO_TELA_MEUS_DADOS_EDITAR = 7;
    public static final int RETORNO_TELA_MEUS_DADOS_TROCAR_SENHA = 8;
    public static final int RETORNO_TELA_CAMERA = 9;
    public static final int RETORNO_TELA_ATUALIZACAO_CADASTRAL_FOTOS = 10;
    public static final int RETORNO_TELA_CARTA_DE_SERVICOS = 11;

    //Constantes das guias dos atualização cadastral
    public static final int ATUALIZACAO_CAD_QTD_ABAS = 6;
    public static final int ATUALIZACAO_CAD_TAB_DOCUMENTOS = 0;
    public static final int ATUALIZACAO_CAD_TAB_ENDERECO = 1;
    public static final int ATUALIZACAO_CAD_TAB_DEPENDENTES = 2;
    public static final int ATUALIZACAO_CAD_TAB_CURSOS = 3;
    public static final int ATUALIZACAO_CAD_TAB_VINCULO = 4;
    public static final int ATUALIZACAO_CAD_TAB_QUESTIONARIO = 5;

    //Constantes das guias dos atualização cadastral
    public static final int DETRAN_ABAS = 1;
    public static final int DETRAN_PONTUACAO = 0;
    public static final int DETRAN_DADOS_VEICULO = 1;


    //Constantes com parametros para a tela de sucesso
    public static final String TELA_SUCESSO_TIPO_MENSAGEM = "TELA_SUCESSO_TIPO_MENSAGEM";
    public static final String TELA_SUCESSO_MENSAGEM = "TELA_SUCESSO_MENSAGEM";
    public static final String TELA_SUCESSO_TITULO = "TELA_SUCESSO_TITULO";

    //Constantes com parametros para a tela de meus dados editar
    public static final String TELA_MEUS_DADOS_EDITAR = "TELA_MEUS_DADOS_EDITAR";
    public static final String TELA_CURSOS_OBJETO_ALTERACAO = "TELA_CURSOS_OBJETO_ALTERACAO";
    public static final String TELA_CURSOS_PRIMEIRA_FORMACAO = "TELA_CURSOS_PRIMEIRA_FORMACAO";
    public static final String TELA_DEPENDENTES_OBJETO_ALTERACAO = "TELA_DEPENDENTES_OBJETO_ALTERACAO";
    public static final String TELA_FOTOS_PROTOCOLO = "TELA_FOTOS_PROTOCOLO";

    //Validacao do email com pattern
    public static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    public static String CELULAR_PATTERN = "^\\([0-9]{2}\\)[9]{1}[2-9]{1}[0-9]{3}\\-[0-9]{4}$";


    /**
     * Limpa as preferencias de cadastro
     */
    public static void limpaPreferenciasCadastro(Context context) throws Exception
    {
        //Faz a limpeza das preferencias
        Apoio.gravaPrefsValorString(context, Apoio.PREFS_CADASTRO_NOME, "");
        Apoio.gravaPrefsValorString(context, Apoio.PREFS_CADASTRO_CPF, "");
        Apoio.gravaPrefsValorString(context, Apoio.PREFS_CADASTRO_DATA_NASC, "");
        Apoio.gravaPrefsValorString(context, Apoio.PREFS_CADASTRO_CELULAR, "");
        Apoio.gravaPrefsValorString(context, Apoio.PREFS_CADASTRO_EMAIL, "");
        Apoio.gravaPrefsValorString(context, Apoio.PREFS_CADASTRO_SENHA, "");
        Apoio.gravaPrefsValorInteiro(context, Apoio.PREFS_CADASTRO_SEXO, 0);
    }

    /**
     * Obtem a data e hora de compilacao
     */
    public static String getDataHoraCompilacao()
    {
        return "27/06/2017 - 10:38";
    }

    /**
     * Obtem o path de backup
     */
    public static String getPathBackupDatabase(Context context)
    {
        return Apoio.getPathSDCard(context) + "/backup_database";
    }

    /**
     * Cria uma caixa de alerta com duas opçõese um titulo
     *
     * @param context   o contexto da activity que o chamou
     * @param sPositivo A mensagem do botão caso o usuario confirme
     * @param sNegativo A mensagem do botão caso o usuario cancele
     * @param sTitulo   O Titulo/descrição
     * @param listener  O Listener que será chamado quando o botão for pressionado
     * @return O alertdialog a ser usado
     */
    public static AlertDialog criarAlertDialog(Context context, String sPositivo, String sNegativo, String sTitulo, String sDesc, DialogInterface.OnClickListener listener)
    {
        AlertDialog.Builder builderDialogo = null;
        AlertDialog dlgAuxiliar = null;

        //Instancia a classe
        builderDialogo = new AlertDialog.Builder(context);

        //Cria as opções do alerta
        builderDialogo.setNegativeButton(sNegativo, listener);
        builderDialogo.setPositiveButton(sPositivo, listener);

        //Define o titulo
        builderDialogo.setTitle(sTitulo);

        //Define a mensagem
        builderDialogo.setMessage(sDesc);

        //Cria o alerta adicionando as opções
        dlgAuxiliar = builderDialogo.create();

        return dlgAuxiliar;
    }

    /**
     * Cria uma caixa de alerta com varias opções
     *
     * @param context  o contexto da activity que o chamou
     * @param sBotoes  Array contendo o nome dos botões
     * @param listener O Listener que será chamado quando o botão for pressionado
     * @return O alertdialog a ser usado
     */
    public static AlertDialog criarAlertDialog(Context context, CharSequence[] sBotoes, DialogInterface.OnClickListener listener)
    {
        AlertDialog.Builder builderDialogo = null;
        AlertDialog dlgAuxiliar = null;

        //Instancia a classe
        builderDialogo = new AlertDialog.Builder(context);

        //Cria as opções do alerta
        builderDialogo.setItems(sBotoes, listener);

        //Cria o alerta adicionando as opções
        dlgAuxiliar = builderDialogo.create();

        return dlgAuxiliar;
    }

    /**
     * Cria uma progressDialog
     */
    public static ProgressDialog criarProgressDialog(Context context)
    {
        ProgressDialog progressDialog = null;

        try
        {
            // Cria a tela de progresso
            progressDialog = new ProgressDialog(context);
            progressDialog.setTitle(context.getString(R.string.atencao));
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
        catch (Exception err)
        {
            LogTrace.escreve(Apoio.localErro(Apoio.class, err), Apoio.getPathLogsSDCard(context), Apoio.getArqErr());
            DialogAlerta.show(context, Apoio.getMsgErr(Apoio.class, err), context.getString(R.string.atencao), context.getString(R.string.ok));
        }

        return progressDialog;
    }

    /**
     * Seta a menagem do progressDialog
     */
    public static void progressDialogMensagem(ProgressDialog progressDialog, String sMensagem)
    {
        progressDialog.setMessage(sMensagem);
    }

    /**
     * Fecha o progressDialog
     */
    public static void fecharProgressDialog(ProgressDialog progressDialog)
    {
        progressDialog.dismiss();
    }

    /**
     * Converte string de date para tipo date
     */
    public static Calendar stringToCalendar(String sDate) throws Exception
    {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        calendar.setTime(simpleFormat.parse(sDate));
        return calendar;
    }

    /**
     * Converte string de date para tipo date
     */
    public static Calendar convertStringParaCalendar(String sDate) throws Exception
    {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        calendar.setTime(simpleFormat.parse(sDate));
        return calendar;
    }

    /**
     * Converte date para string data
     */
    public static String dateToString(Date dtData) throws Exception
    {
        SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault());
        return simpleFormat.format(dtData);
    }

    /**
     * Converte date para string data
     */
    public static String convertCalendarString(Calendar calendar) throws Exception
    {
        SimpleDateFormat simpleFormat = null;

        simpleFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return simpleFormat.format(calendar.getTime());
    }

    /**
     * Converte date para string data
     */
    public static String formataDataYYYYMMDD(Date dtData) throws Exception
    {
        SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return simpleFormat.format(dtData);
    }

    //obtenho a data Atual
    public static String obtemDataAtualSis() throws Exception
    {
        Calendar calendar = null;
        SimpleDateFormat simpleDateFormat = null;
        String sData = "";

        //obtem a data atual
        calendar = Calendar.getInstance();

        //converto a data para string e formato com a data universal
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sData = simpleDateFormat.format(calendar.getTime());

        return sData;
    }

    /**
     * verifica se tem alguma conexao
     */
    public static boolean verificaConectadoWIFI(Context context) throws Exception
    {
        ConnectivityManager connectivity;
        NetworkInfo networkInfo;
        int iNetType = 0;
        boolean bRet = false;

        //Verifica se tem conectividade no Android
        connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        // Se nao existir
        if (connectivity == null)
        {
            return bRet;
        }

        //Obtem as opcoes de conectividade
        networkInfo = connectivity.getActiveNetworkInfo();

        // Se nao existir
        if (networkInfo == null)
        {
            return bRet;
        }

        // Obtem o tipo de conexao
        iNetType = networkInfo.getType();

        //Se retornar sucesso na condicao de tipo wifi
        if (iNetType == ConnectivityManager.TYPE_WIFI)
        {
            // Se nao estiver conectado
            if (networkInfo.isConnected())
            {
                bRet = true;
            }
        }

        return bRet;
    }

    /**
     * Retorna uma string de data formatada conforme o data enviada como parametro
     */
    public static String montaDataString(Calendar calendar, int iFormato) throws Exception
    {
        String sFormat = "";
        SimpleDateFormat dateFormat;

        //Verifica se a data nao esta nula
        if (calendar != null)
        {
            //Verifica tipo de formato
            switch (iFormato)
            {
                case Apoio.DATA_HORA:
                {
                    sFormat = "dd/MM HH:mm";
                    break;
                }
                case Apoio.DATA_COMPLETA:
                {
                    sFormat = "dd/MM/yyyy";
                    break;
                }
                case Apoio.DATA_COMPLETA_HORA:
                {
                    sFormat = "dd/MM/yyyy HH:mm";
                    break;
                }
                case Apoio.DATA_COMPLETA_HORA_COM_SEGUNDOS:
                {
                    sFormat = "dd/MM/yyyy HH:mm:ss";
                    break;
                }
                case Apoio.DATA_COMPLETA_HORA_COM_SEGUNDOS_BANCO:
                {
                    sFormat = "yyyy-MM-dd HH:mm:ss";
                    break;
                }
                case Apoio.DATA_INVERTIDA:
                {
                    sFormat = "yyyy-MM-dd";
                    break;
                }
            }

            //Forma data
            dateFormat = new SimpleDateFormat(sFormat, Locale.getDefault());
            return dateFormat.format(calendar.getTime());
        }

        return "";
    }

    /**
     * Formata o número em string com as decimais
     *
     * @param dValor    - Valor a ser formatado
     * @param sMoeda    - Complemento do texto com a moeda
     * @param iNumCasas - Número de casas decimais até 7
     */
    public static String formataNumeroComMoedaV2(double dValor, String sMoeda, int iNumCasas)
    {
        String sRet = "";

        //Concatena a Moeda
        sRet += sMoeda;

        //Verifica o numero de casas
        switch (iNumCasas)
        {
            //Se for zero
            case 0:
            {
                sRet += String.format("%.0f", dValor);
                break;
            }
            //Se for uma
            case 1:
            {
                sRet += String.format("%.1f", dValor);
                break;
            }
            //Se for duas
            case 2:
            {
                sRet += String.format("%.2f", dValor);
                break;
            }
            //Se for três
            case 3:
            {
                sRet += String.format("%.3f", dValor);
                break;
            }
            //Se for quatro
            case 4:
            {
                sRet += String.format("%.4f", dValor);
                break;
            }
            //Se for cinco
            case 5:
            {
                sRet += String.format("%.5f", dValor);
                break;
            }
            //Se for seis
            case 6:
            {
                sRet += String.format("%.6f", dValor);
                break;
            }
            //Se for sete
            case 7:
            {
                sRet += String.format("%.17", dValor);
                break;
            }
            //Senão
            default:
            {
                sRet += "INVALIDO";
                break;
            }
        }

        //Retorna o valor
        return sRet;
    }

    /**
     * Coloca uma view com foco na tela
     */
    public static void requisitarFoco(View view, AppCompatActivity activity) throws Exception
    {
        //Verifica se conseguiu solicitar o foco
        if (view.requestFocus())
        {
            //Solicia o foco na view para a activity
            activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

            final Rect rect = new Rect(0, 0, view.getWidth(), view.getHeight());
            view.requestRectangleOnScreen(rect, false);
        }
    }

    /**
     * Retorna a propriedade das preferencias inteiro
     */
    public static int retornaPrefsValorInteiro(Context context, String sChave, int iValorPadrao) throws Exception
    {
        SharedPreferences prefs = context.getSharedPreferences(Apoio.PREFS_NAME, Context.MODE_PRIVATE);

        //Retorna o valor inteiro
        return prefs.getInt(sChave, iValorPadrao);
    }

    /**
     * Retorna a propriedade das preferencias string
     */
    public static String retornaPrefsValorString(Context context, String sChave, String sValorPadrao) throws Exception
    {
        SharedPreferences prefs = context.getSharedPreferences(Apoio.PREFS_NAME, Context.MODE_PRIVATE);

        //Retorna o valor string
        return prefs.getString(sChave, sValorPadrao);
    }

    /**
     * Retorna a propriedade das preferencias boolean
     */
    public static boolean retornaPrefsValorBooleano(Context context, String sChave, boolean bValorPadrao) throws Exception
    {
        SharedPreferences prefs = context.getSharedPreferences(Apoio.PREFS_NAME, Context.MODE_PRIVATE);

        //Retorna o valor boolea
        return prefs.getBoolean(sChave, bValorPadrao);
    }

    /**
     * Grava a propriedade das preferencias inteiro
     */
    public static void gravaPrefsValorInteiro(Context context, String sChave, int iValor) throws Exception
    {
        SharedPreferences prefs = context.getSharedPreferences(Apoio.PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = null;

        //Atribui ao editor
        editor = prefs.edit();

        //Adiciona o valor na pref desejada
        editor.putInt(sChave, iValor);

        //Salva as modificações
        editor.commit();
    }

    /**
     * Grava a propriedade das preferencias string
     */
    public static void gravaPrefsValorString(Context context, String sChave, String sValor) throws Exception
    {
        SharedPreferences prefs = context.getSharedPreferences(Apoio.PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = null;

        //Atribui ao editor
        editor = prefs.edit();

        //Adiciona o valor na pref desejada
        editor.putString(sChave, sValor);

        //Salva as modificações
        editor.commit();
    }

    /**
     * Grava a propriedade das preferencias boolean
     */
    public static void gravaPrefsValorBooleano(Context context, String sChave, boolean bValor) throws Exception
    {
        SharedPreferences prefs = context.getSharedPreferences(Apoio.PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = null;

        //Atribui ao editor
        editor = prefs.edit();

        //Adiciona o valor na pref desejada
        editor.putBoolean(sChave, bValor);

        //Salva as modificações
        editor.commit();
    }

    /**
     * Retorna um mensagem especifica de acordo com o erro ocorrido
     */
    public static String getMsgErr(Throwable throwable)
    {
        String sDadoRetorno = "";

        try
        {
            if (throwable instanceof SocketTimeoutException)
            {
                sDadoRetorno = "Tempo limite de conexao atingido!";
            }
            else if (throwable instanceof SocketException)
            {
                sDadoRetorno = "Não foi possível conectar com o servidor!";
            }
            else if (throwable instanceof UnknownHostException)
            {
                sDadoRetorno = "Não foi possível localizar o servidor!";
            }
            else
            {
                sDadoRetorno = "Ocorreram erros, por favor verifique o log!";
            }
        }
        finally
        {
        }

        //Retorno a descricao do erro.
        return sDadoRetorno;
    }

    /**
     * Finaliza a tela setando resultado
     */
    public static void finalizaActivity(Activity activity, int iResultado)
    {
        // Seta o resultado
        activity.setResult(iResultado);

        // Finaliza a acticity
        activity.finish();
    }

    /**
     * Finaliza a tela e passa uma intent junto
     */
    public static void finalizaActivity(Activity activity, int iResultado, Intent intent)
    {
        activity.setResult(iResultado, intent);
        activity.finish();
    }


    /**
     * Verifica se existe um conexão
     */
    public static boolean verificaConexao(Context context) throws Exception
    {
        ConnectivityManager connectivityManager = null;
        NetworkInfo networkInfo = null;
        boolean bRet = false;

        //Obtem o conector
        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();

        //Se encontrou conector
        if (networkInfo != null)
        {
            //Se esta conectado
            if (networkInfo.isConnected())
            {
                //Seta sucesso
                bRet = true;
            }
        }

        return bRet;
    }

    /**
     * Retorna o path do SDCard.
     */
    public static String getPathSDCard(Context context)
    {
        return Apoio.pathCombine(Environment.getExternalStorageDirectory().toString(), context.getPackageName());
    }

    /**
     * Retorna o path de logs do sdcard.
     */
    public static String getPathLogsSDCard(Context context)
    {
        return Apoio.pathCombine(Apoio.getPathSDCard(context), "logs");
    }

    /**
     * Retorna o path de recebe do sdCard.
     */
    public static String getPathRecSDCard(Context context)
    {
        return Apoio.pathCombine(Apoio.getPathSDCard(context), "recebe");
    }

    /**
     * Retorna o path de recebe do sdCard.
     */
    public static String getPathImagensSDCard(Context context)
    {
        return Apoio.pathCombine(Apoio.getPathSDCard(context), "imagens");
    }

    /**
     * Retorna mensagem de erro tratada, para exibicao nos dialogs. Esse metodo
     * deve ser reescrito de acordo com a necessidade de cada projeto.
     *
     * @return mensagem de erro com formato padrao
     */
    public static String getMsgErr(final Class classe, Throwable throwable)
    {
        return "Verifique o log do sistema, erro " + Apoio.localErroDialog(classe, throwable);
    }

    /**
     * Log de erro da apolicacao, onde ficara armazenados dados dos erros ocorridos.
     */
    public static String getArqErr()
    {
        return "./logerr.txt";
    }

    /**
     * Faz o chmod de um path pela aplicação.
     */
    public static int chmod(File fCaminho, int iModo) throws Exception
    {
        Class<?> fileUtils = null;
        Method setPermissions = null;

        //Obtem a classe do fileUtils
        fileUtils = Class.forName("android.os.FileUtils");

        //Obtem o metodo para setar a permissÃ£o
        setPermissions = fileUtils.getMethod("setPermissions", String.class, int.class, int.class, int.class);

        //Executa a permissÃ£o
        return (Integer) setPermissions.invoke(null, fCaminho.getAbsolutePath(), iModo, -1, -1);
    }

    /**
     * Monta o path completo de um arquivo ou pasta, dada toda a sequencia de pastas (e arquivo) como parametro.
     */
    public static String pathCombine(final String... sPaths)
    {
        File file = null;
        int iLen;
        int iAux;

        if (sPaths == null || sPaths.length == 0)
        {
            return null;
        }

        file = new File(sPaths[0]);
        iLen = sPaths.length;

        for (iAux = 1; iAux < iLen; iAux++)
        {
            file = new File(file, sPaths[iAux]);
        }

        return file.getPath();
    }

    /**
     * Metodo que formata uma mensagem de erro para exibicao ao usuario
     */
    public static String localErroDialog(final Class classe, final Throwable throwable)
    {
        StringBuilder sbMensagemRetorno;
        String sNomePacote = "";

        //Inicio a variavel com o valor do nome da classe.
        sNomePacote = classe.getName();

        //Pego somente o nome do pacote.
        sNomePacote = sNomePacote.substring(0, sNomePacote.indexOf("."));

        //Inicializa o buffer de concatenacao
        sbMensagemRetorno = new StringBuilder();

        //Laco pelos dados da pilha.
        for (StackTraceElement stackTraceElement : throwable.getStackTrace())
        {
            //Verifico se encontramos um elemento que contenha o pacote
            //da aplicacao
            if (stackTraceElement.toString().contains(sNomePacote))
            {
                //Monto a mensagem para exibr ao usuarui.
                sbMensagemRetorno.append("Erro em ");
                sbMensagemRetorno.append(stackTraceElement.getClassName().substring(stackTraceElement.getClassName().lastIndexOf(".") + 1));
                sbMensagemRetorno.append(".");
                sbMensagemRetorno.append(stackTraceElement.getMethodName());
                sbMensagemRetorno.append("(");
                sbMensagemRetorno.append(stackTraceElement.getLineNumber());
                sbMensagemRetorno.append(")");

                //Somente um laco, para no primeiro que encontrarmos.
                break;
            }
        }

        //retono a mensagem formatada.
        return sbMensagemRetorno.toString();
    }

    /**
     * Rertorna uma stack com os dados do erro ocorrido, de uma forma mais legivel
     * para nos. Retorna somente erros que tenham acontecido em classes que
     * estejam dentro do pacote definido no metodo <code>setaPacoteAplicacao(final Class classe)</code>
     */
    public static String localErro(final Class classe, final Throwable throwable)
    {
        StringBuilder sbDados = null;
        StackTraceElement[] arrStackTrace;
        int iPoss = 0;
        String sNomeDaClasse = "";
        String sNomePacote = "";
        boolean bVerificaNomeClasse = true;
        int iCont = 0;

        try
        {
            //Inicio a variavel com o valor do nome da classe.
            sNomePacote = classe.getName();

            //Pego somente o nome do pacote.
            sNomePacote = sNomePacote.substring(0, sNomePacote.indexOf("."));

            //primeiramente inicio o stringBuilder para evitar retorno null.
            sbDados = new StringBuilder();

            //Pego o array com as informações dos erros.
            arrStackTrace = throwable.getStackTrace();

            //Apend com o nome do erro
            sbDados.append(throwable.toString());
            sbDados.append(". Pilha: ");

            //Pego a o tamanho da stack, para utilizar no laco.
            iCont = arrStackTrace.length - 1;

            //Laco pelos registros da stack.
            for (; iCont >= 0; iCont--)
            {
                //Pego o nome da classe
                sNomeDaClasse = arrStackTrace[iCont].getClassName();

                //Verifico se o nome da classe da stack, eh o mesmo passado por parametro,
                //assim somente logo a partir da classe onde ocorreu o problema.
                if (bVerificaNomeClasse && !sNomeDaClasse.contains(sNomePacote))
                {
                    //Continuo o laco.
                    continue;
                }

                //Seto como false, para nao vericicar mais se estamos
                bVerificaNomeClasse = false;

                //pego o  ultimo index de onde está o '.'.
                iPoss = sNomeDaClasse.lastIndexOf('.');

                //Se for menor que 0 ficamos com o valor 0, senão ficamos com o valor dele + 1 para não pegar o '.'.
                iPoss = iPoss < 0 ? 0 : ++iPoss;

                //Pegamos o nome a partir do ultimo
                sbDados.append(",");
                sbDados.append(sNomeDaClasse.substring(iPoss));
                sbDados.append(".");
                sbDados.append(arrStackTrace[iCont].getMethodName());//Nome do metodo.
                sbDados.append(".");
                sbDados.append(arrStackTrace[iCont].getLineNumber());//linha onde ocorreu o erro.
            }
        }
        //temos que tratar com o Throwable, pois podemos receber um como parametro.
        catch (Throwable err)
        {
            //Deixar vazio, caso ocorra um erro nao afeta a aplicacao.
        }

        //Retorno a mensagem formatada ou vazio caso ocorra um erro.
        return sbDados.toString().replace("\n", " ");
    }

    /**
     * Troca as cores do botão do dialog
     */
    public static void trocaCoresBotoesDialog(AlertDialog dlgMensagem, Context context) throws Exception
    {
        Button cmdNegativo = null;
        Button cmdPositivo = null;

        //Troca a cor do botão negativo
        cmdNegativo = dlgMensagem.getButton(DialogInterface.BUTTON_NEGATIVE);
        cmdNegativo.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));

        //Troca a cor do botão positivo
        cmdPositivo = dlgMensagem.getButton(DialogInterface.BUTTON_POSITIVE);
        cmdPositivo.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
    }

    /**
     * Cria os diretorios utilizados pela aplicacao
     */
    public static boolean criaDiretorios(Context context)
    {
        File fileDir = null;
        boolean bRet = false;

        try
        {
            //Cria path do sdcard
            fileDir = new File(Apoio.getPathSDCard(context));
            boolean bCriou = fileDir.mkdirs();

            //Damos permissao para qualquer usuario poder ler, editar e gravar
            Apoio.chmod(fileDir, 0777);

            //Cria path de recebimento no SDCard
            fileDir = new File(Apoio.getPathRecSDCard(context));
            fileDir.mkdirs();

            //Damos permissao para qualquer usuario poder ler, editar e gravar
            Apoio.chmod(fileDir, 0777);

            //Cria path de imagens no SDCard
            fileDir = new File(Apoio.getPathImagensSDCard(context));
            fileDir.mkdirs();

            //Damos permissao para qualquer usuario poder ler, editar e gravar
            Apoio.chmod(fileDir, 0777);

            //Cria path de logs no SDCard
            fileDir = new File(Apoio.getPathLogsSDCard(context));
            fileDir.mkdirs();

            //Damos permissao para qualquer usuario poder ler, editar e gravar
            Apoio.chmod(fileDir, 0777);
            bRet = true;
        }
        catch (Exception errSis)
        {
            bRet = false;
        }

        return bRet;
    }

    /**
     * Fazemos o parse da data para o formato desejado em string
     *
     * @param sData            String contendo a data
     * @param sFormatoAtual    Formato que se encontra a data
     * @param sFormatoDesejado Formato no qual deseja-se o retorno
     */
    public static String parseData(String sData, String sFormatoAtual, String sFormatoDesejado) throws ParseException
    {
        String sDataRet = "";
        SimpleDateFormat simpleDateFormat = null;

        try
        {
            // Iniciamos o dateFormat com o pattern passado
            simpleDateFormat = new SimpleDateFormat(sFormatoAtual);

            //Seta a propriedade para false, para que a data não seja corrigida automaticamente pelo android
            simpleDateFormat.setLenient(false);

            // Obtemos o objeto date e convertemos para string no formato yyyyMMdd
            sDataRet = DateFormat.format(sFormatoDesejado, simpleDateFormat.parse(sData)).toString();
        }
        catch (Exception errSis)
        {
            sDataRet = "";
        }

        return sDataRet;
    }

    /**
     * Verifica se o CPF informado e valido
     */
    public static boolean verificaCPFValido(String sCPF) throws Exception
    {
        char[] cCPF = new char[32];
        char[] cDigitos = new char[8];
        int iContDigitos;
        int iSomaValDigitos;
        int iRestoDiv;
        int iDigito1;
        int iDigito2;

        // Se o tamanho do CPF for diferente de 11 digitos
        if (sCPF.length() != 11 ||
            sCPF.equals("00000000000") ||
            sCPF.equals("11111111111") ||
            sCPF.equals("22222222222") ||
            sCPF.equals("33333333333") ||
            sCPF.equals("44444444444") ||
            sCPF.equals("55555555555") ||
            sCPF.equals("66666666666") ||
            sCPF.equals("77777777777") ||
            sCPF.equals("88888888888") ||
            sCPF.equals("99999999999") )
        {
            return false;
        }

        // Limpa variavel que ira conter o CPF e copia para buffer auxiliar
        // apenas 9 bytes
        // MemSet(sCPF, sizeof(sCPF), NULL);
        cCPF = sCPF.substring(0, 9).toCharArray();

        // Limpa variavel que ira conter os digitos de verif. e copia para
        // buffer auxiliar
        // apenas 2 bytes
        // MemSet(sDigitos, sizeof(sDigitos), NULL);
        cDigitos = sCPF.substring(9, 11).toCharArray();

        // Calcula primeiro digito
        // *****************************************************
        // Loop pelos digitos
        iSomaValDigitos = 0;

        for (iContDigitos = 0; iContDigitos < 9; iContDigitos++)
        {
            iSomaValDigitos += (cCPF[iContDigitos] - 48) * (11 - (iContDigitos + 1));
        }

        // Calculo do resto da divisao da soma dos digitos por 11
        iRestoDiv = 0;
        iRestoDiv = iSomaValDigitos % 11;

        // Verifica se o resto é igual a 0 ou 1
        if (iRestoDiv < 2)
        // Se for o primeiro digito é zero
        {
            iDigito1 = 0;
        }
        else
        // Senão o digito é 11 menos o resto da divisão
        {
            iDigito1 = 11 - iRestoDiv;
        }

        // Calcula segundo digito
        // *****************************************************
        // Loop pelos digitos
        iSomaValDigitos = iDigito1 * 2;

        for (iContDigitos = 0; iContDigitos < 9; iContDigitos++)
        {
            iSomaValDigitos += (cCPF[iContDigitos] - 48) * (12 - (iContDigitos + 1));
        }

        // Calculo do resto da divisao da soma dos digitos por 11
        iRestoDiv = iSomaValDigitos % 11;

        // Verifica se o resto é igual a 0 ou 1
        if (iRestoDiv < 2)
        // Se for o primeiro digito é zero
        {
            iDigito2 = 0;
        }
        else
        // Senão o digito é 11 menos o resto da divisão
        {
            iDigito2 = 11 - iRestoDiv;
        }

        // *****************************************************
        return (cDigitos[0] == iDigito1 + 48 && cDigitos[1] == iDigito2 + 48);
    }

    /**
     * Fecha uma instancia de uma classe derivada de InputStream, com tratamento de erro,
     * evitando assim que o codigo de fechamento com try/catch seja necessario,
     * deixando assim o codigo mais legivel/limpo na classe/metodo cliente.
     */
    public static void closeStream(InputStream stream)
    {
        if (stream != null)
        {
            try
            {
                stream.close();
            }
            catch (Exception err)
            {
            }
        }
    }

    /**
     * Fecha uma instancia de uma classe derivada de InputStream, com tratamento de erro,
     * evitando assim que o codigo de fechamento com try/catch seja necessario,
     * deixando assim o codigo mais legivel/limpo na classe/metodo cliente.
     */
    public static void closeStream(OutputStream stream)
    {
        if (stream != null)
        {
            try
            {
                stream.close();
            }
            catch (Exception err)
            {
            }
        }
    }

    /**
     * Verifica se o usuário está logado
     */
    public static MASUser verificaUsuarioLogado(Context context) throws Exception
    {
        MASUser masuserUsuario = null;

        //Inicia o MAS para não perder o MASUserm, pois quando o Android fica inativo por muito tempo, o MASUser aponta erro na chamada do getCurrentUser
        //MAS.start(context);
        //MAS.setAuthenticationListener(new RetornoMASAuthenticationListener());

        //Se não tiver um usuário logado
        masuserUsuario = MASUser.getCurrentUser();
        return masuserUsuario;
    }

    /**
     * Fecha o teclado de uma tela
     */
    public static void fechaTeclado(Activity activity)
    {
        InputMethodManager inputMethodManager = null;
        View view = null;

        //Pega o input method do sistema
        inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);

        //Recupera a view que está com o foco atual
        view = activity.getCurrentFocus();

        //Se for nulo o foco
        if (view == null)
        {
            //Instancia nova view
            view = new View(activity);
        }

        //Esconde o teclado
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * Tenta fazer o parse, caso seja invalido o valor eh zero
     *
     * @param sNumero String contendo o valor a ser transformado
     * @return Retorna o numero transformado
     */
    public static int tryParse(String sNumero)
    {
        // Chamamos o trryparse de 0
        return Apoio.tryParse(sNumero, 0);
    }

    /**
     * Tenta fazer o parse, caso seja invalido o valor eh o valor padrao
     *
     * @param sNumero String contendo o valor a ser transformado
     * @param iValPadrao Valor que sera setado caso seja invalido
     * @return Retorna o numero transformado
     */
    public static int tryParse(String sNumero, int iValPadrao)
    {
        int iRetorno = 0;

        try
        {
            // Fazemos o parse de int
            iRetorno = Integer.parseInt(sNumero);
        }
        catch (NumberFormatException errNfe)
        {
            // Deu erro, setamos o valor padrao
            iRetorno = iValPadrao;
        }

        return iRetorno;
    }

    /**
     * Tenta fazer o parse, caso seja invalido o valor eh o valor padrao
     *
     * @param sNumero String contendo o valor a ser transformado
     * @param dValPadrao Valor que sera setado caso seja invalido
     * @return Retorna o numero transformado
     */
    public static double tryParse(String sNumero, double dValPadrao)
    {
        double dRetorno = 0;

        try
        {
            // Fazemos o parse de int
            dRetorno = Double.parseDouble(sNumero);
        }
        catch (NumberFormatException errNfe)
        {
            // Deu erro, setamos o valor padrao
            dRetorno = dValPadrao;
        }

        return dRetorno;
    }

    /**
     * Faz a localização do item no array para retornar a posição de onde ele se encontra ou -1 se não tiver
     */
    public static int retornaPosicaoItemCombo(ArrayList<Combos> arrDados, String sInfoComparacao) throws Exception
    {
        int iCont = 0;
        int iPosicao = -1;
        Combos combos = null;

        //Posiciona o sexo
        for ( iCont = 0; iCont < arrDados.size(); iCont++ )
        {
            //Obtem o item
            combos = arrDados.get(iCont);

            //Compara com o valor da carga para posicionar
            if ( combos.sCodigo.equals(sInfoComparacao) )
            {
                iPosicao = iCont;
                break;
            }
        }

        return iPosicao;
    }

    /**
     * Retorna a descrição do sexo pelo código
     */
    public static String retornaSexo(String sCodigo) throws Exception
    {
        String sSexo = "";

        //Se for feminino
        if ( sCodigo.equals("F") )
        {
            sSexo = "FEMININO";
        }
        //Se for masculino
        else if ( sCodigo.equals("M") )
        {
            sSexo = "MASCULINO";
        }

        return sSexo;
    }

    /**
     * Retorna a descrição do tipo de dependente pelo código
     */
    public static String retornaTipoDependente(String sCodigo) throws Exception
    {
        String sTipoDependente = "";

        //Se for outros
        if ( sCodigo.equals("0") )
        {
            sTipoDependente = "OUTROS";
        }
        //Se for cônjuge
        else if ( sCodigo.equals("1") )
        {
            sTipoDependente = "CÔNJUGE";
        }
        //Se for companheiro
        else if ( sCodigo.equals("2") )
        {
            sTipoDependente = "COMPANHEIRO(A)";
        }
        //Se for filho não emancipado
        else if ( sCodigo.equals("3") )
        {
            sTipoDependente = "FILHO(A) NÃO EMANCIPADO MENOR 21 ANOS";
        }
        //Se for filho invalido
        else if ( sCodigo.equals("4") )
        {
            sTipoDependente = "FILHO(A) INVÁLIDO(A)";
        }
        //Se for pai mae depend.
        else if ( sCodigo.equals("5") )
        {
            sTipoDependente = "PAI / MÃE COM DEPENDÊNCIA ECONOMICA";
        }
        //Se for irmão não emancipado
        else if ( sCodigo.equals("6") )
        {
            sTipoDependente = "IRMÃO NÂO EMANCIPADO MENOR DE 21 ANOS COM DEP. ECONÔMICA";
        }
        //Se for irmão inválido
        else if ( sCodigo.equals("7") )
        {
            sTipoDependente = "IRMÃO INVÁLIDO COM DEPENDÊNCIA ECONÔMICA";
        }
        //Se for enteado não emancipado
        else if ( sCodigo.equals("8") )
        {
            sTipoDependente = "ENTEADO NÃO EMANCIPADO MENOR DE 21 ANOS COM DEP. ECONÔMICA";
        }
        //Se for enteado inválido
        else if ( sCodigo.equals("9") )
        {
            sTipoDependente = "ENTEADO INVÁLIDO COM DEPENDENCIA ECONÔMICA";
        }
        //Se for menor tutelado
        else if ( sCodigo.equals("10") )
        {
            sTipoDependente = "MENOR TUTELADO NÃO EMANCIPADO MENOR DE 21 ANOS COM DEP. ECONÔMICA";
        }
        //Se for menor tutelado inválido
        else if ( sCodigo.equals("11") )
        {
            sTipoDependente = "MENOR TUTELADO INVÁLIDO COM DEP. ECONÔMICA";
        }
        //Se for filho maior de 21
        else if ( sCodigo.equals("12") )
        {
            sTipoDependente = "FILHO MAIOR DE 21 ANOS COM DEPENDÊNCIA ECONÔMICA";
        }
        //Se for ex cônjuge
        else if ( sCodigo.equals("14") )
        {
            sTipoDependente = "EX-CÔNJUGE QUE RECEBA PENSÃO DE ALIMENTOS";
        }

        return sTipoDependente;
    }

    /**
     * Retorna a descrição do tipo de curso pelo código
     */
    public static String retornaTipoCurso(String sCodigo) throws Exception
    {
        String sTipoCurso = "";

        //Se for formação acadêmica
        if ( sCodigo.equals("1") )
        {
            sTipoCurso = "FORMAÇÃO ACADÊMICA";
        }
        //Se for cívil
        else if ( sCodigo.equals("2") )
        {
            sTipoCurso = "CÍVIL";
        }
        //Se for militar
        else if ( sCodigo.equals("3") )
        {
            sTipoCurso = "MILITAR";
        }
        //Se for idiomas
        else if ( sCodigo.equals("4") )
        {
            sTipoCurso = "IDIOMAS";
        }
        //Se for especializacao
        else if ( sCodigo.equals("5") )
        {
            sTipoCurso = "ESPECIALIZAÇÃO";
        }
        //Se for outros
        else if ( sCodigo.equals("6") )
        {
            sTipoCurso = "OUTROS";
        }

        return sTipoCurso;
    }

    /**
     * Formata os dados de acordo com a mascara informada com #
     */
    public static String formataDados(String sDadosOriginais, String sFormato) throws Exception
    {
        char cFilter;
        char cOriginal;
        int iAuxString = 0;
        int iAuxFilter = 0;
        String sRetorno = "";

        //Tira os espaços em branco da string
        sDadosOriginais = sDadosOriginais.trim();

        //faz um laco para formatar a string
        for(iAuxFilter = 0; iAuxFilter < sFormato.length(); iAuxFilter++)
        {
            //pega o char do filtro
            cFilter = sFormato.charAt(iAuxFilter);

            //faz uma verificacao se a string passada tem o numero de caracteres valido
            if(sDadosOriginais.length() > iAuxString)
            {
                //pega o character da string original
                cOriginal = sDadosOriginais.charAt(iAuxString);
            }
            else
            {
                //para a execução do for
                break;
            }

            //verifica se no filtro temos um character de input(se e para colocar um character normal)
            if(cFilter == '#')
            {
                //adiciona o character na string de saida
                sRetorno += String.format("%c", cOriginal);

                //aumenta o contador da string original
                iAuxString++;
            }
            //caso nao seja um character de input
            else
            {
                //colocamos ele na string
                sRetorno += String.format("%c", cFilter);

                //caso o character do filtro seja o mesmo que contem na string
                if(cOriginal == cFilter)
                {
                    //adicionamos o contador da string original
                    iAuxString++;
                }
            }
        }

        return sRetorno;
    }

    public  static boolean isJSONValid(String test) {
        try {
            new JSONObject(test);
        } catch (JSONException ex) {
            try {
                new JSONArray(test);
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;
    }

}