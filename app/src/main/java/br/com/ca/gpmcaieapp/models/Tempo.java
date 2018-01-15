package br.com.ca.gpmcaieapp.models;

/**
 * Created by Rubens on 06/09/17.
 [{
 "previsao": 40,
 "descricao_detalhada": "Nebulosidade variável e chuva ocasional durante o período",
 "temperatura_maxima": 33,
 "umidade_relativa_maxima": 87,
 "temperatura_minima": 22,
 "data": "2017-12-11",
 "umidade_relativa_minima": 44,
 "regiao": "1",
 "condicao_atual": "NO NORDESTE DO BRASIL OBSERVA-SE NEBULOSIDADE SIGNIFICATIVA NA MAIOR PARTE DA BAHIA, MARANHÃO E PIAUÍ, SUL DO CEARÁ E EM PONTOS ISOLADOS DE SERGIPE, ALAGOAS, PERNAMBUCO, PARAÍBA E RIO GRANDE DO NORTE (IMAGEM METEOSAT-10 DE 11/12/17  ( 09:00 UTM)",
 "cidade": 2,
 "uv": 13,
 "direcao_vento": "NE",
 "intensidade_vento": "FR"
 }]
 */

public class Tempo {

    private int previsao;
    private String descricao_detalhada;
    private int temperatura_maxima;
    private int umidade_relativa_maxima;
    private int temperatura_minima;
    private String data;
    private int umidade_relativa_minima;
    private String regiao;
    private String condicao_atual;
    private String cidade;
    private int uv;
    private String direcao_vento;
    private String intensidade_vento;



    public Tempo() {

        int previsao = 40;
        String descricao_detalhada = "Nebulosidade variável e chuva ocasional durante o período";
        int temperatura_maxima = 33;
        int umidade_relativa_maxima = 87;
        int temperatura_minima = 22;
        String data = "2017-12-11";
        int umidade_relativa_minima = 44;
        String regiao = "1";
        String condicao_atual = "NO NORDESTE DO BRASIL OBSERVA-SE NEBULOSIDADE SIGNIFICATIVA NA MAIOR PARTE DA BAHIA, MARANHÃO E PIAUÍ, SUL DO CEARÁ E EM PONTOS ISOLADOS DE SERGIPE, ALAGOAS, PERNAMBUCO, PARAÍBA E RIO GRANDE DO NORTE (IMAGEM METEOSAT-10 DE 11/12/17  ( 09:00 UTM)";
        int cidade = 2;
        int uv = 13;
        String direcao_vento = "NE";
        String intensidade_vento = "FR";
    }

    public int getPrevisao() {
        return previsao;
    }

    public void setPrevisao(int previsao) {
        this.previsao = previsao;
    }

    public String getDescricao_detalhada() {
        return descricao_detalhada;
    }

    public void setDescricao_detalhada(String descricao_detalhada) {
        this.descricao_detalhada = descricao_detalhada;
    }

    public int getTemperatura_maxima() {
        return temperatura_maxima;
    }

    public void setTemperatura_maxima(int temperatura_maxima) {
        this.temperatura_maxima = temperatura_maxima;
    }

    public int getUmidade_relativa_maxima() {
        return umidade_relativa_maxima;
    }

    public void setUmidade_relativa_maxima(int umidade_relativa_maxima) {
        this.umidade_relativa_maxima = umidade_relativa_maxima;
    }

    public int getTemperatura_minima() {
        return temperatura_minima;
    }

    public void setTemperatura_minima(int temperatura_minima) {
        this.temperatura_minima = temperatura_minima;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getUmidade_relativa_minima() {
        return umidade_relativa_minima;
    }

    public void setUmidade_relativa_minima(int umidade_relativa_minima) {
        this.umidade_relativa_minima = umidade_relativa_minima;
    }

    public String getRegiao() {
        return regiao;
    }

    public void setRegiao(String regiao) {
        this.regiao = regiao;
    }

    public String getCondicao_atual() {
        return condicao_atual;
    }

    public void setCondicao_atual(String condicao_atual) {
        this.condicao_atual = condicao_atual;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public int getUv() {
        return uv;
    }

    public void setUv(int uv) {
        this.uv = uv;
    }

    public String getDirecao_vento() {
        return direcao_vento;
    }

    public void setDirecao_vento(String direcao_vento) {
        this.direcao_vento = direcao_vento;
    }

    public String getIntensidade_vento() {
        return intensidade_vento;
    }

    public void setIntensidade_vento(String intensidade_vento) {
        this.intensidade_vento = intensidade_vento;
    }



}
