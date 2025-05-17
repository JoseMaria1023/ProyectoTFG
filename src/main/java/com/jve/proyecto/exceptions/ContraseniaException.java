package com.jve.proyecto.exceptions;

public class ContraseniaException  extends BaseException {

    public ContraseniaException() {
        super("La contraseña es muy corta", "401");
    }

}
