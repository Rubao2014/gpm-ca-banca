package br.gov.al.itec.gpmapp.telas;

/**
 * Created by Rubens on 06/12/16.
 */
import android.app.Activity;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import br.gov.al.itec.gpmapp.R;


public class AdapterCartaServicos extends ArrayAdapter<String> {
    private String[] listSigla;
    private ArrayList<String> alSigla;
    private String[] listNome;
    private ArrayList<String> alNome;
    private String[] listUnidade;
    private ArrayList<String> alUnidade;
    private Activity context;


    public AdapterCartaServicos(Activity context, String[] listSiglaParam, String[] listNomeParam, String[] listUnidadeParam) {
        super(context, R.layout.lstview_carta_de_servicos);
        this.context = context;
        this.listSigla = listSiglaParam;
        this.listNome = listNomeParam;
        this.listUnidade = listUnidadeParam;

    }

    public AdapterCartaServicos(Activity context, ArrayList<String> alSiglaParam, ArrayList<String> alNomeParam, ArrayList<String> alUnidadeParam) {
        super(context, R.layout.lstview_carta_de_servicos);
        this.context = context;
        this.alSigla = alSiglaParam;
        this.alNome = alNomeParam;
        this.alUnidade = alUnidadeParam;

    }

    @Override
    public int getCount() {
        return alSigla.size();
    }


    @Nullable
    @Override
    public String getItem(int position) {
        return alSigla.get(position).toString();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.lstview_carta_de_servicos, null, true);

        TextView tvSigla = (TextView) listViewItem.findViewById(R.id.txtSigla);
        TextView tvNome = (TextView) listViewItem.findViewById(R.id.txtNomeServico);
        TextView tvUnidade = (TextView) listViewItem.findViewById(R.id.txtUnidade);

        tvSigla.setText(this.alSigla.get(position).toString());
        tvNome.setText(this.alNome.get(position).toString());
        tvUnidade.setText(this.alUnidade.get(position).toString());


        return listViewItem;
    }




}
