package br.com.ca.gpmcaieapp.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class Vacinas {

    private String json_raw = "";
    private HashMap<Integer,Vacina> lista;
    private JSONObject jsonObjectRetornoDados;

    public static enum CategoryEnum {
            NUMBER,
            VALUE,
            STATUS};

    public Vacinas(JSONObject jsonObjectRetornoDados){
        this.jsonObjectRetornoDados = jsonObjectRetornoDados;
        this.lista = new HashMap<Integer,Vacina>();
    }

    public void Create(){
        try {
            JSONObject object = jsonObjectRetornoDados;
            String cns = object.getString("cns");
            JSONArray Jarray  = object.getJSONArray("vacinas_cns");

            for (int i = 0; i < Jarray.length(); i++)
            {
                JSONObject jsonObj = Jarray.getJSONObject(i);
                Integer id = jsonObj.getInt("id");
                String desc = jsonObj.getString("desc");
                Integer flag = jsonObj.getInt("flag");

                Vacina vacina = new Vacina(id, cns, desc, flag);
                this.lista.put(id, vacina);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Vacina getVacina(Integer id){
        return (this.lista.get(id));
    }

    /*
     getContractsStringList
     returns the list of contracts in an arraylist
     Parameters :
        whichone
        0 :  contracts list
        1 :  values list
        2 :  statuses list
     */
    public ArrayList<Vacina> getVacinasList() {
        ArrayList<Vacina> vacinas = new ArrayList<Vacina>();

        Set set = this.lista.entrySet();
        Iterator iterator = set.iterator();

        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            Vacina vacina = (Vacina) entry.getValue();
            vacinas.add(vacina);
        }

        return vacinas;
    }





}
