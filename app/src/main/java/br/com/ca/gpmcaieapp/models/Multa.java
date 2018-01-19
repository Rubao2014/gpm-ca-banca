package br.com.ca.gpmcaieapp.models;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import br.com.ca.gpmcaieapp.util.Apoio;

public class Multa implements Serializable {

    private Integer id;
    private String desc;
    private Integer pts;

    public Multa(Integer id, String desc, Integer pts){
        this.setId(id);
        this.setDesc(desc);
        this.setPts(pts);
    }

    public void addDetais(String json_raw){

        if (json_raw.isEmpty()) return;

        if (Apoio.isJSONValid(json_raw)) {

            try {
                JSONObject object = new JSONObject(json_raw);
                JSONObject Jasonobject = object.getJSONObject("multas");

                setId(Jasonobject.getInt("id"));
                setDesc(Jasonobject.getString("desc"));
                setPts(Jasonobject.getInt("pts"));

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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getPts() {
        return pts;
    }

    public void setPts(Integer pts) {
        this.pts = pts;
    }
}

