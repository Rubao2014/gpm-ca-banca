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




public class Matriculas {

    private String json_raw = "";
    private HashMap<String,Matricula> lista;
    public static enum CategoryEnum {
            NUMBER,
            VALUE,
            STATUS};

    public Matriculas(String json){

        this.json_raw = json;
        this.lista = new HashMap<String,Matricula>();
    }

    public void Create(){


        try {

            JSONObject object = new JSONObject(json_raw);
            JSONArray Jarray  = object.getJSONArray("inscricoes");

            for (int i = 0; i < Jarray.length(); i++)
            {
                //JSONObject Jasonobject = Jarray.getJSONObject(i);
                //String  nr = Jasonobject.getString("nr_matricula");
                //String  valor =Jasonobject.getString("valor");
                //String  status=Jasonobject.getString("status");
                String nr = Jarray.getString(i);
                Log.d("Item " + i+ ":", nr  );
                this.lista.put(nr,new Matricula(nr));

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Matricula getMatricula(String nr){

        return (this.lista.get(nr));

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
    public ArrayList<String> getMatriculasStringList (CategoryEnum list_type){

        ArrayList<String> al = new ArrayList<String>();

        /* Display content using Iterator*/
        Set set = this.lista.entrySet();
        Iterator iterator = set.iterator();

        while(iterator.hasNext()) {
            Map.Entry mentry = (Map.Entry)iterator.next();
            System.out.print("key is: "+ mentry.getKey() + " & Value is: ");
            Matricula c = (Matricula) mentry.getValue();

            switch (list_type){

                case NUMBER:
                    System.out.println("Número da Inscrição: "+ mentry.getKey().toString());
                    //Adcionar à lista
                    al.add(mentry.getKey().toString());
                    break;
                case STATUS:
                    System.out.println("Status: "+c.getStatusValue().toString());
                    //Adcionar à lista
                    al.add(c.getStatusValue().toString());
                    break;

            }

        }

        return al;
    }




}
