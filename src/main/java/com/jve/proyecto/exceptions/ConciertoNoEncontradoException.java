package com.jve.proyecto.exceptions;

public class ConciertoNoEncontradoException extends BaseException {

    public ConciertoNoEncontradoException() {
        super("No se ha encontrado el concierto", "401");
    }

}
