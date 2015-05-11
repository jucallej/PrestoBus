package com.fdi.ucm.prestobus.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fdi.ucm.prestobus.utilidades.BusApplication;

import org.apache.commons.lang3.text.WordUtils;

/**
 * Created by Julián and Jesús
 * More info on the REEDME
 */

/**
 * Clase del modelo que representa una Linea de autobús
 */
@JsonIgnoreProperties({ "dateFirst", "dateEnd" })
public class Linea {
    private Integer groupNumber;
    private String line;
    private String label;
    private String nameA;
    private String nameB;

    /**
     * Constructor vacio necesario para el conversor de Json a clase Android
     */
    public Linea() {
    }


    /**
     * Constructor con los distintos valores de la clase
     * @param groupNumber
     * @param line
     * @param label
     * @param nameA
     * @param nameB
     */
    public Linea(Integer groupNumber, String line, String label, String nameA, String nameB) {
        this.groupNumber = groupNumber;
        this.line = line;
        this.label = label;
        this.nameA = nameA;
        this.nameB = nameB;
    }

    /**
     * Devuleve el GroupNumber
     * @return el GroupNumber
     */
    public Integer getGroupNumber() {
        return groupNumber;
    }

    /**
     * Ajusta el GroupNumber
     * @param groupNumber
     */
    public void setGroupNumber(Integer groupNumber) {
        this.groupNumber = groupNumber;
    }

    /**
     * Devuelve el string que representa la Linea
     * @return
     */
    public String getLine() {
        return line;
    }

    /**
     * Ajusta el String que representa la Linea
     * @param line
     */
    public void setLine(String line) {
        this.line = line;
    }

    /**
     * Devuelve el label de la Linea
     * @return
     */
    public String getLabel() {
        return label;
    }

    /**
     * Ajusta el String del label
     * @param label
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Devuelve el nombre del principio de la linea
     * @return
     */
    public String getNameA() {
        return nameA;
    }

    /**
     * Ajusta el nombre del principio de la linea
     * @param nameA
     */
    public void setNameA(String nameA) {
        this.nameA = nameA;
    }

    /**
     * Devuelve el nombre del final de la linea
     * @return
     */
    public String getNameB() {
        return nameB;
    }

    /**
     * Ajusta el nombre del final de la linea
     * @param nameB
     */
    public void setNameB(String nameB) {
        this.nameB = nameB;
    }

    /**
     * Devuelve un string con toda la información relevante de la linea
     * @return string con toda la información relevante de la linea
     */
    @Override
    public String toString() {
        return "Linea " + this.getLabel() + BusApplication.NEXT_LINE + BusApplication.TAB + WordUtils.capitalize(this.getNameA().toLowerCase()) + BusApplication.NEXT_LINE + BusApplication.TAB + WordUtils.capitalize(this.getNameB().toLowerCase());
    }
}
