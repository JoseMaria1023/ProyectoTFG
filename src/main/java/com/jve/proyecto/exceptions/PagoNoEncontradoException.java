package com.jve.proyecto.exceptions;

public class PagoNoEncontradoException extends BaseException {

    public PagoNoEncontradoException() {
        super("Pago no encontrado", "401");
    }

}
