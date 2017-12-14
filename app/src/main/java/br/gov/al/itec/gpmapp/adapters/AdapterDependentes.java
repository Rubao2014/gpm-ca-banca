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
import br.gov.al.itec.gpmapp.models.Dependentes;
import br.gov.al.itec.gpmapp.util.Apoio;
import br.gov.al.itec.gpmapp.util.DialogAlerta;
import br.gov.al.itec.gpmapp.util.LogTrace;

/**
 * Adapter para os dependentes
 */
public class AdapterDependentes extends RecyclerView.Adapter implements View.OnClickListener
{
    //Variaveis da classe
    private Context context = null;
    private ArrayList<Dependentes> arrDependentes = null;
    private IComunicacaoGeral comunicacaoGeral = null;

    /**
     * Construtor da classe
     */
    public AdapterDependentes(Context contextParam, IComunicacaoGeral comunicacaoGeralParam, ArrayList<Dependentes> arrDependentesParam)
    {
        //Carrega variaveis da tela
        context = contextParam;
        arrDependentes = arrDependentesParam;
        comunicacaoGeral = comunicacaoGeralParam;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup vwgGrupo, int iTipoView)
    {
        View view = null;
        HolderDependentes holderDependentes = null;

        try
        {
            //Infla a view e inicia o view holder
            view = LayoutInflater.from(context).inflate(R.layout.lst_dependentes, vwgGrupo, false);
            holderDependentes = new HolderDependentes(view);
        }
        catch(Exception err)
        {
            LogTrace.escreve(Apoio.localErro(getClass(), err), Apoio.getPathLogsSDCard(context), Apoio.getArqErr());
            DialogAlerta.show(context, Apoio.getMsgErr(getClass(), err), context.getString(R.string.atencao), context.getString(R.string.ok));
        }

        return holderDependentes;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int iPosicao)
    {
        HolderDependentes holderDependentes = null;
        Dependentes dependentes = null;

        try
        {
            //Converte o view holder
            holderDependentes = (HolderDependentes)holder;

            //Obtem o registro
            dependentes = arrDependentes.get(iPosicao);

            //Seta os dados dos dependentes
            holderDependentes.lblNome.setText(dependentes.sNome);
            holderDependentes.lblTipoDependente.setText(Apoio.retornaTipoDependente(dependentes.sTipoDependenteID));
            holderDependentes.lblSexo.setText(Apoio.retornaSexo(dependentes.sSexo));
            holderDependentes.lblDataNascimento.setText(Apoio.parseData(dependentes.sDataNascimento, "yyyyMMdd", "dd/MM/yyyy"));
            holderDependentes.lblCPF.setText(Apoio.formataDados(dependentes.sCPFDependente, "###.###.###-##"));

            //define o listener no excluir
            holderDependentes.lnlExcluirDependente.setTag(iPosicao);
            holderDependentes.lnlExcluirDependente.setOnClickListener(this);

            //define o listener no linearlayout
            holderDependentes.lnlDependente.setTag(iPosicao);
            holderDependentes.lnlDependente.setOnClickListener(this);
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
        return arrDependentes.size();
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
    public class HolderDependentes extends RecyclerView.ViewHolder
    {
        //Controles da classe
        final TextView lblNome;
        final TextView lblTipoDependente;
        final TextView lblSexo;
        final TextView lblDataNascimento;
        final TextView lblCPF;
        final LinearLayout lnlExcluirDependente;
        final LinearLayout lnlDependente;

        /**
         * Construtor da classe
         */
        public HolderDependentes(View itemView)
        {
            super(itemView);

            //Carrega os controles da lista
            lnlDependente = (LinearLayout) itemView.findViewById(R.id.lnlDependente);
            lblNome = (TextView) itemView.findViewById(R.id.lblNome);
            lblTipoDependente = (TextView) itemView.findViewById(R.id.lblTipoDependente);
            lblSexo = (TextView) itemView.findViewById(R.id.lblSexo);
            lblDataNascimento = (TextView) itemView.findViewById(R.id.lblDataNascimento);
            lblCPF = (TextView) itemView.findViewById(R.id.lblCPF);
            lnlExcluirDependente = (LinearLayout) itemView.findViewById(R.id.lnlExcluirDependente);
        }
    }
}