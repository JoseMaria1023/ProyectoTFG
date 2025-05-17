package com.jve.proyecto.exceptions;

public class MetodosDePagoException extends BaseException {

    public MetodosDePagoException() {
        super("El método de pago no puede estar vacio", "401");
    }

}
