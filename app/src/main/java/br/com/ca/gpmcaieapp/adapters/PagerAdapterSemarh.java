package br.com.ca.gpmcaieapp.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import br.com.ca.gpmcaieapp.telas.FrgSemarhLitoral;
import br.com.ca.gpmcaieapp.util.Apoio;

/**
 * Adapter para as guias da tela de atualização cadastral
 */
public class PagerAdapterSemarh extends FragmentPagerAdapter
{
    //Variaveis da classe
    private FrgSemarhLitoral frgSemarhLitoral = null;
    /**
     * Construtor da classe
     */
    public PagerAdapterSemarh(FragmentManager fragmentManager, FrgSemarhLitoral frgSemarhLitoralParam)
    {
        super(fragmentManager);

        //Armazena os fragmentos
        frgSemarhLitoral = frgSemarhLitoralParam;

    }

    @Override
    public Fragment getItem(int iPosicao)
    {
        //Verifica qual aba estamos e retorna o fragmento de acordo com a aba
        switch (iPosicao) {
            //Primeira aba é a parte de documentos
            case Apoio.SEMARH_LITORIAL: {
                return frgSemarhLitoral;
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