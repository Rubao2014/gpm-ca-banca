package br.gov.al.itec.gpmapp.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class DependentesBeneficiarios {

    private String json_raw = "";
    private HashMap<String,Beneficiario> lista;

    public DependentesBeneficiarios(String json){

        this.json_raw = json;
        this.lista = new HashMap<String,Beneficiario>();
    }

    public void Create(){


        try {

            JSONObject object = new JSONObject(json_raw);
            JSONObject jsonObject = object.getJSONObject("contrato");

            //TITULAR
            JSONArray jsonArray  = object.getJSONArray("titular");
            JSONObject objBeneficiario = jsonArray.getJSONObject(0); //indice 0 - titular primero da lista
            Beneficiario b = new Beneficiario();
            b.addDetais(objBeneficiario.toString());
            this.lista.put(b.getTxtNome(),b);

            //DEPENDENTES
            jsonArray  = object.getJSONArray("dependentes");

            for (int i = 0; i < jsonArray.length(); i++)
            {

                b = new Beneficiario();
                b.addDetais(jsonArray.getJSONObject(i).toString());
                this.lista.put(b.getTxtNome(),b);


            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public Beneficiario getParticipant(String i){

        return (this.lista.get(i));

    }

    public ArrayList<String> getBeneficiariosStringList(String list_type){

        ArrayList<String> al = new ArrayList<String>();

        /* Display content using Iterator*/
        Set set = this.lista.entrySet();
        Iterator iterator = set.iterator();

        while(iterator.hasNext()) {
            Map.Entry mentry = (Map.Entry)iterator.next();
            System.out.print("key is: "+ mentry.getKey() + " & Value is: ");
            Beneficiario b = (Beneficiario) mentry.getValue();

            switch (list_type){

                case "nome":
                    System.out.println("Nome: "+ mentry.getKey().toString());
                    al.add(mentry.getKey().toString());
                    break;
                case "plano_odontologico":
                    System.out.println("Plano: "+b.getTxtPlanoOdontologico());
                    //Adcionar à lista
                    al.add(b.getTxtPlanoOdontologico());
                    break;
                case "situacao":
                    System.out.println("Situacao: "+b.getTxtSituacao());
                    //Adcionar à lista
                    al.add(b.getTxtSituacao());
                    break;
            }

        }

        return al;
    }




}
