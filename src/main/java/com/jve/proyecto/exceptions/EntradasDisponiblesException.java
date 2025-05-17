package com.jve.proyecto.exceptions;

public class EntradasDisponiblesException extends BaseException {

    public EntradasDisponiblesException() {
        super("Solo las entradas disponibles se pueden revender", "401");
    }

}
