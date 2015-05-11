package com.fdi.ucm.prestobus.utilidades;

import android.app.Activity;
import android.webkit.JavascriptInterface;

import com.fdi.ucm.prestobus.model.Llegada;
import com.fdi.ucm.prestobus.model.Parada;

import org.apache.commons.lang3.text.WordUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Julián and Jesús
 * More info on the REEDME
 */

/**
 * Clase que hace de interfaz para la parte web. Los metodos de esta clase están marcados con anotaciones de manera especial para que se les pueda llamar desde javascript en la webview
 */
public class WebAppInterface {
    private Activity mContext;
    private Parada parada;

    /**
     * Constructor de la clase con la actividad atual, y la parada de la que se consultarán los datos
     * @param c
     * @param parada
     */
    public WebAppInterface(Activity c, Parada parada){
        this.parada = parada;
        mContext = c;
    }

    /**
     * Constructor con solo la actividad actual
     * @param mContext
     */
    public WebAppInterface(Activity mContext) {
        this.mContext = mContext;
    }

    /**
     * Ajusta la parada que se va a consultar desde javascript
     * @param parada
     */
    public void setParada(Parada parada) {
        this.parada = parada;
    }

    /**
     * Devuelve la parada que se va a consultar desde javascript
     * @return
     */
    private Parada getParada(){
        return parada;
    }

    /**
     * Devuelve las lineas que pasan por esta parada, que actualmente no tienen autobuses llegando
     * @return
     */
    private List<String> getLineasSinBus(){
        ArrayList<String> linesSinBus = getParada().getLines();

        for (Llegada llegada : parada.getListaLLegadas()){
            boolean busquedaLinea = true;
            int i = 0;
            while (busquedaLinea && linesSinBus.size() > i){
                String idLinea = linesSinBus.get(i);
                if (idLinea.equalsIgnoreCase(llegada.getLineId())){
                    busquedaLinea = false;
                    linesSinBus.remove(i);
                }
                else {
                    i++;
                }
            }
        }

        return linesSinBus;
    }

    /**
     * Metodo que se puede consultar desde javasctipt, y devuelve el id de la parada
     * @return
     */
    @JavascriptInterface
    public String getIdParada(){
        return parada.getNode();
    }

    /**
     * Metodo que se puede consultar desde javasctipt, y devuelve el nombre de la parada
     * @return
     */
    @JavascriptInterface
    public String getNombreParada(){
        return WordUtils.capitalize(getParada().getName());
    }

    /**
     * Metodo que se puede consultar desde javasctipt, y devuelve el size de la lista de llegadas de autobuses de la parada
     * @return
     */
    @JavascriptInterface
    public int getSizeLLegadas(){
        return getParada().getListaLLegadas().size();
    }

    /**
     * Metodo que se puede consultar desde javasctipt, y devuelve a partir del indice de llegada que quieras (0...getSizeLLegadas) la linea de esa llegada
     * @return
     */
    @JavascriptInterface
    public String getLLegadaLinea(int i){
        return getParada().getListaLLegadas().get(i).getLineId();
    }

    /**
     * Metodo que se puede consultar desde javasctipt, y devuelve a partir del indice de llegada que quieras (0...getSizeLLegadas) el destino de esa llegada
     * @return
     */
    @JavascriptInterface
    public String getLLegadaDestino(int i){
        return getParada().getListaLLegadas().get(i).getDestination();
    }

    /**
     * Metodo que se puede consultar desde javasctipt, y devuelve a partir del indice de llegada que quieras (0...getSizeLLegadas) el tiempo de espera formateado
     * @return
     */
    @JavascriptInterface
    public String getLLegadaTiempoEspera(int i){
        return getParada().getListaLLegadas().get(i).getTimeLeftFormated();
    }

    /**
     * Metodo que se puede consultar desde javasctipt, y devuelve la latitud de la parada
     * @return
     */
    @JavascriptInterface
    public String getLatitude(){
        return getParada().getLatitude();
    }

    /**
     * Metodo que se puede consultar desde javasctipt, y devuelve la longitud de la parada
     * @return
     */
    @JavascriptInterface
    public String getLongitude(){
        return getParada().getLongitude();
    }

    /**
     * Metodo que se puede consultar desde javasctipt, y devuelve el size de la lista de lineas sin autobuses actualmente
     * @return
     */
    @JavascriptInterface
    public String getSizeLineasSinBus(){
        List<String> linesSinBus = getLineasSinBus();

        return Integer.toString(linesSinBus.size());
    }

    /**
     * Metodo que se puede consultar desde javasctipt, y devuelve a partir del indice de lineas sin autobues el nombre de la linea
     * @return
     */
    @JavascriptInterface
    public String getLineasSinBus(int i){
        List<String> linesSinBus = getLineasSinBus();

        return linesSinBus.get(i);
    }

    /**
     * Comprueba si la parada actual es favorita o no
     * @return
     */
    @JavascriptInterface
    public boolean isFavorite(){
        return ((BusApplication) mContext.getApplication()).getParada(this.getIdParada()) != null;
    }

    /**
     * Añade la parada actual a los favoritos (actualizandola en la clase BusApplicaction, que a su vez se encarga de guardarlo en memoria permanente)
     */
    @JavascriptInterface
    public void addToFavorites(){
        ((BusApplication) mContext.getApplication()).AddFavorite(this.getParada(), this.mContext);
    }

    /**
     * Elimina la parada actual de los favoritos (actualizandola en la clase BusApplicaction, que a su vez se encarga de guardarlo en memoria permanente)
     */
    @JavascriptInterface
    public void removeToFavorites(){
        ((BusApplication) mContext.getApplication()).removeFavorite(this.getIdParada(), this.mContext);
    }
}
