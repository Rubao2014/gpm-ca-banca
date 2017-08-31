package br.gov.ba.prefeitura.gpmapp.models;

import com.google.gson.annotations.SerializedName;

/**
 * Classe para preenchimento dos endereços
 */
public class Enderecos
{
    //Variáveis da classe
    @SerializedName("bairro")
    public String sBairro = "";

    @SerializedName("cep")
    public String sCEP = "";

    @SerializedName("ponto_referencia")
    public String sPontoReferencia = "";

    @SerializedName("numero")
    public String sNumero = "";

    @SerializedName("fone_1")
    public String sFone1 = "";

    @SerializedName("fone_2")
    public String sFone2 = "";

    @SerializedName("tipo_endereco")
    public String sTipoEndereco = "";

    @SerializedName("fixo")
    public String sFixo = "";

    @SerializedName("email")
    public String sEmail = "";

    @SerializedName("logradouro")
    public String sLogradouro = "";

    @SerializedName("complemento")
    public String sComplemento = "";

    @SerializedName("cidade")
    public String sCidade = "";
}
