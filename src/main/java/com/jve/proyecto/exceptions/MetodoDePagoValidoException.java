package com.jve.proyecto.exceptions;

public class MetodoDePagoValidoException extends BaseException {

    public MetodoDePagoValidoException() {
        super("El metodo de pago no es valido", "401");
    }

}
