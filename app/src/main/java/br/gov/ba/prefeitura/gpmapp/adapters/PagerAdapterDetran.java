package br.gov.ba.prefeitura.gpmapp.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import br.gov.ba.prefeitura.gpmapp.telas.FrgDetranPontuacao;
import br.gov.ba.prefeitura.gpmapp.util.Apoio;

/**
 * Adapter para as guias da tela de atualização cadastral
 */
public class PagerAdapterDetran extends FragmentPagerAdapter
{
    //Variaveis da classe
    private FrgDetranPontuacao frgDetranPontuacao = null;
    /**
     * Construtor da classe
     */
    public PagerAdapterDetran(FragmentManager fragmentManager, FrgDetranPontuacao frgDetranPontuacaoParam)
    {
        super(fragmentManager);

        //Armazena os fragmentos
        frgDetranPontuacao = frgDetranPontuacaoParam;

    }

    @Override
    public Fragment getItem(int iPosicao)
    {
        //Verifica qual aba estamos e retorna o fragmento de acordo com a aba
        switch (iPosicao) {
            //Primeira aba é a parte de documentos
            case Apoio.DETRAN_PONTUACAO: {
                return frgDetranPontuacao;
            }
            //A segunda aba é a parte de endereço
            //case Apoio.DETRAN_DADOS_VEICULO: {
            //    return frgDetranDadosVeiculo;
            //}
        }
        return null;
    }

    @Override
    public int getCount()
    {
        return Apoio.DETRAN_ABAS;
    }
}