package br.gov.al.itec.gpmapp.models;

/**
 * Created by Rubens on 06/12/16.
 */

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import br.gov.al.itec.gpmapp.util.Apoio;

/*
        txtInscricaoValue
        txtStatusValue
        txtAnoInscricaoValue
        txtNomeAlunoValue
        txtNomeResponsavel01value
        txtNomeResponsavel02value
        txtDataNascimentoValue
        txtEscola
        txtSerieValue
        txtBolsaFamiliaValue
        txtIrmaoGemeoValue
        txtJustificativaValue

    */
public class Matricula implements Serializable {

    private String txtInscricaoValue;
    private String txtStatusValue;
    private String txtAnoInscricaoValue;
    private String txtNomeAlunoValue;
    private String txtNomeResponsavel01value;
    private String txtNomeResponsavel02value;
    private String txtDataNascimentoValue;
    private String txtEscolaValue;
    private String txtSerieValue;
    private String txtBolsaFamiliaValue;
    private String txtIrmaoGemeoValue;
    private String txtJustificativaValue;


    public String getTxtInscricaoValue() {
        return txtInscricaoValue;
    }

    public String getTxtStatusValue() {
        return txtStatusValue;
    }

    public String getTxtAnoInscricaoValue() {
        return txtAnoInscricaoValue;
    }

    public String getTxtNomeAlunoValue() {
        return txtNomeAlunoValue;
    }

    public String getTxtNomeResponsavel01value() {
        return txtNomeResponsavel01value;
    }

    public String getTxtNomeResponsavel02value() {
        return txtNomeResponsavel02value;
    }

    public String getTxtDataNascimentoValue() {
        return txtDataNascimentoValue;
    }

    public String getTxtEscolaValue() {
        return txtEscolaValue;
    }

    public String getTxtSerieValue() {
        return txtSerieValue;
    }

    public String getTxtBolsaFamiliaValue() {
        return txtBolsaFamiliaValue;
    }

    public String getTxtIrmaoGemeoValue() {
        return txtIrmaoGemeoValue;
    }

    public String getTxtJustificativaValue() {
        return txtJustificativaValue;
    }

    public void setTxtInscricaoValue(String txtInscricaoValue) {
        this.txtInscricaoValue = txtInscricaoValue;
    }

    public void setTxtStatusValue(String txtStatusValue) {
        this.txtStatusValue = txtStatusValue;
    }

    public void setTxtAnoInscricaoValue(String txtAnoInscricaoValue) {
        this.txtAnoInscricaoValue = txtAnoInscricaoValue;
    }

    public void setTxtNomeAlunoValue(String txtNomeAlunoValue) {
        this.txtNomeAlunoValue = txtNomeAlunoValue;
    }

    public void setTxtNomeResponsavel01value(String txtNomeResponsavel01value) {
        this.txtNomeResponsavel01value = txtNomeResponsavel01value;
    }

    public void setTxtNomeResponsavel02value(String txtNomeResponsavel02value) {
        this.txtNomeResponsavel02value = txtNomeResponsavel02value;
    }

    public void setTxtDataNascimentoValue(String txtDataNascimentoValue) {
        this.txtDataNascimentoValue = txtDataNascimentoValue;
    }

    public void setTxtEscolaValue(String txtEscolaValue) {
        this.txtEscolaValue = txtEscolaValue;
    }

    public void setTxtSerieValue(String txtSerieValue) {
        this.txtSerieValue = txtSerieValue;
    }

    public void setTxtBolsaFamiliaValue(String txtBolsaFamiliaValue) {
        this.txtBolsaFamiliaValue = txtBolsaFamiliaValue;
    }

    public void setTxtIrmaoGemeoValue(String txtIrmaoGemeoValue) {
        this.txtIrmaoGemeoValue = txtIrmaoGemeoValue;
    }

    public void setTxtJustificativaValue(String txtJustificativaValue) {
        this.txtJustificativaValue = txtJustificativaValue;
    }

    public String getInscricaoValue() {
        return this.txtInscricaoValue;
    }

    public void setInscricaoValue(String nr_matricula) {
        this.txtInscricaoValue = nr_matricula;

    }
    public void setStatusValue(String status) {
        this.txtStatusValue = status;
    }
    public String getStatusValue() {

        return txtStatusValue;
    }

    public Matricula(String nr, String status){

        this.txtInscricaoValue = nr;
        //this.valor = val;
        this.txtStatusValue = status;
    }

    public Matricula(String nr){

        this.txtInscricaoValue = nr;

    }

    public void addDetais(String json_raw){




        if (json_raw.isEmpty()) return;

        if (Apoio.isJSONValid(json_raw)) {

            try {
                JSONObject object = new JSONObject(json_raw);
                //JSONArray Jarray = object.getJSONArray("inscricao");
                JSONObject Jasonobject = object.getJSONObject("inscricao");
                //JSONObject Jasonobject = Jarray.getJSONObject(0);

                //txtInscricaoValue
                setInscricaoValue(Jasonobject.getString("sele_numero_inscricao"));
                //txtStatusValue
                setStatusValue(Jasonobject.getString("sele_status"));
                //txtAnoInscricaoValue
                setTxtAnoInscricaoValue(Jasonobject.getString("sele_ano"));
                //txtNomeAlunoValue
                setTxtNomeAlunoValue(Jasonobject.getString("sele_aluno_nome"));
                //txtNomeResponsavel01value
                setTxtNomeResponsavel01value(Jasonobject.getString("sele_mae"));
                //txtNomeResponsavel02value
                setTxtNomeResponsavel02value(Jasonobject.getString("sele_pai"));
                //txtDataNascimentoValue
                setTxtDataNascimentoValue(Jasonobject.getString("sele_aluno_nasc"));
                //txtEscola
                setTxtEscolaValue(Jasonobject.getString("sele_codigo_escola"));
                //txtSerieValue
                setTxtSerieValue(Jasonobject.getString("sele_serie_seq"));
                //txtBolsaFamiliaValue
                setTxtBolsaFamiliaValue(Jasonobject.getString("sele_responsavel_bolsa"));
                //txtIrmaoGemeoValue
                setTxtIrmaoGemeoValue(Jasonobject.getString("sele_gemeo"));
                // txtJustificativaValue
                setTxtJustificativaValue(Jasonobject.getString("sele_tipo_justificativa"));


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}

