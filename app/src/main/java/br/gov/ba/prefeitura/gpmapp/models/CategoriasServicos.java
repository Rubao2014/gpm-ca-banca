package br.gov.ba.prefeitura.gpmapp.models;

/**
 * Classe de categorias e serviços
 */
public class CategoriasServicos
{
    //Variáveis da classe
    public String sNomeCategoria = "";
    public boolean bAtivo = false;
    public boolean bCategoria = false;

    /**
     * Construtor da classe
     */
    public CategoriasServicos(String sNomeCategoriaParam, boolean bAtivoParam, boolean bCategoriaParam)
    {
        sNomeCategoria = sNomeCategoriaParam;
        bAtivo = bAtivoParam;
        bCategoria = bCategoriaParam;
    }
}
