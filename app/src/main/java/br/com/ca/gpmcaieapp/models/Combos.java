package br.com.ca.gpmcaieapp.models;

/**
 * Classe para preenchimento dos combos
 */
public class Combos
{
    //Variáveis da classe
    public String sCodigo = "";
    public String sDescricao = "";

    public Combos(String sCodigoParam, String sDescricaoParam)
    {
        sCodigo = sCodigoParam;
        sDescricao = sDescricaoParam;
    }

    @Override
    public String toString()
    {
        return sDescricao;
    }
}
