package br.com.ca.gpmcaieapp.telas;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import br.com.ca.gpmcaieapp.R;
import br.com.ca.gpmcaieapp.adapters.AdapterDependentes;
import br.com.ca.gpmcaieapp.base.FragmentBase;
import br.com.ca.gpmcaieapp.models.Dependentes;
import br.com.ca.gpmcaieapp.util.Apoio;
import br.com.ca.gpmcaieapp.util.DialogAlerta;
import br.com.ca.gpmcaieapp.util.LogTrace;

/**
 * Fragmento de dependentes da atualização cadastral
 */
public class FrgAtualizacaoCadDependentes extends FragmentBase implements View.OnClickListener, DialogInterface.OnClickListener
{
    //Controles da tela
    private RecyclerView rcvDependentes = null;
    private FloatingActionButton cmdNovo = null;
    private AlertDialog dlgExcluir = null;

    //variaveis da classe
    private AdapterDependentes adapterDependentes = null;
    private ArrayList<Dependentes> arrDependentes = null;
    private Dependentes dependentes = null;
    private int iPosicao = -1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup vwgGrupo, @Nullable Bundle bundle)
    {
        View viewTela = null;

        try
        {
            //Infla o fragmento na view
            viewTela = inflater.inflate(R.layout.frg_atualizacao_cad_dependentes, vwgGrupo, false);

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
        //Carrega os controles da classe
        rcvDependentes = (RecyclerView) viewTela.findViewById(R.id.rcvDependentes);
        cmdNovo = (FloatingActionButton) viewTela.findViewById(R.id.cmdNovo);

        //TODO: Escondemos o botão, pois a primeira versão será só read-only
        //cmdNovo.setVisibility(View.GONE);

        //Seta o listener dos controles
        cmdNovo.setOnClickListener(this);
    }

    @Override
    public void carregaDados(View viewTela) throws Exception
    {
        //Preenche a lista de dependentes
        preencheListaDependentes(null);
    }

    /**
     * Preenche a lista de dependentes
     */
    public void preencheListaDependentes(ArrayList<Dependentes> arrDependentesParam) throws Exception
    {
        //Se o array estiver vazio
        if ( arrDependentesParam == null )
        {
            //instancia o array
            arrDependentes = new ArrayList<Dependentes>();
        }
        else
        {
            arrDependentes = arrDependentesParam;
        }

        //seta o adapter e carrega os itens
        adapterDependentes = new AdapterDependentes(getActivity(), this, arrDependentes);
        rcvDependentes.setLayoutManager(new GridLayoutManager(getActivity(), 1, LinearLayoutManager.VERTICAL, false));
        rcvDependentes.setAdapter(adapterDependentes);
        rcvDependentes.setNestedScrollingEnabled(false);

        //Atualize a lista
        adapterDependentes.notifyDataSetChanged();
    }

    /**
     * Limpa os campos da tela
     */
    public void limpaCamposTela() throws Exception
    {
        //Limpa os campos
        arrDependentes = new ArrayList<Dependentes>();

        //Atualize a lista
        adapterDependentes.notifyDataSetChanged();
    }

    /**
     * Exclui o dependente de acordo com a posição
     */
    private void excluirDependente(int iPosicao) throws Exception
    {
        //Se não for nulo o array
        if ( arrDependentes != null )
        {
            //Se tiver tamanho para o indice
            if ( (arrDependentes.size()-1) >= iPosicao  )
            {
                //Remove o item no array de cursos
                arrDependentes.remove(iPosicao);

                //Atualize a lista
                adapterDependentes.notifyDataSetChanged();
            }
        }
    }

    /**
     * Retorna os dados da tela
     */
    public ArrayList<Dependentes> retornaDadosTela() throws Exception
    {
        return arrDependentes;
    }

    /**
     * Insere ou atualiza o dependente que voltou do cadastro
     */
    public void atualizaInsereDependente(Dependentes dependentes) throws Exception
    {
        //Se tiver posição
        if ( iPosicao != -1 )
        {
            //Troca o item
            arrDependentes.set(iPosicao, dependentes);
        }
        else
        {
            //Adiciona o curso
            arrDependentes.add(dependentes);
        }

        //Atualize a lista
        adapterDependentes.notifyDataSetChanged();
    }

    @Override
    public void onReceiveData(Class classe, int iId, boolean bResultado, Object... oObjetos)
    {
        FrmAtualizacaoCad frmAtualizacaoCad = null;

        try
        {
            //Zera a posicao
            iPosicao = -1;

            //Se clicou no Adapter de dependentes
            if ( classe == AdapterDependentes.class )
            {
                //Se existir o objeto
                if (oObjetos != null)
                {
                    iPosicao = (int) oObjetos[0];
                }

                //Se tiver uma posição
                if (iPosicao >= 0)
                {
                    //Se for a exclusão
                    if ( iId == R.id.lnlExcluirDependente )
                    {
                        //Cria um dialogAlert com as opções
                        dlgExcluir = Apoio.criarAlertDialog(getActivity(), getString(R.string.sim), getString(R.string.nao), getString(R.string.atencao), getString(R.string.msg_atualizacao_cad_dependentes_excluir), this);
                        dlgExcluir.show();
                        Apoio.trocaCoresBotoesDialog(dlgExcluir, getActivity());
                    }
                    //Se clicou na linha do curso
                    else if ( iId == R.id.lnlDependente )
                    {
                        //Chama o método que abre a alteração do dependente
                        frmAtualizacaoCad = (FrmAtualizacaoCad)this.getActivity();
                        frmAtualizacaoCad.chamaTelaNovoAtualizacaoCadDependentes(arrDependentes.get(iPosicao));
                    }
                }
            }
        }
        catch (Exception err)
        {
            LogTrace.escreve(Apoio.localErro(getClass(), err), Apoio.getPathLogsSDCard(getActivity()), Apoio.getArqErr());
            DialogAlerta.show(getActivity(), Apoio.getMsgErr(getClass(), err), getString(R.string.atencao), getString(R.string.ok));
        }
    }

    @Override
    public void onClick(View view)
    {
        FrmAtualizacaoCad frmAtualizacaoCad = null;

        try
        {
            //Se clicou no novo
            if (view == cmdNovo)
            {
                //Seta a posição para -1 pois é uma inclusão
                iPosicao = -1;

                //Chama a tela de cadastro de dependentes
                frmAtualizacaoCad = (FrmAtualizacaoCad)this.getActivity();
                frmAtualizacaoCad.chamaTelaNovoAtualizacaoCadDependentes(null);
            }
        }
        catch (Exception err)
        {
            LogTrace.escreve(Apoio.localErro(getClass(), err), Apoio.getPathLogsSDCard(getActivity()), Apoio.getArqErr());
            DialogAlerta.show(getActivity(), Apoio.getMsgErr(getClass(), err), getString(R.string.atencao), getString(R.string.ok));
        }
    }

    @Override
    public void onClick(DialogInterface dialog, int iEscolha)
    {
        try
        {
            //Se for o dialog de excluir
            if ( dialog == dlgExcluir )
            {
                //Se tocou no botão sim
                if (iEscolha == DialogInterface.BUTTON_POSITIVE)
                {
                    //Exclui o item do array e da lista
                    excluirDependente(iPosicao);
                }
            }
        }
        catch(Exception err)
        {
            LogTrace.escreve(Apoio.localErro(getClass(), err), Apoio.getPathLogsSDCard(getActivity()), Apoio.getArqErr());
            DialogAlerta.show(getActivity(), Apoio.getMsgErr(getClass(), err), getString(R.string.atencao), getString(R.string.ok));
        }
    }
}