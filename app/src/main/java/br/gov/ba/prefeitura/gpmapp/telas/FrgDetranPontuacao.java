package br.gov.ba.prefeitura.gpmapp.telas;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import br.gov.ba.prefeitura.gpmapp.R;
import br.gov.ba.prefeitura.gpmapp.base.FragmentBase;
import br.gov.ba.prefeitura.gpmapp.models.Combos;
import br.gov.ba.prefeitura.gpmapp.models.Pontos;
import br.gov.ba.prefeitura.gpmapp.util.Apoio;
import br.gov.ba.prefeitura.gpmapp.util.DialogAlerta;
import br.gov.ba.prefeitura.gpmapp.util.LogTrace;

/**
 * Fragmento de endereço da atualização cadastral
 */
public class FrgDetranPontuacao extends FragmentBase
{
    //Controles da tela

    private EditText txtPontos = null;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup vwgGrupo, @Nullable Bundle bundle)
    {
        View viewTela = null;

        try
        {
            //Infla o fragmento na view
            viewTela = inflater.inflate(R.layout.frg_detran_pontuacao, vwgGrupo, false);

            //chama o método para iniciar o fluxo de dados da classe
            iniciaFluxoDados(viewTela);
        }
        catch(Exception err)
        {
            LogTrace.escreve(Apoio.localErro(getClass(), err), Apoio.getPathLogsSDCard(getActivity()), Apoio.getArqErr());
            DialogAlerta.show(getActivity(), Apoio.getMsgErr(getClass(), err), getString(R.string.atencao), getString(R.string.ok));
        }

        return viewTela;
    }

    @Override
    public void iniciaControles(View viewTela) throws Exception
    {
        TextWatcher textWatcherPontos = null;

        //Carrega os controles da classe
        txtPontos = (EditText) viewTela.findViewById(R.id.txtPontuacao);
        txtPontos.setKeyListener(null);

    }

    @Override
    public void carregaDados(View viewTela) throws Exception
    {
        //Preenche combo de tipo de endereço
        preencheComboTipoEndereco();
    }

    /**
     * Preenche o combo de tipo de endereço
     */
    private void preencheComboTipoEndereco() throws Exception
    {
        ArrayAdapter<Combos> dataAdapter = null;

    }

    /**
     * Carrega os campos da tela com base no JSON de dados
     */
    public void carregaCamposTela(Pontos pontos) throws Exception
    {

        //Seta os valores dos campos
        txtPontos.setText(pontos.sPontos);

    }

    /**
     * Remove os erros dos campos da tela
     */
    public void removerErroDosCampos() throws Exception
    {
        //Tira o erro
    }

    /**
     * Valida os campos da tela
     */
    public boolean validaCampos(AppCompatActivity activity, ViewPager viewPager) throws Exception
    {
        return true;
    }

    /**
     * Retorna os dados da tela
     */
    public Pontos retornaDadosTela() throws Exception
    {
        Pontos pontos = null;
        return pontos;
    }

    /**
     * Limpa os campos da tela
     */
    public void limpaCamposTela() throws Exception
    {
        //Limpa os campos
        txtPontos.setText("");

    }
}