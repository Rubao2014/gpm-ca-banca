package br.gov.al.itec.gpmapp.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Rubens on 06/09/17.
 [{
 "previsao": 40,
 "descricao_detalhada": "Nebulosidade variável e chuva ocasional durante o período",
 "temperatura_maxima": 33,
 "umidade_relativa_maxima": 87,
 "temperatura_minima": 22,
 "data": "2017-12-11",
 "umidade_relativa_minima": 44,
 "regiao": "1",
 "condicao_atual": "NO NORDESTE DO BRASIL OBSERVA-SE NEBULOSIDADE SIGNIFICATIVA NA MAIOR PARTE DA BAHIA, MARANHÃO E PIAUÍ, SUL DO CEARÁ E EM PONTOS ISOLADOS DE SERGIPE, ALAGOAS, PERNAMBUCO, PARAÍBA E RIO GRANDE DO NORTE (IMAGEM METEOSAT-10 DE 11/12/17  ( 09:00 UTM)",
 "cidade": 2,
 "uv": 13,
 "direcao_vento": "NE",
 "intensidade_vento": "FR"
 }]
 */

public class Previsoes {


    private List<Tempo> lista;
    private String jsonSeries = "";
    private HashMap<String,String> listaCidades;
    private String lstCompletaCidades = "{\n" +
            "\t\"cidades\": [{\n" +
            "\t\t\t\"codigo\": 1,\n" +
            "\t\t\t\"nome\": \"ÁGUA BRANCA\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 2,\n" +
            "\t\t\t\"nome\": \"ANADIA\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 3,\n" +
            "\t\t\t\"nome\": \"ARAPIRACA\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 4,\n" +
            "\t\t\t\"nome\": \"ATALAIA\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 5,\n" +
            "\t\t\t\"nome\": \"BARRA DE SANTO ANTÔNIO\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 6,\n" +
            "\t\t\t\"nome\": \"BARRA DE SÃO MIGUEL\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 7,\n" +
            "\t\t\t\"nome\": \"BATALHA\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 8,\n" +
            "\t\t\t\"nome\": \"BELÉM\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 9,\n" +
            "\t\t\t\"nome\": \"BELO MONTE\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 10,\n" +
            "\t\t\t\"nome\": \"BOCA DA MATA\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 11,\n" +
            "\t\t\t\"nome\": \"BRANQUINHA\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 12,\n" +
            "\t\t\t\"nome\": \"CACIMBINHAS\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 13,\n" +
            "\t\t\t\"nome\": \"CAJUEIRO\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 14,\n" +
            "\t\t\t\"nome\": \"CAMPESTRE\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 15,\n" +
            "\t\t\t\"nome\": \"CAMPO ALEGRE\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 16,\n" +
            "\t\t\t\"nome\": \"CAMPO GRANDE\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 17,\n" +
            "\t\t\t\"nome\": \"CANAPI\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 18,\n" +
            "\t\t\t\"nome\": \"CAPELA\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 19,\n" +
            "\t\t\t\"nome\": \"CARNEIROS\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 20,\n" +
            "\t\t\t\"nome\": \"CHÃ PRETA\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 21,\n" +
            "\t\t\t\"nome\": \"COITÉ DO NÓIA\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 22,\n" +
            "\t\t\t\"nome\": \"COLÔNIA LEOPOLDINA\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 23,\n" +
            "\t\t\t\"nome\": \"COQUEIRO SECO\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 24,\n" +
            "\t\t\t\"nome\": \"CORURIPE\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 25,\n" +
            "\t\t\t\"nome\": \"CRAÍBAS\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 26,\n" +
            "\t\t\t\"nome\": \"DELMIRO GOUVEIA\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 27,\n" +
            "\t\t\t\"nome\": \"DOIS RIACHOS\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 28,\n" +
            "\t\t\t\"nome\": \"ESTRELA DE ALAGOAS\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 29,\n" +
            "\t\t\t\"nome\": \"FEIRA GRANDE\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 30,\n" +
            "\t\t\t\"nome\": \"FELIZ DESERTO\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 31,\n" +
            "\t\t\t\"nome\": \"FLEXEIRAS\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 32,\n" +
            "\t\t\t\"nome\": \"GIRAU DO PONCIANO\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 33,\n" +
            "\t\t\t\"nome\": \"IBATEGUARA\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 34,\n" +
            "\t\t\t\"nome\": \"IGACI\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 35,\n" +
            "\t\t\t\"nome\": \"IGREJA NOVA\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 36,\n" +
            "\t\t\t\"nome\": \"INHAPI\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 37,\n" +
            "\t\t\t\"nome\": \"JACARÉ DOS HOMENS\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 38,\n" +
            "\t\t\t\"nome\": \"JACUÍPE\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 39,\n" +
            "\t\t\t\"nome\": \"JAPARATINGA\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 40,\n" +
            "\t\t\t\"nome\": \"JARAMATAIA\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 41,\n" +
            "\t\t\t\"nome\": \"JOAQUIM GOMES\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 42,\n" +
            "\t\t\t\"nome\": \"JEQUIÁ DA PRAIA\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 43,\n" +
            "\t\t\t\"nome\": \"JUNDIÁ\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 44,\n" +
            "\t\t\t\"nome\": \"JUNQUEIRO\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 45,\n" +
            "\t\t\t\"nome\": \"LAGOA DA CANOA\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 46,\n" +
            "\t\t\t\"nome\": \"LIMOEIRO DE ANADIA\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 47,\n" +
            "\t\t\t\"nome\": \"MACEIÓ\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 48,\n" +
            "\t\t\t\"nome\": \"MAJOR ISIDORO\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 49,\n" +
            "\t\t\t\"nome\": \"MAR VERMELHO\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 50,\n" +
            "\t\t\t\"nome\": \"MARAGOGI\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 51,\n" +
            "\t\t\t\"nome\": \"MARAVILHA\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 52,\n" +
            "\t\t\t\"nome\": \"MARECHAL DEODORO\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 53,\n" +
            "\t\t\t\"nome\": \"MARIBONDO\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 54,\n" +
            "\t\t\t\"nome\": \"MATA GRANDE\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 55,\n" +
            "\t\t\t\"nome\": \"MATRIZ DE CAMARAGIBE\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 56,\n" +
            "\t\t\t\"nome\": \"MESSIAS\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 57,\n" +
            "\t\t\t\"nome\": \"MINADOR DO NEGRÃO\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 58,\n" +
            "\t\t\t\"nome\": \"MONTEIRÓPOLIS\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 59,\n" +
            "\t\t\t\"nome\": \"MURICI\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 60,\n" +
            "\t\t\t\"nome\": \"NOVO LINO\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 61,\n" +
            "\t\t\t\"nome\": \"OLHO D'ÁGUA GRANDE\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 62,\n" +
            "\t\t\t\"nome\": \"OLHO D'ÁGUA DAS FLORES\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 63,\n" +
            "\t\t\t\"nome\": \"OLHO D'ÁGUA DO CASADO\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 64,\n" +
            "\t\t\t\"nome\": \"OLIVENÇA\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 65,\n" +
            "\t\t\t\"nome\": \"OURO BRANCO\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 66,\n" +
            "\t\t\t\"nome\": \"PALESTINA\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 67,\n" +
            "\t\t\t\"nome\": \"PALMEIRA DOS ÍNDIOS\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 68,\n" +
            "\t\t\t\"nome\": \"PÃO DE AÇÚCAR\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 69,\n" +
            "\t\t\t\"nome\": \"PARICONHA\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 70,\n" +
            "\t\t\t\"nome\": \"PARIPUEIRA\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 71,\n" +
            "\t\t\t\"nome\": \"PASSO DE CAMARAGIBE\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 72,\n" +
            "\t\t\t\"nome\": \"PAULO JACINTO\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 73,\n" +
            "\t\t\t\"nome\": \"PENEDO\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 74,\n" +
            "\t\t\t\"nome\": \"PIAÇABUÇU\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 75,\n" +
            "\t\t\t\"nome\": \"PILAR\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 76,\n" +
            "\t\t\t\"nome\": \"PINDOBA\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 77,\n" +
            "\t\t\t\"nome\": \"PIRANHAS\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 78,\n" +
            "\t\t\t\"nome\": \"POÇO DAS TRINCHEIRAS\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 79,\n" +
            "\t\t\t\"nome\": \"PORTO CALVO\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 80,\n" +
            "\t\t\t\"nome\": \"PORTO REAL DO COLÉGIO\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 81,\n" +
            "\t\t\t\"nome\": \"PORTO DE PEDRAS\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 82,\n" +
            "\t\t\t\"nome\": \"QUEBRANGULO\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 83,\n" +
            "\t\t\t\"nome\": \"RIO LARGO\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 84,\n" +
            "\t\t\t\"nome\": \"ROTEIRO\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 85,\n" +
            "\t\t\t\"nome\": \"SANTA LUZIA DO NORTE\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 86,\n" +
            "\t\t\t\"nome\": \"SANTANA DO IPANEMA\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 87,\n" +
            "\t\t\t\"nome\": \"SANTANA DO MUNDAÚ\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 88,\n" +
            "\t\t\t\"nome\": \"SÃO BRÁS\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 89,\n" +
            "\t\t\t\"nome\": \"SÃO JOSÉ DA LAJE\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 90,\n" +
            "\t\t\t\"nome\": \"SÃO JOSÉ DA TAPERA\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 91,\n" +
            "\t\t\t\"nome\": \"SÃO LUIS DO QUITUNDE\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 92,\n" +
            "\t\t\t\"nome\": \"SÃO MIGUEL DOS CAMPOS\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 93,\n" +
            "\t\t\t\"nome\": \"SÃO MIGUEL DOS MILAGRES\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 94,\n" +
            "\t\t\t\"nome\": \"SÃO SEBASTIÃO\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 95,\n" +
            "\t\t\t\"nome\": \"SATUBA\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 96,\n" +
            "\t\t\t\"nome\": \"SENADOR RUI PALMEIRA\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 97,\n" +
            "\t\t\t\"nome\": \"TANQUE D'ARCA\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 98,\n" +
            "\t\t\t\"nome\": \"TAQUARANA\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 99,\n" +
            "\t\t\t\"nome\": \"TEOTÔNIO VILELA\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 100,\n" +
            "\t\t\t\"nome\": \"TRAIPÚ\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 101,\n" +
            "\t\t\t\"nome\": \"UNIÃO DOS PALMARES\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"codigo\": 102,\n" +
            "\t\t\t\"nome\": \"VIÇOSA\"\n" +
            "\t\t}\n" +
            "\t]\n" +
            "}";


