package com.fdi.ucm.prestobus.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fdi.ucm.prestobus.R;
import com.fdi.ucm.prestobus.model.Parada;
import com.fdi.ucm.prestobus.utilidades.BusApplication;

import org.apache.commons.lang3.text.WordUtils;

import java.util.List;

/**
 * Created by Julián and Jesús
 * More info on the REEDME
 */

/**
 * Crea un Adapter personalizado para usar con ListView, usado para la lista de favoritos de la pantalla principal.
 */
public class AdapterListParadas extends ArrayAdapter<Parada> {
    private Activity context;
    private List<Parada> objects;

    /**
     * Constructor
     * @param context activity actual donde estará la ListView
     * @param objects lista de paradas (En este caso paradas favoritas)
     */
    public AdapterListParadas(Activity context, List<Parada> objects) {
        super(context, R.layout.row_layout, objects);
        this.context = context;
        this.objects = objects;
    }

    /**
     * Metodo que teníamos que sobreesribir. Indica como se ve cada una de las filas de la ListView. No es necesario llamarlo directamente, lo llama la propia clase cuando se llena de objetos.
     * @param position posición del array, para indicar la parada favorita que queremos 'pintar en este momento'.
     * @param convertView parametro que pasa el metodo al sobrescribirlo. No lo usamos.
     * @param parent padre de la vista.
     * @return view de la fila creada.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_layout, parent, false);

        Parada parada = objects.get(position);
        TextView textView = (TextView) rowView.findViewById(R.id.textViewTitle);
        textView.setText(WordUtils.capitalize(parada.getName().toLowerCase()));

        LinearLayout layout = (LinearLayout) rowView.findViewById(R.id.lineaLayoutDetailArrivals);

        ((BusApplication) context.getApplication()).getAllArrivalsFromStop(this.context, layout, parada.getNode());

        return rowView;
    }
}
