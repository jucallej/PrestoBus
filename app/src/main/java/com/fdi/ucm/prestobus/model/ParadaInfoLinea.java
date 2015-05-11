package com.fdi.ucm.prestobus.model;

import com.fdi.ucm.prestobus.utilidades.BusApplication;

import org.apache.commons.lang3.text.WordUtils;

/**
 * Created by Julián and Jesús
 * More info on the REEDME
 */

/**
 * Clase del modelo que representa una parada conseguida a partir de una linea (da detalles extra como la distancia con la siguiente parada, el orden, o si es en un sentido de la linea o el inverso)
 */
public class ParadaInfoLinea {
    private Integer line;
    private String secDetail;
    private String orderDetail;
    private String node;
    private String distance;
    private String distancePreviousStop;
    private String name;
    private String latitude;
    private String longitude;

    /**
     * Constructor vacio necesario para el conversor de Json a clase Android
     */
    public ParadaInfoLinea() {
    }

    /**
     * Constructor con los distintos valores de la clase
     * @param line
     * @param secDetail
     * @param orderDetail
     * @param node
     * @param distance
     * @param distancePreviousStop
     * @param name
     * @param latitude
     * @param longitude
     */
    public ParadaInfoLinea(Integer line, String secDetail, String orderDetail, String node, String distance, String distancePreviousStop, String name, String latitude, String longitude) {
        this.line = line;
        this.secDetail = secDetail;
        this.orderDetail = orderDetail;
        this.node = node;
        this.distance = distance;
        this.distancePreviousStop = distancePreviousStop;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     *  Devuelve el id de la linea
     * @return
     */
    public Integer getLine() {
        return line;
    }

    /**
     * Ajusta el id de la linea
     * @param line
     */
    public void setLine(Integer line) {
        this.line = line;
    }

    /**
     * Devuelve un string usado para saber el sentido
     * @return
     */
    public String getSecDetail() {
        return secDetail;
    }

    /**
     * Ajusta el string usado para saber el sentido
     * @param secDetail
     */
    public void setSecDetail(String secDetail) {
        this.secDetail = secDetail;
    }

    /**
     * Devuleve un string indicando el orden
     * @return
     */
    public String getOrderDetail() {
        return orderDetail;
    }

    /**
     * Ajusta el string usado para indicar el orden
     * @param orderDetail
     */
    public void setOrderDetail(String orderDetail) {
        this.orderDetail = orderDetail;
    }

    /**
     * Devuleve el id de la parda
     * @return
     */
    public String getNode() {
        return node;
    }

    /**
     * Ajusta el id de la parada
     * @param node
     */
    public void setNode(String node) {
        this.node = node;
    }

    /**
     * Deuelve la distancia
     * @return
     */
    public String getDistance() {
        return distance;
    }

    /**
     * Ajusta la distancia
     * @param distance
     */
    public void setDistance(String distance) {
        this.distance = distance;
    }

    /**
     * Devuelve la distancia con la parada anterior
     * @return
     */
    public String getDistancePreviousStop() {
        return distancePreviousStop;
    }

    /**
     * Ajusta la distancia con la parada anterior
     * @param distancePreviousStop
     */
    public void setDistancePreviousStop(String distancePreviousStop) {
        this.distancePreviousStop = distancePreviousStop;
    }

    /**
     * Devuelve el nombre de la parada
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Ajusta el nombre de la parada anterior
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Devuelve la latitud
     * @return
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * Ajusta la latitud
     * @param latitude
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    /**
     * Devuelve la longitud
     * @return
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * Ajusta la longitud
     * @param longitude
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    /**
     * Devuelve un string con toda la información relevante de la paradaInfoLinea
     * @return
     */
    @Override
    public String toString() {
        return "Nº " + this.getNode() + BusApplication.NEXT_LINE + BusApplication.TAB + WordUtils.capitalize(this.getName().toLowerCase());
    }
}
