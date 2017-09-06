package br.gov.ba.prefeitura.gpmapp.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Rubens on 06/09/17.
 */

public class Series {


    private HashMap<String,String> lista;
    private String jsonSeries = "";
    private String[] spinnerCodigosSeries = null;
    private String[] spinnerTitulosSeries = null;

    public Series(String json) {
        jsonSeries = json;
        lista = new HashMap<String, String>();
        spinnerCodigosSeries = new String[0];
        spinnerTitulosSeries = new String[0];
    }

    public HashMap<String, String> getLista() {
        return lista;
    }

    public void Create(){


        try {

            JSONObject object = new JSONObject(jsonSeries);
            JSONArray Jarray  = object.getJSONArray("series");

            spinnerCodigosSeries = new String[Jarray.length()];
            spinnerTitulosSeries = new String[Jarray.length()];

            for (int i = 0; i < Jarray.length(); i++)
            {
                JSONObject Jasonobject = Jarray.getJSONObject(i);
                String  sSeq = Jasonobject.getString("serie_seq");
                String  sAnoEscolar = Jasonobject.getString("ano_escolarizacao");
                Log.d("Serie " + i + ":", sSeq + " " +  sAnoEscolar );
                this.lista.put(sSeq,sAnoEscolar);
                spinnerCodigosSeries[i] = sSeq;
                spinnerTitulosSeries[i] = sAnoEscolar;

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String[] getTitulosSeries(){

        return spinnerTitulosSeries;
    }
    public String[] getCodigosSeries(){

        return spinnerCodigosSeries;
    }

}
