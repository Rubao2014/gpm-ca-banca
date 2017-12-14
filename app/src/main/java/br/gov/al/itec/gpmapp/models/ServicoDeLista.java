package br.gov.al.itec.gpmapp.models;

/**
 * Created by Rubens on 06/12/16.
 */

import java.io.Serializable;


public class ServicoDeLista implements Serializable {

    private String sigla;
    private String nome;
    private String unidade;
    private String cetm; // Custo efetivo total mensal
    private String dtc;  // Data de credito na conta
    private String dtvc; // Data de vencidmento do contrato
    private String ccc;  // conta corrente de credito
    private String ccd;  // conta corrente de debito


    public ServicoDeLista(String sSiglaParam, String sNomeParam, String sUnidadeParam){

        this.sigla = sSiglaParam;
        this.nome = sNomeParam;
        this.unidade = sUnidadeParam;
    }

    public String getNome(){
        return (this.nome);
    }

    public String getSigla() {
        return sigla;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }


}

