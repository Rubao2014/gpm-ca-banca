package br.com.ca.gpmcaieapp.models;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import br.com.ca.gpmcaieapp.util.Apoio;

public class Vacina implements Serializable {

    private Integer id;
    private String cns;
    private String descricao;
    private Integer flag;

    public enum VacinaJsonOutput {
        COMPLETO,
        UPDATE
    };

    public Vacina(Integer id, String cns, String descricao, Integer flag){
        this.setId(id);
        this.setCns(cns);
        this.setDescricao(descricao);
        this.setFlag(flag);
    }

    public void addDetais(String json_raw){

        if (json_raw.isEmpty()) return;

        if (Apoio.isJSONValid(json_raw)) {

            try {
                JSONObject object = new JSONObject(json_raw);
                JSONObject Jasonobject = object.getJSONObject("vacinas_cns");

                setId(Jasonobject.getInt("id"));
                setDescricao(Jasonobject.getString("desc"));
                setFlag(Jasonobject.getInt("flag"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getCns() {
        return cns;
    }

    public void setCns(String cns) {
        this.cns = cns;
    }

    public String toJson(VacinaJsonOutput format) {
        StringBuffer json = new StringBuffer();
        json.append("{");
        switch (format) {
            case COMPLETO: {
                json.append(String.format("\"cns\": \"%s\"", cns)).append(", ");
                json.append(String.format("\"vac_id\": %d", id)).append(", ");
                json.append(String.format("\"desc\": \"%s\"", descricao)).append(", ");
                json.append(String.format("\"flag\": %d", flag));
                break;
            }
            case UPDATE: {
                json.append(String.format("\"cns\": \"%s\"", cns)).append(", ");
                json.append(String.format("\"vac_id\": %d", id)).append(", ");
                json.append(String.format("\"flag\": %d", flag));
                break;
            }
        }
        json.append("}");
        return json.toString();
    }
}

