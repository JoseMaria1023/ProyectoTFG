package com.jve.proyecto.exceptions;

public class PrecioEntradaException extends BaseException {

    public PrecioEntradaException() {
        super("El precio de la reventa supera el 10%", "401");
    }


}
