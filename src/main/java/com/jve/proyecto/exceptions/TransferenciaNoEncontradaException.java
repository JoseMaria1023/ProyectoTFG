package com.jve.proyecto.exceptions;

public class TransferenciaNoEncontradaException extends BaseException {

    public TransferenciaNoEncontradaException() {
        super("Transferencia no encontrada", "401");
    }

}
