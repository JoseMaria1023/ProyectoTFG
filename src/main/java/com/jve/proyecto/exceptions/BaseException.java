package com.jve.proyecto.exceptions;

public class BaseException extends RuntimeException {
    private final String codigo;
    public BaseException(String mensaje, String codigo) {
        super(mensaje);
        this.codigo = codigo;
    }
    public String getCodigo() {
        return codigo;
    }
}