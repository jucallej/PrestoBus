package com.fdi.ucm.prestobus.utilidades;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fdi.ucm.prestobus.ParadaListActivity;
import com.fdi.ucm.prestobus.R;
import com.fdi.ucm.prestobus.model.Linea;
import com.fdi.ucm.prestobus.model.Llegada;
import com.fdi.ucm.prestobus.model.Parada;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Julián and Jesús
 * More info on the REEDME
 */

/**
 * Clase que extiende a Applicacion, con todo lo que ello conlleva para Android. Se puede considerar una especie de clase static donde guardaremos los favoritos y la lista de lineas,
 * para no tener que estar preguntando constantemente por lo mismo y ahorrar trafico al usuario y hacer la App más rápida.
 */
public class BusApplication extends Application {
    public final static String NEXT_LINE = System.getProperty("line.separator");
    public final static String TAB = " ";

    private List<Parada> favoritos;
    private List<Linea> lineas;

    private boolean inicializado;


    /**
     * Constructor vacio de la clase. Inicializa el array de favoritos.
     */
    public BusApplication() {
        super();
        favoritos = new ArrayList<>();
        inicializado = false;
    }

    /**
     * Se encarga de leer los favoritos del fichero "data_json". Si no hay favoritos muestra un Toast informando al usuario omo añadirlos.
     * @param context
     */
    public void init(Context context){
        if (!inicializado) {
            try {
                InputStream stream = this.getBaseContext().openFileInput("data_json");
                ObjectMapper mapper = new ObjectMapper();

                List<Parada> paradas = mapper.readValue(stream, new TypeReference<List<Parada>>() {});
                favoritos.addAll(paradas);

            } catch (IOException e) {
                e.printStackTrace();
            }

            inicializado = true;

            if (favoritos.size() == 0){
                Toast toast = Toast.makeText(context, context.getString(R.string.noFav),  Toast.LENGTH_LONG);
                toast.show();
            }
        }
    }

    /**
     * Utilidad que usamos para añadir un TextView a un LinearLayout. Lo usamos en la lista de favoritos
     * @param layout
     * @param str
     * @param activity
     */
    public static void addTextViewToLayout(LinearLayout layout, String str, Context activity){
        TextView value = new TextView(activity);
        value.setText(str);
        value.setTextAppearance(activity, android.R.style.TextAppearance_DeviceDefault_Medium);
        layout.addView(value);
    }

    /**
     * Devuelve la lista de favoritos
     * @return
     */
    public List<Parada> getFavoritos() {
        return favoritos;
    }


    /**
     * Añade al ArrayAdapter la lista con las lineas. Si esta lista todavía no se había cargado, se llama a la clase Rest para que lo haga.
     * @param activity
     * @param adapter
     */
    public void getAllLines(Activity activity, ArrayAdapter<Linea> adapter) {
        if(lineas != null){
            adapter.addAll(lineas);
            adapter.notifyDataSetChanged();
        }
        else{
            lineas = new ArrayList<>();

            Rest.getAllLines(activity, adapter, lineas);
        }
    }

    /**
     * Añade al layout todas las proximas llegadas de los autobues a una parada. Si la parada no está en favoritos, o no se tienen las proximas llamadas se llama a la clase Rest
     * @param context
     * @param layout
     * @param node
     */
    public void getAllArrivalsFromStop(Activity context, LinearLayout layout, String node) {
        Parada paradaTemp = null;

        int i = 0;
        while (paradaTemp == null){
            if(favoritos.get(i).getNode().equals(node)) paradaTemp = favoritos.get(i);

            i++;
        }

        if (paradaTemp.getListaLLegadas() != null){
            int limite = 4;
            if (paradaTemp.getListaLLegadas().size() < limite) limite = paradaTemp.getListaLLegadas().size();

            for (int j = 0; j<limite; j++){
                addTextViewToLayout(layout, paradaTemp.getListaLLegadas().get(j).toString(), context);
            }

            if (paradaTemp.getListaLLegadas().size() == 0)  addTextViewToLayout(layout, context.getString(R.string.empty), context);

        }

        else{
            paradaTemp.setListaLLegadas(new ArrayList<Llegada>());
            Rest.getAllArrivalsFromStop(context, layout, node, paradaTemp.getListaLLegadas());
        }

    }

    /**
     * Devuelve una parada a partir de su id
     * @param idParada
     * @return
     */
    public Parada getParada(String idParada) {
        Parada paradaTemp = null;

        int i = 0;
        while (paradaTemp == null && i < this.favoritos.size()){
            if(favoritos.get(i).getNode().equals(idParada)) paradaTemp = favoritos.get(i);

            i++;
        }

        return  paradaTemp;
    }

    /**
     * Actualiza los datos guardados en el ficher "data_json" poniendo las paradas favoritas que hay en este momento
     */
    private void updateStoredData(){
        try {
            OutputStream stream = this.openFileOutput("data_json", MODE_PRIVATE);
            ObjectMapper mapper = new ObjectMapper();

            mapper.writeValue(stream, this.favoritos);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Elimina un favorito a partir de su id
     * @param idParada
     * @param mContext
     */
    public void removeFavorite(String idParada, Activity mContext) {
        if (this.getParada(idParada) != null) {
            boolean borrado = false;
            int i = 0;
            while (!borrado) {
                if (favoritos.get(i).getNode().equals(idParada)) {
                    favoritos.remove(i);
                    borrado = true;
                }

                i++;
            }

            updateStoredData();

            checkTabletMode(mContext);
        }
    }

    /**
     * Añade un favorito a partir de la parada
     * @param parada
     * @param mContext
     */
    public void AddFavorite(Parada parada, Activity mContext) {
        if (this.getParada(parada.getNode()) == null) {
            favoritos.add(parada);

            updateStoredData();

            checkTabletMode(mContext);
        }
    }

    /**
     * Comprueba si estamos en modo 'tablet', en cuyo caso se lanza un intent para ir a la lista de paradas favoritas
     * @param mContext
     */
    private void checkTabletMode(Activity mContext) {
        if (mContext.getClass().getName().equalsIgnoreCase("com.fdi.ucm.prestobus.ParadaListActivity")){
            //Estamos en modo tablet y tenemos que recargar

            Intent currentIntent = new Intent(this, ParadaListActivity.class);
            mContext.startActivity(currentIntent);
        }
    }

    /**
     * Borra toda la información de las llegadas de los favoritos. De manera que la proxima vez que se pidan estos datos, se actualizarán usando la clase Rest
     */
    public void refresh(){
        for (Parada parada : favoritos){
            parada.removeListaParadas();
        }
    }
}
