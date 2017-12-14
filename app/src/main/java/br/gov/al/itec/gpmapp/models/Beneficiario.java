package br.gov.al.itec.gpmapp.models;

/**
 * Created by Rubens on 06/12/16.
 */

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import br.gov.al.itec.gpmapp.util.Apoio;


public class Beneficiario implements Serializable {

    private String txtNome;
    private String txtPlanoOdontologico;
    private String txtSituacao;

    public String getTxtNome() {
        return txtNome;
    }

    public void setTxtNome(String txtNome) {
        this.txtNome = txtNome;
    }

    public String getTxtPlanoOdontologico() {
        return txtPlanoOdontologico;
    }

    public void setTxtPlanoOdontologico(String txtPlanoOdontologico) {
        this.txtPlanoOdontologico = txtPlanoOdontologico;
    }

    public String getTxtSituacao() {
        return txtSituacao;
    }

    public void setTxtSituacao(String txtSituacao) {
        this.txtSituacao = txtSituacao;
    }

    public Beneficiario(String nome, String planoOdontologico, String status){

        this.txtNome = nome;
        this.txtPlanoOdontologico = status;
        this.txtSituacao = status;
    }
    public Beneficiario(){

        this.txtNome = "";
        this.txtPlanoOdontologico = "";
        this.txtSituacao = "";
    }

    public void addDetais(String json_raw){


        if (json_raw.isEmpty()) return;

        if (Apoio.isJSONValid(json_raw)) {

            try {
                JSONObject object = new JSONObject(json_raw);
                this.setTxtNome(object.getString("nome"));
                this.setTxtPlanoOdontologico(object.getString("plano_odontologico"));
                this.setTxtSituacao(object.getString("situacao"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}

