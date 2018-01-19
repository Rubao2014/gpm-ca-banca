package br.com.ca.gpmcaieapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;

import br.com.ca.gpmcaieapp.R;
import br.com.ca.gpmcaieapp.models.Vacina;
import br.com.ca.gpmcaieapp.telas.FrmIWVacinasLista;


public class VacinasListView extends ArrayAdapter<Vacina> {

    private ArrayList<Vacina> vacinas;
    private FrmIWVacinasLista context;

    public VacinasListView(FrmIWVacinasLista context, ArrayList<Vacina> vacinas) {
        super(context, R.layout.custom_vacinas_listview, vacinas);
        this.context = context;
        this.vacinas = vacinas;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Vacina vacina = getItem(position);

        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(R.layout.custom_vacinas_listview, null, true);

        TextView textViewDesc = (TextView) convertView.findViewById(R.id.txtVaciDesc);
        CheckBox cboxFlag = (CheckBox) convertView.findViewById(R.id.cboxVaciFlag);
        ImageView imageViewStatus = (ImageView) convertView.findViewById(R.id.imageViewStatus);

        cboxFlag.setTag(vacina);
        cboxFlag.setOnCheckedChangeListener(context);

        // adapt visual effects
        cboxFlag.setChecked(vacina.getFlag() > 0);
        textViewDesc.setText(vacina.getDescricao());
        int resourceId = getImageResource(vacina.getFlag());
        imageViewStatus.setImageResource(resourceId);

        return convertView;
    }

    private void updateVacinaCNS(Vacina vacina) {

    }

    private int getImageResource(Integer flag) {
        return (flag == 0) ?
                R.drawable.vaccine_tohave :
                R.drawable.vaccine_had;
    }


}
