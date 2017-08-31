package br.gov.ba.prefeitura.gpmapp.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import java.io.File;
import java.util.ArrayList;
import br.gov.ba.prefeitura.gpmapp.R;
import br.gov.ba.prefeitura.gpmapp.base.IComunicacaoGeral;
import br.gov.ba.prefeitura.gpmapp.util.Apoio;
import br.gov.ba.prefeitura.gpmapp.util.DialogAlerta;
import br.gov.ba.prefeitura.gpmapp.util.LogTrace;

/**
 * Adapter para as fotos
 */
public class AdapterFotos extends RecyclerView.Adapter implements View.OnClickListener
{
    //Variaveis da classe
    private Context context = null;
    private ArrayList<String> arrFotos = null;
    private IComunicacaoGeral comunicacaoGeral = null;

    /**
     * Construtor da classe
     */
    public AdapterFotos(Context contextParam, IComunicacaoGeral comunicacaoGeralParam, ArrayList<String> arrFotosParam)
    {
        //Carrega variaveis da tela
        context = contextParam;
        arrFotos = arrFotosParam;
        comunicacaoGeral = comunicacaoGeralParam;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup vwgGrupo, int iTipoView)
    {
        View view = null;
        HolderFotos holderFotos = null;

        try
        {
            //Infla a view e inicia o view holder
            view = LayoutInflater.from(context).inflate(R.layout.lst_fotos, vwgGrupo, false);
            holderFotos = new HolderFotos(view);
        }
        catch(Exception err)
        {
            LogTrace.escreve(Apoio.localErro(getClass(), err), Apoio.getPathLogsSDCard(context), Apoio.getArqErr());
            DialogAlerta.show(context, Apoio.getMsgErr(getClass(), err), context.getString(R.string.atencao), context.getString(R.string.ok));
        }

        return holderFotos;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int iPosicao)
    {
        HolderFotos holderFotos = null;
        String sFoto = null;
        Bitmap bitmapImagem = null;
        String sPathImagem = null;
        File fArq = null;
        boolean bExisteImagem = false;

        try
        {
            //Converte o view holder
            holderFotos = (HolderFotos) holder;

            //Obtem o registro
            sFoto = arrFotos.get(iPosicao);

            //Monta o caminho completo da foto
            sPathImagem = Apoio.getPathImagensSDCard(context) + "/" + sFoto;
            fArq = new File(sPathImagem);

            //Se existir o arquivo
            if ( fArq.exists() )
            {
                //Carrega a imagem no controle
                bitmapImagem = BitmapFactory.decodeFile(sPathImagem);
                holderFotos.imgFoto.setImageBitmap(bitmapImagem);

                //Seta como verdadeira a existencia da imagem
                bExisteImagem = true;
            }
            else
            {
                //Seta como falso a existencia da imagem
                bExisteImagem = false;
            }

            //Se não tiver foto, coloca a padrão
            if ( !bExisteImagem )
            {
                //Seta a imagem padrão
                holderFotos.imgFoto.setImageResource(R.drawable.logogovernoacre);
            }

            //define o listener no excluir
            holderFotos.rtlExcluir.setTag(iPosicao);
            holderFotos.rtlExcluir.setOnClickListener(this);

            //define o listener no linearlayout
            holderFotos.rtlFoto.setTag(iPosicao);
            holderFotos.rtlFoto.setOnClickListener(this);
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
        return arrFotos.size();
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
    public class HolderFotos extends RecyclerView.ViewHolder
    {
        //Controles da classe
        final ImageView imgFoto;
        final RelativeLayout rtlExcluir;
        final RelativeLayout rtlFoto;

        /**
         * Construtor da classe
         */
        public HolderFotos(View itemView)
        {
            super(itemView);

            //Carrega os controles da lista
            imgFoto = (ImageView) itemView.findViewById(R.id.imgFoto);
            rtlExcluir = (RelativeLayout) itemView.findViewById(R.id.rtlExcluir);
            rtlFoto = (RelativeLayout) itemView.findViewById(R.id.rtlFoto);
        }
    }
}