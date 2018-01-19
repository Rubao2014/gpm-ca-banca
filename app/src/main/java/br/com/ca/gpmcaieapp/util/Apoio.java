package br.com.ca.gpmcaieapp.util;

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

import br.com.ca.gpmcaieapp.R;
import br.com.ca.gpmcaieapp.models.Combos;

/**
 * Classe de Apoio
 */
public class Apoio
{
    // Constantes de preferencias
    public static final String PREFS_MANTER_CONECTADO = "prefs_manter_conectado";
    public static final String PREFS_TOKEN = "prefs_token";


    // Constantes de preferencias de cadastro
    public static final String PREFS_ID_CNS = "prefs_id_cns";
    public static final String PREFS_ID_CPF = "prefs_id_cpf";

    // Constantes de preferencias de cadastro
    public static final String PREFS_CADASTRO_NOME = "prefs_cadastro_nome";
    public static final String PREFS_CADASTRO_CPF = "prefs_cadastro_cpf";
    public static final String PREFS_CADASTRO_DATA_NASC = "prefs_cadastro_data_nasc";
    public static final String PREFS_CADASTRO_CELULAR = "prefs_cadastro_celular";
    public static final String PREFS_CADASTRO_EMAIL = "prefs_cadastro_email";
    public static final String PREFS_CADASTRO_SENHA = "prefs_cadastro_senha";
    public static final String PREFS_CADASTRO_SEXO = "prefs_cadastro_sexo";
    public static final String PREFS_CADASTRO_CNS = "prefs_cadastro_cns";
    public static final String PREFS_CADASTRO_CNH = "prefs_cadastro_cnh";

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

    // GW ta ITEC
    //public static final String CLIENT_KEY = "64819718-e690-48a7-8a7b-49bebc30ccde";
    //public static final String CLIENT_SECRET = "2bf20aa8-fc95-40ee-94dd-5a380fe26efd";

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
    public static final int TIPO_COMUNICACAO_EDUCACAO_SERIES = 11;
    public static final int TIPO_COMUNICACAO_SAUDE = 12;
    public static final int TIPO_COMUNICACAO_NUMERO_CNS = 13;
    public static final int TIPO_COMUNICACAO_VACINAS_CNS = 14;
    public static final int TIPO_COMUNICACAO_RELACIONAMENTO_CPF_CNH = 15;
    public static final int TIPO_COMUNICACAO_NUMERO_CNH = 16;
    public static final int TIPO_COMUNICACAO_MULTAS_CNH = 17;

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
    public static final int RETORNO_TELA_SAUDE = 13;
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


    //Constantes das guias da SEMARH
    public static final int SEMARH_ABAS = 1;
    public static final int SEMARH_LITORIAL = 0;




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

