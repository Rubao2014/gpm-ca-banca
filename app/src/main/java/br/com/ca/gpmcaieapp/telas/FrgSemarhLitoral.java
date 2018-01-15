package br.com.ca.gpmcaieapp.telas;

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
import android.widget.TextView;

import br.com.ca.gpmcaieapp.R;
import br.com.ca.gpmcaieapp.base.FragmentBase;
import br.com.ca.gpmcaieapp.models.Combos;
import br.com.ca.gpmcaieapp.models.Pontos;
import br.com.ca.gpmcaieapp.models.Previsoes;
import br.com.ca.gpmcaieapp.models.Tempo;
import br.com.ca.gpmcaieapp.util.Apoio;
import br.com.ca.gpmcaieapp.util.DialogAlerta;
import br.com.ca.gpmcaieapp.util.LogTrace;

/**
 * Fragmento de endereço da atualização cadastral
 */
public class FrgSemarhLitoral extends FragmentBase
{
    //Controles da tela

    private EditText txtTemperatura = null;
    private TextView lblCidade = null;
    private TextView lblData = null;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup vwgGrupo, @Nullable Bundle bundle)
    {
        View viewTela = null;

        try
        {
            //Infla o fragmento na view
            viewTela = inflater.inflate(R.layout.frg_semarh_litoral, vwgGrupo, false);

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
        txtTemperatura = (EditText) viewTela.findViewById(R.id.txtTemperatura);
        txtTemperatura.setKeyListener(null);

        lblCidade = (TextView) viewTela.findViewById(R.id.lblCidade);
        lblData = (TextView) viewTela.findViewById(R.id.lblData);


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
    public void carregaCamposTela(Tempo t) throws Exception
    {
        Previsoes p = new Previsoes();
        p.populateHashListaCompletaCidade();

        final String DEGREE  = "\u00b0";
        //Seta os valores dos campos
        txtTemperatura.setText(String.valueOf(t.getPrevisao()) + DEGREE);

        lblCidade.setText(p.getEscola(t.getCidade()));
        lblData.setText(t.getData());

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
        txtTemperatura.setText("");
        lblCidade.setText("");
        lblData.setText("");

    }
}