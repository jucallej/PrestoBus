package com.fdi.ucm.prestobus.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Julián and Jesús
 * More info on the REEDME
 */

/**
 * Clase del modelo que representa una parada de una linea de autobús
 */
@JsonIgnoreProperties({ "listaLLegadas"})
public class Parada {
    private ArrayList<String> lines;
    private String node;
    private String name;
    private String latitude;
    private String longitude;

    private List<Llegada> listaLLegadas = null;

    /**
     * Constructor vacio necesario para el conversor de Json a clase Android
     */
    public Parada() {
    }

    /**
     * Constructor con los distintos valores de la clase
     * @param lines
     * @param node
     * @param name
     * @param latitude
     * @param longitude
     */
    public Parada(ArrayList<String> lines, String node, String name, String latitude, String longitude) {
        this.lines = lines;
        this.node = node;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        listaLLegadas = null;
    }

    /**
     * Elimina elementos redundantes que devuelve la API de la EMT pero que no nos hacen falta
     */
    public void formatLines(){
        ArrayList<String> linesTemp = new ArrayList<>();
        for (String line :lines){
            //No nos hace falta datos extra que da la api de la EMT
            linesTemp.add(line.substring(0, (line).length() - 2));
        }

        // Eliminamos repetidos (por ej una parada que es principio y final)
        Set<String> hs = new HashSet<>();
        hs.addAll(linesTemp);

        linesTemp.clear();
        linesTemp.addAll(hs);

        this.lines = linesTemp;
    }

    /**
     * Devuelve las lineas que pasasn por esa parada
     * @return las lienas que pasasn por esa parada
     */
    public ArrayList<String> getLines() {
        return lines;
    }

    /**
     * Devuelve el id que representa a esa parada
     * @return
     */
    public String getNode() {
        return node;
    }

    /**
     * Devuelve el nombre de la parada
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Ajusta el nombre de la parada
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Devuelve la latitud de la parada
     * @return
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * Devuelve la longitud de la parada
     * @return
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * Devuelve la lista con los proximos autobuses por llegar. Puede devolver null si no se han ajustado esta lista antes
     * @return
     */
    public List<Llegada> getListaLLegadas() {
        return listaLLegadas;
    }

    /**
     * Ajusta la lista con los proximos autobuses por llegar
     * @param listaLLegadas
     */
    public void setListaLLegadas(List<Llegada> listaLLegadas) {
        this.listaLLegadas = listaLLegadas;
    }

    /**
     * Pone a null la lista con los proximos autobuses por llegar
     */
    public void removeListaParadas() {
        this.listaLLegadas = null;
    }
}
