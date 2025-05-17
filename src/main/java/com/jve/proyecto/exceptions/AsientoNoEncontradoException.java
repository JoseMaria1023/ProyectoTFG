package com.jve.proyecto.exceptions;

public class AsientoNoEncontradoException extends BaseException {

    public AsientoNoEncontradoException() {
        super("Asiento no encontrado", "401");
    }
}
