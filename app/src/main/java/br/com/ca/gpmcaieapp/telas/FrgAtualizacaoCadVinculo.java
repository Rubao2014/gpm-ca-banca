package br.com.ca.gpmcaieapp.telas;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import br.com.ca.gpmcaieapp.R;
import br.com.ca.gpmcaieapp.base.FragmentBase;
import br.com.ca.gpmcaieapp.models.Vinculos;
import br.com.ca.gpmcaieapp.util.Apoio;
import br.com.ca.gpmcaieapp.util.DialogAlerta;
import br.com.ca.gpmcaieapp.util.LogTrace;

/**
 * Fragmento de vinculo da atualização cadastral
 */
public class FrgAtualizacaoCadVinculo extends FragmentBase
{
    //Controles da tela
    private TextView lblMatricula = null;
    private TextView lblSituacao = null;
    private TextView lblVinculo = null;
    private TextView lblOrgaoPagamento = null;
    private TextView lblMatricula2 = null;
    private TextView lblDocAdmissao = null;
    private TextView lblDocAdmissaoNumero = null;
    private TextView lblDocAdmissaoData = null;
    private TextView lblDataAdmissao = null;
    private TextView lblConcursado = null;
    private TextView lblSituacao2 = null;
    private TextView lblCargaHoraria = null;
    private TextView lblVinculo2 = null;
    private TextView lblCidade = null;
    private TextView lblOrgaoOrigem = null;
    private TextView lblOrgaoPagamento2 = null;
    private TextView lblCargo = null;
    private TextView lblOrgaoLotacao = null;
    private TextView lblSetorLotacao = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup vwgGrupo, @Nullable Bundle bundle)
    {
        View viewTela = null;

        try
        {
            //Infla o fragmento na view
            viewTela = inflater.inflate(R.layout.frg_atualizacao_cad_vinculo, vwgGrupo, false);

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

    //Carrega os controles da classe
    @Override
    public void iniciaControles(View viewTela) throws Exception
    {
        lblMatricula = (TextView)viewTela.findViewById(R.id.lblMatricula);
        lblSituacao = (TextView)viewTela.findViewById(R.id.lblSituacao);
        lblVinculo = (TextView)viewTela.findViewById(R.id.lblVinculo);
        lblOrgaoPagamento = (TextView)viewTela.findViewById(R.id.lblOrgaoPagamento);
        lblMatricula2 = (TextView)viewTela.findViewById(R.id.lblMatricula2);
        lblDocAdmissao = (TextView)viewTela.findViewById(R.id.lblDocAdmissao);
        lblDocAdmissaoNumero = (TextView)viewTela.findViewById(R.id.lblDocAdmissaoNumero);
        lblDocAdmissaoData = (TextView)viewTela.findViewById(R.id.lblDocAdmissaoData);
        lblDataAdmissao = (TextView)viewTela.findViewById(R.id.lblDataAdmissao);
        lblConcursado = (TextView)viewTela.findViewById(R.id.lblConcursado);
        lblSituacao2 = (TextView)viewTela.findViewById(R.id.lblSituacao2);
        lblCargaHoraria = (TextView)viewTela.findViewById(R.id.lblCargaHoraria);
        lblVinculo2 = (TextView)viewTela.findViewById(R.id.lblVinculo2);
        lblCidade = (TextView)viewTela.findViewById(R.id.lblCidade);
        lblOrgaoOrigem = (TextView)viewTela.findViewById(R.id.lblOrgaoOrigem);
        lblOrgaoPagamento2 = (TextView)viewTela.findViewById(R.id.lblOrgaoPagamento2);
        lblCargo = (TextView)viewTela.findViewById(R.id.lblCargo);
        lblOrgaoLotacao = (TextView)viewTela.findViewById(R.id.lblOrgaoLotacao);
        lblSetorLotacao = (TextView)viewTela.findViewById(R.id.lblSetorLotacao);
    }

    @Override
    public void carregaDados(View viewTela) throws Exception
    {
    }

    /**
     * Carrega os campos da tela com base no JSON de dados
     */
    public void carregaCamposTela(Vinculos vinculos) throws Exception
    {
        //Seta os valores dos campos
        lblMatricula.setText(vinculos.sMatricula);
        lblSituacao.setText(vinculos.sStatusDescricao);
        lblVinculo.setText(vinculos.sVinculoDescricao);
        lblOrgaoPagamento.setText(vinculos.sOrgaoLotacao);
        lblMatricula2.setText("");
        lblDocAdmissao.setText("");
        lblDocAdmissaoNumero.setText("");
        lblDocAdmissaoData.setText("");
        lblDataAdmissao.setText(vinculos.sDataAdmissao);
        lblConcursado.setText(vinculos.sConcursado);
        lblSituacao2.setText("");
        lblCargaHoraria.setText(vinculos.sHorasTrabalhoID);
        //lblVinculo2.setText("");
        lblCidade.setText(vinculos.sCidadeDescricao);
        lblOrgaoOrigem.setText(vinculos.sOrgaoDescricao);
        //lblOrgaoPagamento2.setText("");
        lblCargo.setText(vinculos.sCargoID);
        lblOrgaoLotacao.setText(vinculos.sSetorOrgaoLotacao);
        lblSetorLotacao.setText(vinculos.sSetorLotacao);
    }

    /**
     * Limpa os campos da tela
     */
    public void limpaCamposTela() throws Exception
    {
        //Limpa os campos
        lblMatricula.setText("");
        lblSituacao.setText("");
        lblVinculo.setText("");
        lblOrgaoPagamento.setText("");
        lblMatricula2.setText("");
        lblDocAdmissao.setText("");
        lblDocAdmissaoNumero.setText("");
        lblDocAdmissaoData.setText("");
        lblDataAdmissao.setText("");
        lblConcursado.setText("");
        lblSituacao2.setText("");
        lblCargaHoraria.setText("");
        lblVinculo2.setText("");
        lblCidade.setText("");
        lblOrgaoOrigem.setText("");
        lblOrgaoPagamento2.setText("");
        lblCargo.setText("");
        lblOrgaoLotacao.setText("");
        lblSetorLotacao.setText("");
    }
}