package br.com.ca.gpmcaieapp.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class Multas {

    private String json_raw = "";
    private String cns = null;
    private Integer totalPts = null;
    private HashMap<Integer,Multa> lista;
    private JSONObject jsonObjectRetornoDados;

    public Integer getTotalPts() {
        return totalPts;
    }

    public void setTotalPts(Integer totalPts) {
        this.totalPts = totalPts;
    }

    public static enum CategoryEnum {
            NUMBER,
            VALUE,
            STATUS};

    public Multas(JSONObject jsonObjectRetornoDados){
        this.jsonObjectRetornoDados = jsonObjectRetornoDados;
        this.lista = new HashMap<Integer,Multa>();
    }

    public void create(){
        try {
            JSONObject object = jsonObjectRetornoDados;
            this.cns = object.getString("cnh");
            this.setTotalPts(object.getInt("total_pts"));
            JSONArray Jarray  = object.getJSONArray("multas");

            for (int i = 0; i < Jarray.length(); i++)
            {
                JSONObject jsonObj = Jarray.getJSONObject(i);
                Integer id = jsonObj.getInt("id");
                String desc = jsonObj.getString("desc");
                Integer pts = jsonObj.getInt("pts");

                Multa multa = new Multa(id, desc, pts);
                this.lista.put(id, multa);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Multa getMulta(Integer id){
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
    public ArrayList<Multa> getMultaList() {
        ArrayList<Multa> multas = new ArrayList<Multa>();

        Set set = this.lista.entrySet();
        Iterator iterator = set.iterator();

        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            Multa multa = (Multa) entry.getValue();
            multas.add(multa);
        }

        return multas;
    }


}
