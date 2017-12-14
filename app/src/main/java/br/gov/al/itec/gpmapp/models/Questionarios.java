package br.gov.al.itec.gpmapp.models;

import com.google.gson.annotations.SerializedName;

/**
 * Classe para preenchimento dos questionários
 */
public class Questionarios
{
    //Variáveis da classe
    @SerializedName("descricao_alergia")
    public String sDescricaoAlergia = "";

    @SerializedName("tipo_moradia")
    public String sTipoMoradia = "";

    @SerializedName("atv_fisica")
    public String sAtividadeFisica = "";

    @SerializedName("cardiaco")
    public String sCardiaco = "";

    @SerializedName("doador")
    public String sDoador = "";

    @SerializedName("descricao_atv_fisica")
    public String sDescricaoAtividadeFisica = "";

    @SerializedName("servico_esgoto")
    public String sServicoEsgoto = "";

    @SerializedName("habito_fumar")
    public String sHabitoFumar = "";

    @SerializedName("servico_eletronico")
    public String sServicoEletronico = "";

    @SerializedName("moradia")
    public String sMoradia = "";

    @SerializedName("piso")
    public String sPiso = "";

    @SerializedName("alergia")
    public String sAlergia = "";

    @SerializedName("diabetico")
    public String sDiabetico = "";

    @SerializedName("servico_agua")
    public String sServicoAgua = "";

    @SerializedName("piso_descricao")
    public String sPisoDescricao = "";

    @SerializedName("hipertensao")
    public String sHipertensao = "";

    @SerializedName("colesterol")
    public String sColesterol = "";

    @SerializedName("servico_lixo")
    public String sServicoLixo = "";

    @SerializedName("habito_beber")
    public String sHabitoBeber = "";
}
