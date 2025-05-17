package com.jve.proyecto.exceptions;

public class EntradaEnPetenenciaException extends BaseException {

    public EntradaEnPetenenciaException() {
        super("La entrada no pertenece a la cuenta", "401");
    }

}
