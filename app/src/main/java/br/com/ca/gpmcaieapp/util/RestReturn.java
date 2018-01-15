package br.com.ca.gpmcaieapp.util;

import com.google.gson.annotations.SerializedName;

/**
 * Created by marcos.allan on 20/08/2015.
 */
public class RestReturn
{
    //Variaveis da classe
    @SerializedName("Codigo")
    private int iCodigo = 0;

    @SerializedName("Mensagem")
    private String sMensagem = "";

    @SerializedName("Objeto")
    private String sObjeto = "";

    /**
     * Construtor da classe
     */
    public RestReturn(int iCodigoParam, String sMensagemParam, String sObjetoParam)
    {
        //Seta os valores
        iCodigo = iCodigoParam;
        sMensagem = sMensagemParam;
        sObjeto = sObjetoParam;
    }

    /**
     * Codigo Retorno
     */
    public int getCodigoRetorno()
    {
        return iCodigo;
    }

    /**
     * Mensagem Retorno
     */
    public String getMensagemRetorno()
    {
        return sMensagem;
    }

    /**
     * Objeto de Retorno
     */
    public String getRetorno()
    {
        return sObjeto;
    }

    /**
     * Objeto de Retorno
     */
    public void setRetorno(String sObjetoParam)
    {
        sObjeto = sObjetoParam;
    }
}