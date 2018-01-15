package br.com.ca.gpmcaieapp.adapters;

/**
 * Created by Rubens on 06/12/16.
 */
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.ca.gpmcaieapp.R;


public class DependentesBeneficiariosListView extends ArrayAdapter<String> {
    private String[] list_nomes;
    private ArrayList<String> array_list_nomes;
    private String[] list_plano_odontologico;
    private ArrayList<String> array_list_plano_odontologico;
    private String[] list_situacao;
    private ArrayList<String> array_list_situacao;
    private Activity context;


    public DependentesBeneficiariosListView(Activity context, String[] list_nomes, String[] list_situacao) {
        super(context, R.layout.custom_matriculas_listview, list_nomes);
        this.context = context;
        this.list_nomes = list_nomes;
        this.list_situacao = list_situacao;
    }

    public DependentesBeneficiariosListView(Activity context, ArrayList<String> list_nomes) {
        super(context, R.layout.custom_matriculas_listview, list_nomes);
        this.context = context;
        this.array_list_nomes = list_nomes;
    }

    public DependentesBeneficiariosListView(Activity context, ArrayList<String> list_nomes, ArrayList<String> array_list_plano_odontologico, ArrayList<String> list_situacao) {
        super(context, R.layout.custom_matriculas_listview, list_nomes);
        this.context = context;
        this.array_list_nomes = list_nomes;
        this.array_list_plano_odontologico = array_list_plano_odontologico;
        this.array_list_situacao = list_situacao;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.custom_dependentes_beneficiarios_listview, null, true);
        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewNome);
        TextView textViewPO = (TextView) listViewItem.findViewById(R.id.textViewPO);
        TextView textViewSituacao = (TextView) listViewItem.findViewById(R.id.textViewSituacao);


        textViewName.setText(this.array_list_nomes.get(position).toString());
        textViewPO.setText(this.array_list_plano_odontologico.get(position).toString());
        textViewSituacao.setText(this.array_list_situacao.get(position).toString());

        return listViewItem;
    }



}
