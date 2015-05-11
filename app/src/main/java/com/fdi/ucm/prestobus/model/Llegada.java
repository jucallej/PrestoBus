package com.fdi.ucm.prestobus.model;

import java.util.concurrent.TimeUnit;

/**
 * Created by Julián and Jesús
 * More info on the REEDME
 */

/**
 * Clase del modelo que representa una llegada del autobús para una parada concreta
 */
public class Llegada {
    private String stopId;
    private String lineId;
    private String isHead;
    private String destination;
    private String busId;
    private String busTimeLeft;
    private String busDistance;
    private String longitude;
    private String latitude;
    private String busPositionType;

    /**
     * Constructor vacio necesario para el conversor de Json a clase Android
     */
    public Llegada() {
    }

    /**
     * Constructor con los distintos valores de la clase
     * @param stopId
     * @param lineId
     * @param isHead
     * @param destination
     * @param busId
     * @param busTimeLeft
     * @param busDistance
     * @param longitude
     * @param latitude
     * @param busPositionType
     */
    public Llegada(String stopId, String lineId, String isHead, String destination, String busId, String busTimeLeft, String busDistance, String longitude, String latitude, String busPositionType) {
        this.stopId = stopId;
        this.lineId = lineId;
        this.isHead = isHead;
        this.destination = destination;
        this.busId = busId;
        this.busTimeLeft = busTimeLeft;
        this.busDistance = busDistance;
        this.longitude = longitude;
        this.latitude = latitude;
        this.busPositionType = busPositionType;
    }

    /**
     * Devuelve el id de la parada
     * @return
     */
    public String getStopId() {
        return stopId;
    }

    /**
     * Ajusta el id de la parada
     * @param stopId
     */
    public void setStopId(String stopId) {
        this.stopId = stopId;
    }

    /**
     * Devuelve el id de la linea
     * @return
     */
    public String getLineId() {
        return lineId;
    }

    /**
     * Ajusta el id de la linea
     * @param lineId
     */
    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    /**
     * Deuelve un string indicando si es el primero en llegar
     * @return
     */
    public String getIsHead() {
        return isHead;
    }

    /**
     * Ajusta el string que indica si es el primero en llegar
     * @param isHead
     */
    public void setIsHead(String isHead) {
        this.isHead = isHead;
    }

    /**
     * Devuelve el destino del autobús
     * @return
     */
    public String getDestination() {
        return destination;
    }

    /**
     * Ajusta el string del destino del autobús
     * @param destination
     */
    public void setDestination(String destination) {
        this.destination = destination;
    }

    /**
     * Devuelve el id del autobús
     * @return
     */
    public String getBusId() {
        return busId;
    }

    /**
     * Ajusta el id del autobús
     * @param busId
     */
    public void setBusId(String busId) {
        this.busId = busId;
    }

    /**
     * Devuelve los milisegundos que le queda al autobús para llegar
     * @return
     */
    public String getBusTimeLeft() {
        return busTimeLeft;
    }

    /**
     * Ajusta los milisegundos que le queda al autobús para llegar
     * @param busTimeLeft
     */
    public void setBusTimeLeft(String busTimeLeft) {
        this.busTimeLeft = busTimeLeft;
    }

    /**
     * Devuelve la distancia a la que está el autobús
     * @return
     */
    public String getBusDistance() {
        return busDistance;
    }

    /**
     * Ajusta la distancia a la que está el autobús.
     * @param busDistance
     */
    public void setBusDistance(String busDistance) {
        this.busDistance = busDistance;
    }

    /**
     * Devuelve la longitud donde está el autobús en ese momento
     * @return
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * Ajusta longitud donde está el autobús en ese mommento
     * @param longitude
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    /**
     * Devuelve la latitud donde está el autobús en ese momento
     * @return
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * Ajusta latitude donde está el autobús en ese mommento
     * @param latitude
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    /**
     * Devuelve el tipo de posición del autobús
     * @return
     */
    public String getBusPositionType() {
        return busPositionType;
    }

    /**
     * Ajusta el tipo de posición del autobús
     * @param busPositionType
     */
    public void setBusPositionType(String busPositionType) {
        this.busPositionType = busPositionType;
    }

    /**
     * Devuelve un string con toda la información relevante de llegada del autobús
     * @return string con toda la información relevante de llegada del autobús
     */
    @Override
    public String toString() {
        String tiempo = this.getTimeLeftFormated();

        return "Línea " + this.getLineId() + " - " + tiempo + ".";
    }

    /**
     * Devuelve un string con el tiempo formateado de manera correcta para mostrarlo
     * @return un string con el tiempo formateado de manera correcta para mostrarlo
     */
    public String getTimeLeftFormated() {
        long millis = Integer.parseInt(this.getBusTimeLeft()) * 1000;
        String tiempo = "+20 min";

        if (millis <= 1200000) tiempo = String.format("%d min, %d sec", TimeUnit.MILLISECONDS.toMinutes(millis), TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));

        return tiempo;
    }
}
