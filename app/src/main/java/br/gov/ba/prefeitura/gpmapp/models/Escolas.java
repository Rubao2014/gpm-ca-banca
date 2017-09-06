package br.gov.ba.prefeitura.gpmapp.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Rubens on 05/09/17.
 */

public class Escolas {

    private HashMap<String,String> lista;
    private String lstCompletaEscola = "{\"escolas\": [\n" +
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
            "]}";

    public Escolas() {

        lista = new HashMap<String, String>();
    }

    public HashMap<String, String> getLista() {
        return lista;
    }

    public void Create(){


        try {

            JSONObject object = new JSONObject(lstCompletaEscola);
            JSONArray Jarray  = object.getJSONArray("escolas");

            for (int i = 0; i < Jarray.length(); i++)
            {
                JSONObject Jasonobject = Jarray.getJSONObject(i);
                String  sCodigo = Jasonobject.getString("codigo");
                String  sNome = Jasonobject.getString("nome");
                //Log.d("Escola " + i + ":", sCodigo + " " +  sNome );
                this.lista.put(sCodigo,sNome);

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



}
