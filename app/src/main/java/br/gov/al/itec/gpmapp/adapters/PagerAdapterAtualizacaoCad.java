package br.gov.al.itec.gpmapp.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import br.gov.al.itec.gpmapp.telas.FrgAtualizacaoCadCursos;
import br.gov.al.itec.gpmapp.telas.FrgAtualizacaoCadDependentes;
import br.gov.al.itec.gpmapp.telas.FrgAtualizacaoCadDocumentos;
import br.gov.al.itec.gpmapp.telas.FrgAtualizacaoCadEndereco;
import br.gov.al.itec.gpmapp.telas.FrgAtualizacaoCadQuestionario;
import br.gov.al.itec.gpmapp.telas.FrgAtualizacaoCadVinculo;
import br.gov.al.itec.gpmapp.util.Apoio;

/**
 * Adapter para as guias da tela de atualização cadastral
 */
public class PagerAdapterAtualizacaoCad extends FragmentPagerAdapter
{
    //Variaveis da classe
    private FrgAtualizacaoCadDocumentos frgAtualizacaoCadDocumentos = null;
    private FrgAtualizacaoCadEndereco frgAtualizacaoCadEndereco = null;
    private FrgAtualizacaoCadDependentes frgAtualizacaoCadDependentes = null;
    private FrgAtualizacaoCadCursos frgAtualizacaoCadCursos = null;
    private FrgAtualizacaoCadVinculo frgAtualizacaoCadVinculo = null;
    private FrgAtualizacaoCadQuestionario frgAtualizacaoCadQuestionario = null;

    /**
     * Construtor da classe
     */
    public PagerAdapterAtualizacaoCad(FragmentManager fragmentManager, FrgAtualizacaoCadDocumentos frgAtualizacaoCadDocumentosParam, FrgAtualizacaoCadEndereco frgAtualizacaoCadEnderecoParam,FrgAtualizacaoCadDependentes frgAtualizacaoCadDependentesParam, FrgAtualizacaoCadCursos frgAtualizacaoCadCursosParam, FrgAtualizacaoCadVinculo frgAtualizacaoCadVinculoParam, FrgAtualizacaoCadQuestionario frgAtualizacaoCadQuestionarioParam)
    {
        super(fragmentManager);

        //Armazena os fragmentos
        frgAtualizacaoCadDocumentos = frgAtualizacaoCadDocumentosParam;
        frgAtualizacaoCadEndereco = frgAtualizacaoCadEnderecoParam;
        frgAtualizacaoCadDependentes = frgAtualizacaoCadDependentesParam;
        frgAtualizacaoCadCursos = frgAtualizacaoCadCursosParam;
        frgAtualizacaoCadVinculo = frgAtualizacaoCadVinculoParam;
        frgAtualizacaoCadQuestionario = frgAtualizacaoCadQuestionarioParam;
    }

    @Override
    public Fragment getItem(int iPosicao)
    {
        //Verifica qual aba estamos e retorna o fragmento de acordo com a aba
        switch (iPosicao)
        {
            //Primeira aba é a parte de documentos
            case Apoio.ATUALIZACAO_CAD_TAB_DOCUMENTOS:
            {
                return frgAtualizacaoCadDocumentos;
            }
            //A segunda aba é a parte de endereço
            case Apoio.ATUALIZACAO_CAD_TAB_ENDERECO:
            {
                return frgAtualizacaoCadEndereco;
            }
            //A terceira aba é a parte de dependentes
            case Apoio.ATUALIZACAO_CAD_TAB_DEPENDENTES:
            {
                return frgAtualizacaoCadDependentes;
            }
            //A quarta aba é a parte de cursos
            case Apoio.ATUALIZACAO_CAD_TAB_CURSOS:
            {
                return frgAtualizacaoCadCursos;
            }
            //A quinta aba é a parte de vinculo
            case Apoio.ATUALIZACAO_CAD_TAB_VINCULO:
            {
                return frgAtualizacaoCadVinculo;
            }
            //A sexta aba é a parte de questionario
            case Apoio.ATUALIZACAO_CAD_TAB_QUESTIONARIO:
            {
                return frgAtualizacaoCadQuestionario;
            }
        }

        return null;
    }

    @Override
    public int getCount()
    {
        return Apoio.ATUALIZACAO_CAD_QTD_ABAS;
    }
}