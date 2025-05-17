package com.jve.proyecto.exceptions;

public class EntradaNoEncontradaException extends BaseException {

    public EntradaNoEncontradaException() {
        super("Entrada no encontrada", "401");
    }

}
