package br.com.ca.gpmcaieapp.adapters;

/**
 * Created by Rubens on 06/12/16.
 */
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;

import br.com.ca.gpmcaieapp.R;


public class MatriculasListView extends ArrayAdapter<String> {
    private String[] list_nr;
    private ArrayList<String> array_list_nr;
    private String[] list_statuses;
    private ArrayList<String> array_list_statuses;
    private Activity context;


    public MatriculasListView(Activity context, String[] list_nr, String[] list_statuses) {
        super(context, R.layout.custom_matriculas_listview, list_nr);
        this.context = context;
        this.list_nr = list_nr;
        this.list_statuses = list_statuses;

    }

    public MatriculasListView(Activity context, ArrayList<String> list_nr) {
        super(context, R.layout.custom_matriculas_listview, list_nr);
        this.context = context;
        this.array_list_nr = list_nr;

    }

    public MatriculasListView(Activity context, ArrayList<String> list_nr, ArrayList<String> list_statuses) {
        super(context, R.layout.custom_matriculas_listview, list_nr);
        this.context = context;
        this.array_list_nr = list_nr;
        this.array_list_statuses = list_statuses;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.custom_matriculas_listview, null, true);
        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewNome);
        //TextView textViewDesc = (TextView) listViewItem.findViewById(R.id.textViewContractValue);
        //ImageView image = (ImageView) listViewItem.findViewById(R.id.imageViewStatus);


        //Typeface custom_font = Typeface.createFromAsset(context.getAssets(), "fonts/futura_normal.ttf");
        //textViewName.setTypeface(custom_font);
        //textViewDesc.setTypeface(custom_font);

        textViewName.setText(
                context.getResources().getString(R.string.list_view_contrato_nr_label) + " " +
                        this.array_list_nr.get(position).toString());
        //textViewDesc.setText(
        //        context.getResources().getString(R.string.list_view_contrato_val_label) + " " +
        //                setCurrencyMask(this.array_list_values.get(position).toString()));

        //setImages(image, this.array_list_statuses.get(position).toString());

        return listViewItem;
    }

    private void setImages(ImageView image, String img_item) {


        switch (img_item) {

            case "A":
                image.setImageResource(R.drawable.checked);
                break;
            case "P":
                image.setImageResource(R.drawable.stopwatch);
                break;
            case "N":
                image.setImageResource(R.drawable.cancel);
                break;
            default:
                image.setImageResource(R.drawable.support);
                break;
        }
    }

    public static String setCurrencyMask(String in) {

        String cleanString = in.toString().replaceAll("[$,.]", "");

        double parsed = Double.parseDouble(cleanString);
        String formatted = NumberFormat.getCurrencyInstance().format((parsed));

        return formatted;
    }




}
