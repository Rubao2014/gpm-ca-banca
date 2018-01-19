package br.com.ca.gpmcaieapp.adapters;


import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.ca.gpmcaieapp.R;
import br.com.ca.gpmcaieapp.models.Multa;
import br.com.ca.gpmcaieapp.models.Vacina;
import br.com.ca.gpmcaieapp.telas.FrmIWMultasLista;
import br.com.ca.gpmcaieapp.telas.FrmIWVacinasLista;


public class MultasListView extends ArrayAdapter<Multa> {

    private ArrayList<Multa> multas;
    private FrmIWMultasLista context;

    public MultasListView(FrmIWMultasLista context, ArrayList<Multa> multas) {
        super(context, R.layout.custom_multas_listview, multas);
        this.context = context;
        this.multas = multas;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Multa multa = getItem(position);

        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(R.layout.custom_multas_listview, null, true);

        TextView textMultaDesc = (TextView) convertView.findViewById(R.id.txtMultaDesc);
        TextView textMultaPts = (TextView) convertView.findViewById(R.id.txtMultaPts);
        ImageView imgViewStatus = (ImageView) convertView.findViewById(R.id.imageViewStatus);

        textMultaDesc.setText(multa.getDesc());
        textMultaPts.setText(String.valueOf(multa.getPts()));

        int imageResouce = getImageResouce(multa.getPts());
        imgViewStatus.setImageResource(imageResouce);

        return convertView;
    }

    private int getImageResouce(Integer multaPts) {
        final int SEVERIDADE_BAIXA     =   0;
        final int SEVERIDADE_MODERADA  =   1;
        final int SEVERIDADE_ALTA      =   2;

        final int severidade =
                (multaPts >= 0 && multaPts <= 3) ? SEVERIDADE_BAIXA :
                (multaPts >= 4 && multaPts <= 7) ? SEVERIDADE_MODERADA :
                (multaPts >= 8) ? SEVERIDADE_ALTA : SEVERIDADE_ALTA;

        switch (severidade) {
            case SEVERIDADE_BAIXA:           return R.drawable.multa_blue_alert;
            case SEVERIDADE_MODERADA:        return R.drawable.multa_yellow_alert;
            default: case SEVERIDADE_ALTA:   return R.drawable.multa_red_alert;
        }

    }

}
