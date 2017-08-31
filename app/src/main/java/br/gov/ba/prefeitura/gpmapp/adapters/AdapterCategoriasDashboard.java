package br.gov.ba.prefeitura.gpmapp.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import br.gov.ba.prefeitura.gpmapp.R;
import br.gov.ba.prefeitura.gpmapp.base.IComunicacaoGeral;
import br.gov.ba.prefeitura.gpmapp.models.CategoriasServicos;
import br.gov.ba.prefeitura.gpmapp.util.Apoio;
import br.gov.ba.prefeitura.gpmapp.util.DialogAlerta;
import br.gov.ba.prefeitura.gpmapp.util.LogTrace;

/**
 * Adapter para as categorias e serviços do dashboard
 */
public class AdapterCategoriasDashboard extends RecyclerView.Adapter implements View.OnClickListener
{
    //Variaveis da classe
    private Context context = null;
    private ArrayList<CategoriasServicos> arrCategoriasServicos = null;
    private IComunicacaoGeral comunicacaoGeral = null;

    /**
     * Construtor da classe
     */
    public AdapterCategoriasDashboard(Context contextParam, IComunicacaoGeral comunicacaoGeralParam, ArrayList<CategoriasServicos> arrCategoriasServicosParam)
    {
        //Carrega variaveis da tela
        context = contextParam;
        arrCategoriasServicos = arrCategoriasServicosParam;
        comunicacaoGeral = comunicacaoGeralParam;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup vwgGrupo, int iTipoView)
    {
        View view = null;
        HolderCategorias holderCategorias = null;

        try
        {
            //Infla a view e inicia o view holder
            view = LayoutInflater.from(context).inflate(R.layout.lst_categoria, vwgGrupo, false);
            holderCategorias = new HolderCategorias(view);
        }
        catch(Exception err)
        {
            LogTrace.escreve(Apoio.localErro(getClass(), err), Apoio.getPathLogsSDCard(context), Apoio.getArqErr());
            DialogAlerta.show(context, Apoio.getMsgErr(getClass(), err), context.getString(R.string.atencao), context.getString(R.string.ok));
        }

        return holderCategorias;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int iPosicao)
    {
        HolderCategorias holderCategorias = null;
        CategoriasServicos categoriasServicos = null;

        try
        {
            //Converte o view holder
            holderCategorias = (HolderCategorias)holder;

            //Obtem o registro
            categoriasServicos = arrCategoriasServicos.get(iPosicao);

            //Seta o titulo da categoria
            holderCategorias.lblNomeCategoria.setText(categoriasServicos.sNomeCategoria);

            //Se for a carta de servicos #1
            if ( categoriasServicos.sNomeCategoria.equals(context.getString(R.string.nome_servico_carta)) )
            {
                //Se for ativo
                if ( categoriasServicos.bAtivo )
                {
                    //seta o icone do menu
                    holderCategorias.imgCategoria.setImageResource(R.mipmap.oca_ac);
                    holderCategorias.lblNomeCategoria.setTextColor(ContextCompat.getColor(context, R.color.preto));
                }
                /*else
                {
                    //seta o icone do menu
                    holderCategorias.imgCategoria.setImageResource(R.mipmap.oca_ac);
                    holderCategorias.lblNomeCategoria.setTextColor(ContextCompat.getColor(context, R.color.cinza_claro));
                }*/
            }

            //Se for Detran #2
            if ( categoriasServicos.sNomeCategoria.equals(context.getString(R.string.nome_servico_detran)) )
            {
                //Se for ativo
                if ( categoriasServicos.bAtivo )
                {
                    //seta o icone do menu
                    holderCategorias.imgCategoria.setImageResource(R.mipmap.detran_ac_2);
                    holderCategorias.lblNomeCategoria.setTextColor(ContextCompat.getColor(context, R.color.preto));
                }
                /*
                else
                {
                    //seta o icone do menu
                    holderCategorias.imgCategoria.setImageResource(R.mipmap.detran_pontuacao);
                    holderCategorias.lblNomeCategoria.setTextColor(ContextCompat.getColor(context, R.color.cinza_claro));
                }
                */
            }
            //Se for SEFAZ #3
            if ( categoriasServicos.sNomeCategoria.equals(context.getString(R.string.nome_servico_sefaz)) )
            {
                //Se for ativo
                if ( categoriasServicos.bAtivo )
                {
                    //seta o icone do menu
                    holderCategorias.imgCategoria.setImageResource(R.mipmap.sefaz);
                    holderCategorias.lblNomeCategoria.setTextColor(ContextCompat.getColor(context, R.color.preto));
                }
                /*
                else
                {
                    //seta o icone do menu
                    holderCategorias.imgCategoria.setImageResource(R.mipmap.detran_pontuacao);
                    holderCategorias.lblNomeCategoria.setTextColor(ContextCompat.getColor(context, R.color.cinza_claro));
                }
                */
            }
            //define o listener no linearlayout
            holderCategorias.lnlCategoria.setTag(iPosicao);
            holderCategorias.lnlCategoria.setOnClickListener(this);
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
        return arrCategoriasServicos.size();
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
    public class HolderCategorias extends RecyclerView.ViewHolder
    {
        //Controles da classe
        final TextView lblNomeCategoria;
        final ImageView imgCategoria;
        final LinearLayout lnlCategoria;

        /**
         * Construtor da classe
         */
        public HolderCategorias(View itemView)
        {
            super(itemView);

            //Carrega os controles da lista
            lnlCategoria = (LinearLayout) itemView.findViewById(R.id.lnlCategoria);
            lblNomeCategoria = (TextView) itemView.findViewById(R.id.lblNomeCategoria);
            imgCategoria = (ImageView) itemView.findViewById(R.id.imgIconCategoria);
        }
    }
}