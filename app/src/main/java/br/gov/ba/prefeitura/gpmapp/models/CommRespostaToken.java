package br.gov.ba.prefeitura.gpmapp.models;

import com.google.gson.annotations.SerializedName;

/**
 * Classe para obtenção da resposta do token
 */
public class CommRespostaToken
{
    //Variáveis da classe
    @SerializedName("access_token")
    public String sAccessToken = "";

    @SerializedName("token_type")
    public String sTokenType = "";

    @SerializedName("expires_in")
    public int iExpiresIn = 0;

    @SerializedName("scope")
    public String sScope = "";
}
