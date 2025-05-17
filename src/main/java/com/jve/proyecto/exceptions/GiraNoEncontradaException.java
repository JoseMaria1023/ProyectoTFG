package com.jve.proyecto.exceptions;

public class GiraNoEncontradaException extends BaseException {

    public GiraNoEncontradaException() {
        super("Gira no encontrada", "401");
    }

}
