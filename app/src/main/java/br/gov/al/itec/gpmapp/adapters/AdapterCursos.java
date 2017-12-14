package br.gov.al.itec.gpmapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import br.gov.al.itec.gpmapp.R;
import br.gov.al.itec.gpmapp.base.IComunicacaoGeral;
import br.gov.al.itec.gpmapp.models.Cursos;
import br.gov.al.itec.gpmapp.util.Apoio;
import br.gov.al.itec.gpmapp.util.DialogAlerta;
import br.gov.al.itec.gpmapp.util.LogTrace;

/**
 * Adapter para os cursos
 */
public class AdapterCursos extends RecyclerView.Adapter implements View.OnClickListener
{
    //Variaveis da classe
    private Context context = null;
    private ArrayList<Cursos> arrCursos = null;
    private IComunicacaoGeral comunicacaoGeral = null;

    /**
     * Construtor da classe
     */
    public AdapterCursos(Context contextParam, IComunicacaoGeral comunicacaoGeralParam, ArrayList<Cursos> arrCursosParam)
    {
        //Carrega variaveis da tela
        context = contextParam;
        arrCursos = arrCursosParam;
        comunicacaoGeral = comunicacaoGeralParam;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup vwgGrupo, int iTipoView)
    {
        View view = null;
        HolderCursos holderCursos = null;

        try
        {
            //Infla a view e inicia o view holder
            view = LayoutInflater.from(context).inflate(R.layout.lst_cursos, vwgGrupo, false);
            holderCursos = new HolderCursos(view);
        }
        catch(Exception err)
        {
            LogTrace.escreve(Apoio.localErro(getClass(), err), Apoio.getPathLogsSDCard(context), Apoio.getArqErr());
            DialogAlerta.show(context, Apoio.getMsgErr(getClass(), err), context.getString(R.string.atencao), context.getString(R.string.ok));
        }

        return holderCursos;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int iPosicao)
    {
        HolderCursos holderCursos = null;
        Cursos cursos = null;

        try
        {
            //Converte o view holder
            holderCursos = (HolderCursos) holder;

            //Obtem o registro
            cursos = arrCursos.get(iPosicao);

            //Seta os dados dos dependentes
            holderCursos.lblDescricao.setText(cursos.sDescricaoCurso);
            holderCursos.lblTipoCurso.setText(Apoio.retornaTipoCurso(cursos.sTipoCurso));
            holderCursos.lblCargaHoraria.setText(cursos.sCargaHorariaCurso);
            holderCursos.lblInstituicao.setText(cursos.sLocalCurso);
            holderCursos.lblDataConclusao.setText(Apoio.parseData(cursos.sDataConclusaoCurso, "yyyyMMdd", "dd/MM/yyyy"));

            //define o listener no excluir
            holderCursos.lnlExcluirCurso.setTag(iPosicao);
            holderCursos.lnlExcluirCurso.setOnClickListener(this);

            //define o listener no linearlayout
            holderCursos.lnlCurso.setTag(iPosicao);
            holderCursos.lnlCurso.setOnClickListener(this);
        }
        catch(Exception err)
        {
            LogTrace.escreve(Apoio.localErro(getClass(), err), Apoio.getPathLogsSDCard(context), Apoio.getArqErr());
            DialogAlerta.show(context, Apoio.getMsgErr(getClass(), err), context.getString(R.string.atencao), context.getString(R.string.ok));
        }
    }

    @Override
    public int getItemCount()
    {
        return arrCursos.size();
    }

    @Override
    public void onClick(View view)
    {
        try
        {
            //Passa o id do item para a activity
            comunicacaoGeral.comunicaGeral(getClass(), view.getId(), false, view.getTag());
        }
        catch(Exception err)
        {
            LogTrace.escreve(Apoio.localErro(getClass(), err), Apoio.getPathLogsSDCard(context), Apoio.getArqErr());
            DialogAlerta.show(context, Apoio.getMsgErr(getClass(), err), context.getString(R.string.atencao), context.getString(R.string.ok));
        }
    }

    /**
     * Classe interna do pattern view holder
     */
    public class HolderCursos extends RecyclerView.ViewHolder
    {
        //Controles da classe
        final TextView lblDescricao;
        final TextView lblTipoCurso;
        final TextView lblCargaHoraria;
        final TextView lblInstituicao;
        final TextView lblDataConclusao;
        final LinearLayout lnlExcluirCurso;
        final LinearLayout lnlCurso;

        /**
         * Construtor da classe
         */
        public HolderCursos(View itemView)
        {
            super(itemView);

            //Carrega os controles da lista
            lnlCurso = (LinearLayout) itemView.findViewById(R.id.lnlCurso);
            lblDescricao = (TextView) itemView.findViewById(R.id.lblDescricao);
            lblTipoCurso = (TextView) itemView.findViewById(R.id.lblTipoCurso);
            lblCargaHoraria = (TextView) itemView.findViewById(R.id.lblCargaHoraria);
            lblInstituicao = (TextView) itemView.findViewById(R.id.lblInstituicao);
            lblDataConclusao = (TextView) itemView.findViewById(R.id.lblDataConclusao);
            lnlExcluirCurso = (LinearLayout) itemView.findViewById(R.id.lnlExcluirCurso);
        }
    }
}