package com.jve.proyecto.exceptions;

public class CredencialesInvalidasException extends BaseException {

    public CredencialesInvalidasException() {
        super("Usuario o contraseña incorrectos", "401");
    }
}