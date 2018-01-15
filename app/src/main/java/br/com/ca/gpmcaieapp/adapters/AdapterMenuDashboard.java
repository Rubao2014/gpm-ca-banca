package br.com.ca.gpmcaieapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import br.com.ca.gpmcaieapp.models.ItensMenu;
import br.com.ca.gpmcaieapp.R;
import br.com.ca.gpmcaieapp.base.IComunicacaoGeral;
import br.com.ca.gpmcaieapp.util.Apoio;
import br.com.ca.gpmcaieapp.util.DialogAlerta;
import br.com.ca.gpmcaieapp.util.LogTrace;

/**
 * Adapter para os cartoes
 */
public class AdapterMenuDashboard extends RecyclerView.Adapter implements View.OnClickListener
{
    //Variaveis da classe
    private Context context = null;
    private ArrayList<ItensMenu> arrItensMenu = null;
    private IComunicacaoGeral comunicacaoGeral = null;

    /**
     * Construtor da classe
     */
    public AdapterMenuDashboard(Context contextParam, IComunicacaoGeral comunicacaoGeralParam, ArrayList<ItensMenu> arrItensParam)
    {
        //Carrega variaveis da tela
        context = contextParam;
        arrItensMenu = arrItensParam;
        comunicacaoGeral = comunicacaoGeralParam;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup vwgGrupo, int iTipoView)
    {
        View view = null;
        HolderItensMenu holderItensMenu = null;

        try
        {
            //Infla a view e inicia o view holder
            view = LayoutInflater.from(context).inflate(R.layout.lst_dashboard_menu, vwgGrupo, false);
            holderItensMenu = new HolderItensMenu(view);
        }
        catch(Exception err)
        {
            LogTrace.escreve(Apoio.localErro(getClass(), err), Apoio.getPathLogsSDCard(context), Apoio.getArqErr());
            DialogAlerta.show(context, Apoio.getMsgErr(getClass(), err), context.getString(R.string.atencao), context.getString(R.string.ok));
        }

        return holderItensMenu;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int iPosicao)
    {
        HolderItensMenu holderItensMenu = null;
        ItensMenu itensMenu = null;

        try
        {
            //Converte o view holder
            holderItensMenu = (HolderItensMenu)holder;

            //Obtem o registro
            itensMenu = arrItensMenu.get(iPosicao);

            //Seta o primeiro titulo do menu
            holderItensMenu.lblPrimeiroTitulo.setText(itensMenu.sPrimeiroTitulo);

            //seta o icone do menu
            holderItensMenu.imgItens.setImageResource(itensMenu.iIcone);

            //define o listener no linearlayout
            holderItensMenu.lnlMenuItem.setTag(iPosicao);
            holderItensMenu.lnlMenuItem.setOnClickListener(this);
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
        return arrItensMenu.size();
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
    public class HolderItensMenu extends RecyclerView.ViewHolder
    {
        //Controles da classe
        final TextView lblPrimeiroTitulo;
        final ImageView imgItens;
        final LinearLayout lnlMenuItem;

        /**
         * Construtor da classe
         */
        public HolderItensMenu(View itemView)
        {
            super(itemView);

            //Carrega os controles da lista
            lnlMenuItem =(LinearLayout) itemView.findViewById(R.id.lnlMenuItem);
            lblPrimeiroTitulo = (TextView) itemView.findViewById(R.id.lblPrimeiroTitulo);
            imgItens = (ImageView) itemView.findViewById(R.id.imgIcon);
        }
    }
}