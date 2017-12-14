package br.gov.al.itec.gpmapp.telas;

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
import br.gov.al.itec.gpmapp.R;
import br.gov.al.itec.gpmapp.adapters.AdapterCursos;
import br.gov.al.itec.gpmapp.base.FragmentBase;
import br.gov.al.itec.gpmapp.models.Cursos;
import br.gov.al.itec.gpmapp.util.Apoio;
import br.gov.al.itec.gpmapp.util.DialogAlerta;
import br.gov.al.itec.gpmapp.util.LogTrace;

/**
 * Fragmento de cursos da atualização cadastral
 */
public class FrgAtualizacaoCadCursos extends FragmentBase implements View.OnClickListener, DialogInterface.OnClickListener
{
    //Controles da tela
    private RecyclerView rcvCursos = null;
    private FloatingActionButton cmdNovo = null;
    private AlertDialog dlgExcluir = null;

    //Variaveis da classe
    private AdapterCursos adapterCursos = null;
    private ArrayList<Cursos> arrCursos = null;
    private int iPosicao = -1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup vwgGrupo, @Nullable Bundle bundle)
    {
        View viewTela = null;

        try
        {
            //Infla o fragmento na view
            viewTela = inflater.inflate(R.layout.frg_atualizacao_cad_cursos, vwgGrupo, false);

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
        rcvCursos = (RecyclerView) viewTela.findViewById(R.id.rcvCursos);
        cmdNovo = (FloatingActionButton) viewTela.findViewById(R.id.cmdNovo);

        //TODO: Escondemos o botão, pois a primeira versão será só read-only
        //cmdNovo.setVisibility(View.GONE);

        //Seta o listener dos controles
        cmdNovo.setOnClickListener(this);
    }

    @Override
    public void carregaDados(View viewTela) throws Exception
    {
        //Preenche a lista de cursos
        preencheListaCursos(null);
    }

    /**
     * Preenche a lista de cursos
     */
    public void preencheListaCursos(ArrayList<Cursos> arrCursosParam) throws Exception
    {
        //Se o array estiver vazio
        if ( arrCursosParam == null )
        {
            //instancia o array
            arrCursos = new ArrayList<Cursos>();
        }
        else
        {
            arrCursos = arrCursosParam;
        }

        //seta o adapter e carrega os itens
        adapterCursos = new AdapterCursos(getActivity(), this, arrCursos);
        rcvCursos.setLayoutManager(new GridLayoutManager(getActivity(), 1, LinearLayoutManager.VERTICAL, false));
        rcvCursos.setAdapter(adapterCursos);
        rcvCursos.setNestedScrollingEnabled(false);

        //Atualize a lista
        adapterCursos.notifyDataSetChanged();
    }

    /**
     * Limpa os campos da tela
     */
    public void limpaCamposTela() throws Exception
    {
        //Limpa os campos
        arrCursos = new ArrayList<Cursos>();

        //Atualize a lista
        adapterCursos.notifyDataSetChanged();
    }

    /**
     * Exclui o curso de acordo com a posição
     */
    private void excluirCurso(int iPosicao) throws Exception
    {
        //Se não for nulo o array
        if ( arrCursos != null )
        {
            //Se tiver tamanho para o indice
            if ( (arrCursos.size()-1) >= iPosicao  )
            {
                //Remove o item no array de cursos
                arrCursos.remove(iPosicao);

                //Atualize a lista
                adapterCursos.notifyDataSetChanged();
            }
        }
    }

    /**
     * Retorna os dados da tela
     */
    public ArrayList<Cursos> retornaDadosTela() throws Exception
    {
        return arrCursos;
    }

    /**
     * Insere ou atualiza o curso que voltou do cadastro
     */
    public void atualizaInsereCurso(Cursos cursos) throws Exception
    {
        //Se tiver posição
        if ( iPosicao != -1 )
        {
            //Troca o item
            arrCursos.set(iPosicao, cursos);
        }
        else
        {
            //Adiciona o curso
            arrCursos.add(cursos);
        }

        //Atualize a lista
        adapterCursos.notifyDataSetChanged();
    }

    @Override
    public void onReceiveData(Class classe, int iId, boolean bResultado, Object... oObjetos)
    {
        FrmAtualizacaoCad frmAtualizacaoCad = null;

        try
        {
            //Zera a posicao
            iPosicao = -1;

            //Se clicou no Adapter de cursos
            if ( classe == AdapterCursos.class )
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
                    if ( iId == R.id.lnlExcluirCurso )
                    {
                        //Cria um dialogAlert com as opções
                        dlgExcluir = Apoio.criarAlertDialog(getActivity(), getString(R.string.sim), getString(R.string.nao), getString(R.string.atencao), getString(R.string.msg_atualizacao_cad_cursos_excluir), this);
                        dlgExcluir.show();
                        Apoio.trocaCoresBotoesDialog(dlgExcluir, getActivity());
                    }
                    //Se clicou na linha do curso
                    else if ( iId == R.id.lnlCurso )
                    {
                        //Chama o método que abre a alteração do curso
                        frmAtualizacaoCad = (FrmAtualizacaoCad)this.getActivity();
                        frmAtualizacaoCad.chamaTelaNovoAtualizacaoCadCursos(arrCursos.get(iPosicao));
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

                //Chama a tela de cadastro de cursos
                frmAtualizacaoCad = (FrmAtualizacaoCad)this.getActivity();
                frmAtualizacaoCad.chamaTelaNovoAtualizacaoCadCursos(null);
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
                    excluirCurso(iPosicao);
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