    public Previsoes(String json) {
        jsonSeries = json;
        lista = new ArrayList<Tempo>();
        listaCidades = new HashMap<String,String>();
    }

    public Previsoes() {
        jsonSeries = null;
        lista = new ArrayList<Tempo>();
        listaCidades = new HashMap<String,String>();
    }

    public String getJsonSeries() {
        return jsonSeries;
    }

    public void setJsonSeries(String jsonSeries) {
        this.jsonSeries = jsonSeries;
    }

    public HashMap<String, String> getListaCidades() {
        return listaCidades;
    }

    public void setListaCidades(HashMap<String, String> listaCidades) {
        this.listaCidades = listaCidades;
    }

    public String getLstCompletaCidades() {
        return lstCompletaCidades;
    }

    public void setLstCompletaCidades(String lstCompletaCidades) {
        this.lstCompletaCidades = lstCompletaCidades;
    }

    public String getEscola(String codigo){
        return listaCidades.get(codigo);
    }
    public void populateHashListaCompletaCidade(){
        try {

            JSONObject object = new JSONObject(lstCompletaCidades);
            JSONArray Jarray  = object.getJSONArray("cidades");

            for (int i = 0; i < Jarray.length(); i++)
            {
                JSONObject Jasonobject = Jarray.getJSONObject(i);
                String  sCodigo = String.valueOf(Jasonobject.getInt("codigo"));
                String  sNome = Jasonobject.getString("nome");
                //Log.d("Escola " + i + ":", sCodigo + " " +  sNome );
                this.listaCidades.put(sCodigo,sNome);

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


    public void Create(){


        try {

            JSONArray Jarray  = new JSONArray(jsonSeries);

            for (int i = 0; i < Jarray.length(); i++)
            {
                JSONObject Jasonobject = Jarray.getJSONObject(i);
                Tempo t = new Tempo();
                t.setPrevisao(Jasonobject.getInt("Previsao"));
                t.setDescricao_detalhada(Jasonobject.getString("descricao_detalhada"));
                t.setTemperatura_maxima(Jasonobject.getInt("temperatura_maxima"));
                t.setUmidade_relativa_maxima(Jasonobject.getInt("umidade_relativa_maxima"));
                t.setData(Jasonobject.getString("data"));
                t.setUmidade_relativa_minima(Jasonobject.getInt("umidade_relativa_minima"));
                t.setRegiao(Jasonobject.getString("regiao"));
                t.setCondicao_atual(Jasonobject.getString("condicao_atual"));
                t.setCidade(Jasonobject.getString("cidade"));
                t.setUv(Jasonobject.getInt("uv"));
                t.setDirecao_vento(Jasonobject.getString("direcao_vento"));
                t.setIntensidade_vento(Jasonobject.getString("intensidade_vento"));
                this.lista.add(t);


            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