    public static String lista_escolas = new String("{\"escolas\": [\n" +
            "      {\n" +
            "      \"codigo\": \"0721\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL MAXIMINIANO DA ENCARNACAO\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0817\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL DOIS DE JUNHO\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0719\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL DA ENGOMADEIRA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"1013\",\n" +
            "      \"nome\": \"CENTRO MUNICIPAL DE EDUCACAO INFANTIL CSU JOAO PAULO I\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0733\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL MANOEL FRANCISCO DO NASCIMENTO BRITO\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0521\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL SOCIEDADE BENEFICENTE CULTURAL DE AMARALINA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0748\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL GOVERNADOR ROBERTO SANTOS\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0751\",\n" +
            "      \"nome\": \"CRECHE E PRE ESCOLA PRIMEIRO PASSO SUSSUARANA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"1002\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL DE CANABRAVA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0425\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL NOSSA SENHORA DE NAZARE\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0834\",\n" +
            "      \"nome\": \"CENTRO MUNICIPAL DE EDUCACAO INFANTIL HELCIO TRIGUEIRO\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"1101\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL ALMIRANTE ERNESTO DE MOURAO SA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"1010\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL FAZENDA GRANDE II MINISTRO CARLOS SANTANA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"1011\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL OSCAR DA PENHA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0357\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL 15 DE OUTUBRO\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0316\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL FAZENDA GRANDE\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"1124\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL VISCONDE DE CAIRU\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0349\",\n" +
            "      \"nome\": \"CENTRO MUNICIPAL DE EDUCACAO INFANTIL ANGELINA ROCHA DE ASSIS\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"1130\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL D PEDRO I\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"1104\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL CID PASSOS\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0558\",\n" +
            "      \"nome\": \"CENTRO MUNICIPAL DE EDUCACAO INFANTIL CALABAR\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0956\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL PROFESSOR ANTONIO PITHON PINTO\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0513\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL SANTO ANDRE\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"1128\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL SAO DOMINGOS\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0139\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL MARIA QUITERIA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"1044\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL PROFESSOR CLAUDIO VEIGA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0540\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL PADRE JOSE DE ANCHIETA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0952\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL ANDRE REBOUCAS\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0546\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL PROFESSOR ANTONIO CARLOS ONOFRE\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0516\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL TERTULIANO DE GOES\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0802\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL CLERISTON ANDRADE\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0971\",\n" +
            "      \"nome\": \"CENTRO MUNICIPAL DE EDUCACAO INFANTIL EDUCAR E VIVER\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0329\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL BELA VISTA DO LOBATO\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0537\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL COMUNITARIA CRISTO REDENTOR\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"1037\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL PROFª IRENE DA SILVA COSTA SANTOS\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"1035\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL PROFESSOR MANOEL DE ALMEIDA CRUZ\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0403\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL GISELIA PALMA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0511\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL ENGENHO VELHO DA FEDERACAO\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0445\",\n" +
            "      \"nome\": \"CENTRO MUNICIPAL DE EDUCACAO INFANTIL DA SANTA MONICA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0333\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL CONEGO EMILIO LOBO\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0948\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL TENENTE ALMIR\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0433\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL MINISTRO SIMOES FILHO\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0522\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL OSVALDO CRUZ\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0145\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL LANDULFO ALVES\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0438\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL JULIETA VIANA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"1029\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL CRISTO REI\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0524\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL CASA DA AMIZADE\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0147\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL JOAO PEDRO DOS SANTOS\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0602\",\n" +
            "      \"nome\": \"CENTRO MUNICIPAL DE EDUCACAO INFANTIL ASSOCIACAO CRISTA FEMININA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0428\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL NOSSA SENHORA DA BOA FE\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0534\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL SENHORA SANTANA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0437\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL VILA VICENTINA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0202\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL CARMELITANA 25 DE AGOSTO\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0615\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL PE MANUEL CORREIA DE SOUZA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0347\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL FILHOS DE SALOMAO\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0606\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL UNIAO CARIDADE E ABRIGO\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0603\",\n" +
            "      \"nome\": \"INSTITUTO MUNICIPAL DE EDUCACAO PROFESSOR JOSE ARAPIRACA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0510\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL OSWALDO DA GAMA ALVES\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0502\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL PROFESSORA ANITA BARBUDA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0639\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL NOSSA SENHORA DA PAZ\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0251\",\n" +
            "      \"nome\": \"CENTRO MUNICIPAL DE EDUCACAO INFANTIL DO URUGUAI\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0712\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL RISOLETA NEVES\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0814\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL BEZERRA DE MENEZES\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0713\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL ACELINO MAXIMIANO DA ENCARNACAO\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0836\",\n" +
            "      \"nome\": \"CENTRO MUNICIPAL DE EDUCACAO INFANTIL PIRAJA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0815\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL IRMA MARIANA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0968\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL PROFESSOR ITALO GAUDENZI\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"1112\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL DE BOTELHO\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"1115\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL CLAUDEMIRA SANTOS LIMA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"1020\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL JANDIRA DANTAS COSTA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0735\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL ERALDO TINOCO MELO\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0743\",\n" +
            "      \"nome\": \"CENTRO MUNICIPAL DE EDUCACAO INFANTIL NOSSA LUTA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"1023\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL SAO DAMIAO\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0301\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL ANTONIO CARLOS MAGALHAES\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0303\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL ASSISTENCIA SOCIAL SAO JOSE\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0306\",\n" +
            "      \"nome\": \"CENTRO MUNICIPAL DE EDUCACAO INFANTIL CASA DA CRIANCA DO BOM JUA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0825\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL PROFESSORA HILDA FORTUNA DE CASTRO\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0337\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL ENGENHEIRO GILBERTO PIRES MARINHO\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0351\",\n" +
            "      \"nome\": \"CENTRO MUNICIPAL DE EDUCACAO INFANTIL FRUTO DO AMANHA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0954\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL GERALDO BISPO DOS SANTOS\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"1121\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL DE ILHA DE MARE\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0644\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL MALE DEBALE\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0831\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL DR ORLANDO IMBASSAHY\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0610\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL MANUEL LISBOA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0607\",\n" +
            "      \"nome\": \"CIEP - ENGº LEONEL DE MOURA BRIZOLA - ESCOLA MUNICIPAL PIRATINI\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0631\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL JULIETA CALMON\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0916\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL JOAQUIM MAGALHAES\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0242\",\n" +
            "      \"nome\": \"CENTRO MUNICIPAL DE EDUCACAO INFANTIL ARLETE MAGALHAES\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0960\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL DR EDUARDO DOTO\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"1139\",\n" +
            "      \"nome\": \"CRECHE E PRE ESCOLA PRIMEIRO PASSO PERIPERI\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0313\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL HELENA MAGALHAES\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0112\",\n" +
            "      \"nome\": \"CENTRO MUNICIPAL DE EDUCACAO INFANTIL PAROQUIAL DE SANTANA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0215\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL SOCIEDADE 6 DE JANEIRO\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0506\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL VALE DAS PEDRINHAS\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0221\",\n" +
            "      \"nome\": \"CENTRO MUNICIPAL DE EDUCACAO INFANTIL BARONESA DE SAUIPE\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0234\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL PRIMEIRO DE MAIO\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0429\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL MAJOR ELOI MAGALHAES\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0970\",\n" +
            "      \"nome\": \"CENTRO MUNICIPAL DE EDUCACAO INFANTIL MUNDO FELIZ\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0217\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL TIRADENTES\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0658\",\n" +
            "      \"nome\": \"CENTRO MUNICIPAL DE EDUCACAO INFANTIL CASTRO ALVES\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0332\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL COMUNITARIA REGINA STUKENBORG\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0343\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL BARBOSA RODRIGUES\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0632\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL PEDRO VELOSO GORDILHO\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0346\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL FRANCISCO MANGABEIRA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0439\",\n" +
            "      \"nome\": \"CENTRO MUNICIPAL DE EDUCACAO INFANTIL HOSANNAH DE OLIVEIRA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0441\",\n" +
            "      \"nome\": \"CENTRO MUNICIPAL DE EDUCACAO INFANTIL JOSE DA SILVA TAVARES\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0314\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL MARECHAL RONDON\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0702\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL DO ALTO DA CACHOEIRINHA NELSON MALEIRO\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0707\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL MADRE HELENA IRMAOS KENNEDY\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0711\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL SAO GONCALO DO RETIRO\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0101\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL PROFESSOR ALEXANDRE LEAL COSTA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0720\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL MARIA CONSTANCA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0820\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL JAIME VIEIRA LIMA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0806\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL D ISABEL BRANDAO VILELA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"1015\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL BEATRIZ FARIAS\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0716\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL DO BEIRU\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0724\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL DE NOVO HORIZONTE\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0729\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL SAO MIGUEL\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0108\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL NOSSA SENHORA DA SALETE\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0109\",\n" +
            "      \"nome\": \"CENTRO MUNICIPAL DE EDUCACAO INFANTIL CSU COSME DE FARIAS\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"1021\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL PROFESSOR MILTON SANTOS\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0110\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL SANTA ANGELA DAS MERCES\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"1116\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL D EUGENIO DE ARAUJO SALES\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"1041\",\n" +
            "      \"nome\": \"CENTRO MUNICIPAL DE EDUCACAO INFANTIL RAFAEL DE OLIVEIRA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0609\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL BRIGADEIRO EDUARDO GOMES\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"1005\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL CECY ANDRADE\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0103\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL COSME DE FARIAS\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"1106\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL DE BANANEIRA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"1009\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL MARIA ROSA FREIRE\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0550\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL JOSE CALAZANS BRANDAO DA SILVA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"1127\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL ALTO DE COUTOS\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0967\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL SANTO ANTONIO DAS MALVINAS\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0966\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL DEPUTADO CRISTOVAO FERREIRA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"1138\",\n" +
            "      \"nome\": \"CRECHE E PRE ESCOLA PRIMEIRO PASSO TUBARAO\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0350\",\n" +
            "      \"nome\": \"CENTRO MUNICIPAL DE EDUCACAO INFANTIL YOLANDA PIRES\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0328\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL BATISTA DE SAN MARTIM\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"1131\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL FERNANDO PRESIDIO\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0121\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL SEBASTIAO DIAS\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0505\",\n" +
            "      \"nome\": \"CENTRO MUNICIPAL DE EDUCACAO INFANTIL EDUARDO JOSE DOS SANTOS\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0973\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL MANOEL HENRIQUE DA SILVA BARRADAS\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0972\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL PROFESSORA OLGA METTIG\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0649\",\n" +
            "      \"nome\": \"CENTRO MUNICIPAL DE EDUCACAO INFANTIL GEORGIA MARIA BARRADAS CARNEIRO\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0650\",\n" +
            "      \"nome\": \"CENTRO MUNICIPAL DE EDUCACAO INFANTIL JOSE MARIA DE MAGALHAES NETO\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"1137\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL PROFESSOR WILLIAM MARQUES DE ARAUJO GOES\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0124\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL LELIS PIEDADE\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0132\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL JOAO XXIII\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"1118\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL DE PARIPE\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"1033\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL NOSSA SENHORA APARECIDA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"1126\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL MIRANTES DE PERIPERI\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0940\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL AGRIPINIANO BARROS\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0969\",\n" +
            "      \"nome\": \"CENTRO MUNICIPAL DE EDUCACAO INFANTIL MARIO ALTENFELDER\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0961\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL PROFESSORA EUFROSINA MIRANDA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0934\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL GRACILIANO RAMOS\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0622\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL NOVA DO BAIRRO DA PAZ\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0626\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL DO PARQUE SAO CRISTOVAO PROFESSOR JOAO FERNANDES DA CUNHA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0162\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL VISCONDE DE CAIRU DE BROTAS\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0838\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL ADILSON DE SOUZA GALLO\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0443\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL PROFESSORA SUZANA IMBASSAHY\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0310\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL CONSUL SCHINDLER\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0126\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL JOAO LINO\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"1031\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL PROFESSOR AFONSO TEMPORAL\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"1034\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL BATISTA DE VALERIA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0823\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL DE SAO MARCOS\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0975\",\n" +
            "      \"nome\": \"CENTRO MUNICIPAL DE EDUCACAO INFANTIL PAULO BISPO BRAZ\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0409\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL DO PAU MIUDO\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0340\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL PROFESSORA MARIA DE LOURDES SANTANA ALVES\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0317\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL PROFESSOR ANTONIO CARVALHO GUEDES\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0630\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL TERESA CRISTINA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0444\",\n" +
            "      \"nome\": \"CENTRO MUNICIPAL DE EDUCACAO INFANTIL EPIFANIA SILVA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0703\",\n" +
            "      \"nome\": \"CENTRO MUNICIPAL DE EDUCACAO INFANTIL CSU DE PERNAMBUES\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"1110\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL DR OTAVIANO PIMENTA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0717\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL DE NOVA SUSSUARANA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0727\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL JESUS DE NAZARE\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"1113\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL OITO DE MAIO\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"1006\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL DA PALESTINA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"1048\",\n" +
            "      \"nome\": \"CRECHE E PRE ESCOLA PRIMEIRO PASSO CAJAZEIRAS VIII\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"1049\",\n" +
            "      \"nome\": \"CRECHE E PRE ESCOLA PRIMEIRO PASSO CAJAZEIRAS X\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"1001\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL DE NOVO MAROTINHO\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0742\",\n" +
            "      \"nome\": \"CENTRO MUNICIPAL DE EDUCACAO INFANTIL CECY ANDRADE\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0745\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL JARDIM SANTO INACIO\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0747\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL EDITE DANTAS\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"1024\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL PROFESSORA ELISA SALDANHA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0738\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL TOMAZ GONZAGA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0741\",\n" +
            "      \"nome\": \"CENTRO MUNICIPAL DE EDUCACAO INFANTIL ALVARO DA FRANCA ROCHA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0247\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL ALFREDO AMORIM\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"1103\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL COLINA DO MAR\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0335\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL ASSISTENCIAL NOSSA SENHORA DE GUADALUPE\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0553\",\n" +
            "      \"nome\": \"CENTRO MUNICIPAL DE EDUCACAO INFANTIL DALIA DE MENEZES\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0514\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL SAO JOAO BATISTA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0976\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL IVONE VIEIRA LIMA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0535\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL SAO DOMINGOS SAVIO\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0660\",\n" +
            "      \"nome\": \"CRECHE E PRE ESCOLA PRIMEIRO PASSO SAO CRISTOVAO\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"1129\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL RUI BARBOSA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"1032\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL PROFESSOR RICARDO PEREIRA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"1030\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL ULYSSES GUIMARAES\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0159\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL SANTA RITA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0146\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL PROFESSOR ARISTIDES NOVIS\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0543\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL PROFESSORA GABRIELA SA PEREIRA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0125\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL SATURNINO CABRAL\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0963\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL MACHADO DE ASSIS\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0604\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL DE PITUACU\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"1027\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL IRMA ELISA MARIA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0647\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL ALLAN KARDEC\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"1038\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL PROFESSORA SONIA CAVALCANTI\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0233\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL UNIAO COMUNITARIA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0827\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL CECILIA MEIRELES\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0434\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL SAO JUDAS TADEU\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0432\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL JOSAFA CARLOS BORGES\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"1119\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL NOSSA SENHORA DA CONCEICAO\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0315\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL XAVIER MARQUES\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0150\",\n" +
            "      \"nome\": \"CENTRO MUNICIPAL DE EDUCACAO INFANTIL VIRGEM DE LA ALMUDENA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0228\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL ALMERINDA COSTA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"1039\",\n" +
            "      \"nome\": \"CENTRO MUNICIPAL DE EDUCACAO INFANTIL CANTINHO DAS CRIANCAS\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0144\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL RUY DE LIMA MALTEZ\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0408\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL DA CIDADE NOVA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0207\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL HENRIQUETA MACHADO\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0651\",\n" +
            "      \"nome\": \"CENTRO MUNICIPAL DE EDUCACAO INFANTIL RAUL QUEIROZ\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0243\",\n" +
            "      \"nome\": \"CENTRO MUNICIPAL DE EDUCACAO INFANTIL ELOYNA BARRADAS\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0623\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL RECANTO DOS COQUEIROS\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0213\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL PRESIDENTE CASTELO BRANCO\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0637\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL CARLOS MURION\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0407\",\n" +
            "      \"nome\": \"CENTRO MUNICIPAL DE EDUCACAO INFANTIL DOM BOSCO I\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0244\",\n" +
            "      \"nome\": \"CENTRO MUNICIPAL DE EDUCACAO INFANTIL MARIA DA CONCEICAO COSTA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0232\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL GERALDO TAVARES\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0640\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL BAHA I\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0401\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL ABRIGO FILHOS DO POVO\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0728\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL PROFESSOR CARLOS FORMIGLI\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0708\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL MARISA BAQUEIRO COSTA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"1008\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL FRANCISCO LEITE\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"1017\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL IRMA DULCE\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0813\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL ANTONIO MARTINS DAMASCENO\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0731\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL 22 DE ABRIL\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0732\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL MARIA DA CONCEICAO SANTIAGO IMBASSAHY\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0718\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL DO CALABETAO\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0734\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL DEPUTADO GERSINO COELHO\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0736\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL COMUNITARIA DE SANTA IZABEL\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"1117\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL ANFILOFIO DE CARVALHO\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0749\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL ADROALDO RIBEIRO COSTA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"1003\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL COMUNITARIA DE CANABRAVA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"1004\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL ARTE E ALEGRIA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"1026\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL ADAUTO PEREIRA DE SOUZA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0812\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL SOCIEDADE FRATERNAL\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0965\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL URSULA CATARINO\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0804\",\n" +
            "      \"nome\": \"CENTRO MUNICIPAL DE EDUCACAO INFANTIL JOSE RENATO MONTEIRO LOBATO\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"1040\",\n" +
            "      \"nome\": \"CENTRO MUNICIPAL DE EDUCACAO INFANTIL EDIVALDO MACHADO BOAVENTURA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"1108\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL MARCILIO DIAS\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0141\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL NOSSA SENHORA DOS ANJOS\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0330\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL CAMPINAS DE PIRAJA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0655\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL LUIZA MAHIM\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"1135\",\n" +
            "      \"nome\": \"CENTRO MUNICIPAL DE EDUCACAO INFANTIL ALVARO BAHIA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0130\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL CASA DA PROVIDENCIA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"1036\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL SAO FRANCISCO DE ASSIS\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0504\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL CIDADE DE JEQUIE\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0662\",\n" +
            "      \"nome\": \"CRECHE E PRE ESCOLA PRIMEIRO PASSO CASSANGE\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0136\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL PAROQUIAL DA VITORIA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0134\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL DESPORTIVA SANTA RITA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0161\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL HILDETE LOMANTO\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0913\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL ESTHER FELIX DA SILVA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0612\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL DE SAO CRISTOVAO\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0149\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL COMUNITARIA DA HISTARTE\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0246\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL PROFESSORA MARIA JOSE DE PAULA MOREIRA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0226\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL PROFESSOR FREIRE FILHO\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0414\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL PERO VAZ VELHO\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0154\",\n" +
            "      \"nome\": \"CENTRO MUNICIPAL DE EDUCACAO INFANTIL IACY VAZ FAGUNDES\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0560\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL SENADOR ANTONIO CARLOS MAGALHAES\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0933\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL PAULO MENDES DE AGUIAR\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0947\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL MARIA CONSTANCIA MORAES DE CARVALHO\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0539\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL CENTRO SOCIAL NEUSA NERY\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0158\",\n" +
            "      \"nome\": \"CENTRO MUNICIPAL DE EDUCACAO INFANTIL NOSSA SENHORA DA VITORIA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0119\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL ALLAN KARDEC DA GRACA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0633\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL DO PESCADOR\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0415\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL PIRAJA DA SILVA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0336\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL ENG CARLOS BATALHA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0440\",\n" +
            "      \"nome\": \"CENTRO MUNICIPAL DE EDUCACAO INFANTIL JOSE ADEODATO DE SOUZA FILHO\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0406\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL DR MARCOS VINICIUS VILACA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0654\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL 25 DE JULHO\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0627\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL DE NOVA ESPERANCA PROFESSOR ARX TOURINHO\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0945\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL CORACAO DE JESUS\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0308\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL COMUNITARIA DO BOM JUA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0752\",\n" +
            "      \"nome\": \"CENTRO MUNICIPAL DE EDUCACAO INFANTIL JARDIM BRASILIA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0705\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL DE PERNAMBUES\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0809\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL MANOEL DE ABREU\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0822\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL ESPERANCA DE VIVER\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0106\",\n" +
            "      \"nome\": \"CENTRO MUNICIPAL DE EDUCACAO INFANTIL ABRIGO DO SALVADOR\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0725\",\n" +
            "      \"nome\": \"CENTRO MUNICIPAL DE EDUCACAO INFANTIL CRECHE VOVO ZEZINHO\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"1018\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL SAO FRANCISCO XAVIER\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0142\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL DR FERNANDO MONTANHA PONDE\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0837\",\n" +
            "      \"nome\": \"CENTRO MUNICIPAL DE EDUCACAO INFANTIL UNIDOS DE CASTELO BRANCO\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"1132\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL NOSSA SENHORA DE FATIMA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0746\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL MARIA DOLORES\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"1042\",\n" +
            "      \"nome\": \"CENTRO MUNICIPAL DE EDUCACAO INFANTIL WALDECK ORNELAS\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0917\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL DE FAZENDA COUTOS\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"1107\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL DR ANTONIO CARLOS MAGALHAES\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0111\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL LUIZ ANSELMO\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0345\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL PADRE NORBERTO\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"1123\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL PRESIDENTE MEDICI\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0312\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL FONTE DO CAPIM\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0352\",\n" +
            "      \"nome\": \"CENTRO MUNICIPAL DE EDUCACAO INFANTIL LINDAURA ANDRADE MENDONCA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0341\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL PROFESSOR GUEDES\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0519\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL CONJUNTO ASSISTENCIAL NOSSA SENHORA DE FATIMA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0138\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL MARTAGAO GESTEIRA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0561\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL HOSPITALAR E DOMICILIAR IRMA DULCE\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0829\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL DE PAU DA LIMA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0353\",\n" +
            "      \"nome\": \"CENTRO MUNICIPAL DE EDUCACAO INFANTIL MOSA BERBERT\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0338\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL EDUCADOR PAULO FREIRE\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0236\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL DO URUGUAI\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0911\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL DURVAL PINHEIRO\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0240\",\n" +
            "      \"nome\": \"CENTRO MUNICIPAL DE EDUCACAO INFANTIL DR AUGUSTO LOPES PONTES\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0830\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL DE CASTELO BRANCO\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0155\",\n" +
            "      \"nome\": \"CENTRO MUNICIPAL DE EDUCACAO INFANTIL IEDA BARRADAS CARNEIRO\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0932\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL CONEGO ORLANDO TELES\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0642\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL PADRE CONFA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0115\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL CLEMILDA ANDRADE\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0205\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL SIMOES FILHO\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0421\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL ADALGISA SOUZA PINTO\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0530\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL SAO PEDRO NOLASCO\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0348\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL JAQUEIRA DO CARNEIRO\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0645\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL VINICIUS DE MORAES\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0133\",\n" +
            "      \"nome\": \"ESCOLA SAO JOSE ANEXA AO COLEGIO SANTISSIMO SACRAMENTO\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0620\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL PADRE UGO MEREGALLI\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0616\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL JUAREZ GOES DE SOUZA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0245\",\n" +
            "      \"nome\": \"CENTRO MUNICIPAL DE EDUCACAO INFANTIL NOSSA SENHORA DAS GRACAS\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0143\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL SOROR JOANA ANGELICA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0634\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL OSVALDO GORDILHO\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0307\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL AMAI PRO\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0666\",\n" +
            "      \"nome\": \"CRECHE E PRE ESCOLA PRIMEIRO PASSO ITAPUA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0701\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL CENTRO COMUNITARIO FREI LEONIDAS MENEZES\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0704\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL EPAMINONDAS BERBERT CASTRO\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0356\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL DO CALAFATE\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0801\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL ALLAN KARDEC DE PIRAJA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0832\",\n" +
            "      \"nome\": \"CENTRO MUNICIPAL DE EDUCACAO INFANTIL ANIVAL RAZONI FIGUEIREDO\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"1012\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL MARIA ANTONIETA ALFARANO\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0501\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL ARTUR DE SALES\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0722\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL PROFª ANFRISIA SANTIAGO\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0305\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL BATISTA DE SAO CAETANO\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0515\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL TEODORO SAMPAIO\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"1046\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL 2 DE JULHO\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0824\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL SYD PORTO BRANDAO\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"1007\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL DE CAJAZEIRAS XI\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0344\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL AUSTRICLIANO DE CARVALHO\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0906\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL SENADOR ANTONIO CARLOS PEIXOTO DE MAGALHAES\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0342\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL CRIANCA FELIZ\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0942\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL DARCY RIBEIRO\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0613\",\n" +
            "      \"nome\": \"CENTRO MUNICIPAL DE EDUCACAO INFANTIL CSU DE MUSSURUNGA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0237\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL SANTA BARBARA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0839\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL ZILDA ARNS\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0355\",\n" +
            "      \"nome\": \"CENTRO MUNICIPAL DE EDUCACAO INFANTIL TEREZA HELENA MATA PIRES\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0805\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL D ARLETE MAGALHAES\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0803\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL CONSELHEIRO LUIZ ROGERIO\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0120\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL OLGA FIGUEIREDO DE AZEVEDO\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0635\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL LAURA SALES DE ALMEIDA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0840\",\n" +
            "      \"nome\": \"CRECHE E PRE ESCOLA PRIMEIRO PASSO SAO MARCOS\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0807\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL GENERAL LABATUT\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0135\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL PERMINIO LEITE\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0128\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL TEREZINHA VAZ DA SILVEIRA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0941\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL SENHOR DO BONFIM\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0964\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL SAO BRAZ\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0605\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL CIDADE VITORIA DA CONQUISTA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0617\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL ANJOS DE RUA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0427\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL MANOEL FLORENCIO\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0912\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL DE PLATAFORMA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"1125\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL DE PERIPERI\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0927\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL DE ITACARANHA MANOEL FAUSTINO\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0239\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL CARMELITANA DO MENINO JESUS\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0152\",\n" +
            "      \"nome\": \"CENTRO MUNICIPAL DE EDUCACAO INFANTIL LUIS EDUARDO MAGALHAES\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0551\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL ZULMIRA TORRES\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0548\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL IACY VAZ FAGUNDES\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0625\",\n" +
            "      \"nome\": \"CENTRO MUNICIPAL DE EDUCACAO INFANTIL IRMA SHEILA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0638\",\n" +
            "      \"nome\": \"CENTRO MUNICIPAL DE EDUCACAO INFANTIL JOAQUIM SANTOS\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0652\",\n" +
            "      \"nome\": \"CENTRO MUNICIPAL DE EDUCACAO INFANTIL UNIAO DA BOCA DO RIO\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0422\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL ZACARIAS BOA MORTE\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0601\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL AGNELO DE BRITO\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0946\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL SANTA LUZIA DO LOBATO\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0648\",\n" +
            "      \"nome\": \"CENTRO MUNICIPAL DE EDUCACAO INFANTIL ALMIR OLIVEIRA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0629\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL JUIZ OSCAR MESQUITA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0507\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL HERCILIA MOREIRA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0431\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL BARAO DO RIO BRANCO\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0706\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL HILDETE BAHIA DE SOUZA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0709\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL EUGENIA ANNA DOS SANTOS\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0710\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL MURILO CELESTINO COSTA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0818\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL JOAO FERREIRA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0808\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL MANOEL CLEMENTE FERREIRA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0714\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL ALVARO DA FRANCA ROCHA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0715\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL CABULA I\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0723\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL LEOVICIA ANDRADE\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0730\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL ANTONIO EUZEBIO\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0833\",\n" +
            "      \"nome\": \"CENTRO MUNICIPAL DE EDUCACAO INFANTIL EDUARDO FREITAS\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0903\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL ARMANDO CARNEIRO DA ROCHA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"1045\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL ELYSIO ATHAYDE\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"1014\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL RECANTO DO SOL\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0750\",\n" +
            "      \"nome\": \"CRECHE E PRE ESCOLA PRIMEIRO PASSO MATA ESCURA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0740\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL MARIA FELIPA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"1102\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL ALVARO VASCONCELOS DA ROCHA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"1105\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL DE SANTANA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"1114\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL FRANCISCA DE SANDE\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0556\",\n" +
            "      \"nome\": \"CENTRO MUNICIPAL DE EDUCACAO INFANTIL NOVA ESPERANCA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0641\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL CELIA NOGUEIRA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0544\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL SANTA TEREZINHA DO CHAME CHAME\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0127\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL AMELIA RODRIGUES\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0113\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL JOIR BRASILEIRO\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0811\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL ROBERTO CORREIA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0828\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL AFRANIO PEIXOTO\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0810\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL PROFª ALEXANDRINA SANTOS PITA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"1019\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL IRACI FRAGA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0611\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL LAGOA DO ABAETE\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0977\",\n" +
            "      \"nome\": \"CRECHE E PRE ESCOLA PRIMEIRO PASSO ILHA AMARELA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0664\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL BOSQUE DAS BROMELIAS\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0541\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL ANA NERY\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0628\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL RAYMUNDO LEMOS DE SANTANA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0905\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL SANTA TEREZINHA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0204\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL CONSTANCA MEDEIROS\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0354\",\n" +
            "      \"nome\": \"CENTRO MUNICIPAL DE EDUCACAO INFANTIL SEMENTE DO AMANHA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0151\",\n" +
            "      \"nome\": \"CENTRO MUNICIPAL DE EDUCACAO INFANTIL DRº ELIEZER AUDIFACE\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0547\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL MARIA AMALIA PAIVA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0212\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL HILBERTO SILVA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0241\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL ELOYNA BARRADAS\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0238\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL CENTRO SOCIAL MANGUEIRA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0435\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL CARDEAL DA SILVA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0523\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL BATISTA VASCO DA GAMA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"1136\",\n" +
            "      \"nome\": \"CENTRO MUNICIPAL DE EDUCACAO INFANTIL HELIO VIANNA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0661\",\n" +
            "      \"nome\": \"CRECHE E PRE ESCOLA PRIMEIRO PASSO PARQUE SAO CRISTOVAO\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0532\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL CRISTO E VIDA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0653\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL YVES DE ROUSSAN\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0974\",\n" +
            "      \"nome\": \"CENTRO MUNICIPAL DE EDUCACAO INFANTIL ABDIAS NASCIMENTO\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0520\",\n" +
            "      \"nome\": \"CENTRO MUNICIPAL DE EDUCACAO INFANTIL SAO GONCALO\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0656\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL JORGE AMADO\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0557\",\n" +
            "      \"nome\": \"CENTRO MUNICIPAL DE EDUCACAO INFANTIL PIO BITTENCOURT\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0227\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL SOCIEDADE TOME DE SOUZA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0140\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL VIVALDO DA COSTA LIMA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0643\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL METODISTA SUSANA WESLEY\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0230\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL MELVIN JONES\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0621\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL BARBOSA ROMEO\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"1028\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL DRA MARIA DO CARMO VILACA\"\n" +
            "   },\n" +
            "      {\n" +
            "      \"codigo\": \"0508\",\n" +
            "      \"nome\": \"ESCOLA MUNICIPAL MADRE JUDITE\"\n" +
            "   }\n" +
            "]}");
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
    public static ProgressDialog criarProgressDialog(Context context, String title){
        ProgressDialog progressDialog = null;

        try
        {
            // Cria a tela de progresso
            progressDialog = new ProgressDialog(context);
            progressDialog.setTitle(title);
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
    public static ProgressDialog criarProgressDialog(Context context)
    {
        return criarProgressDialog(context, context.getString(R.string.atencao));
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
                sDadoRetorno = "Previsoes limite de conexao atingido!";
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
    public static String retornaSexoShort(String sCodigo) throws Exception
    {
        String sSexo = "";

        //Se for feminino
        if ( sCodigo.equals("FEMININO") )
        {
            sSexo = "F";
        }
        //Se for masculino
        else if ( sCodigo.equals("MASCULINO") )
        {
            sSexo = "M";
        }

        return sSexo;
    }

    public static String retornaAtivoOuNao(String sCodigo)
    {
        String sNome = "";

        //Se for 0
        if ( sCodigo.equals("0") )
        {
            sNome = "NÃO";
        }
        //ou caso contrario
        else
        {
            sNome = "NÃO";
        }

        return sNome;
    }

    public static String retornaJustificativa(String sCodigo) {
        String sNome = "";

        //Se for 0
        if (sCodigo.equals("1")) {
            sNome = "Desistência da vaga pelo responsável";
        }
        else if(sCodigo.equals("2")){
            sNome = "Não comprovou critério do Bolsa Família";
        }
        else if(sCodigo.equals("3"))
        {
            sNome = "Não comprovou critério de Deficiência";
        }
        else if(sCodigo.equals("4"))
        {
            sNome = "Não comprovou critério de Gêmeos";

        }
        else if(sCodigo.equals("5"))
        {
            sNome = "Impossibilidade de contato com responsável";
        }
        else
            sNome = "";
        return sNome;
    }

    public static String retornaStatus(String sCodigo) {
        String sNome = "";

        //Se for 0
        if (sCodigo.equals("1")) {
            sNome = "Matriculado!";
        }
        else
            sNome = "Não Matriculado!";

        return sNome;
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