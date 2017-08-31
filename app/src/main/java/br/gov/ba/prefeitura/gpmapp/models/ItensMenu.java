package br.gov.ba.prefeitura.gpmapp.models;

/**
 * Classe para itens do menu
 */
public class ItensMenu
{
    //Variaveis da classe
    public String sPrimeiroTitulo = "";
    public int iIcone = 0;

    /**
     * Construtor da classe
     */
    public ItensMenu(String sPrimeiroTituloParam, int iIconeParam)
    {
        sPrimeiroTitulo = sPrimeiroTituloParam;
        iIcone = iIconeParam;
    }
}
