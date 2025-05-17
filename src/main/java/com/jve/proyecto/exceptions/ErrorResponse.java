package com.jve.proyecto.exceptions;

public class ErrorResponse {
    private String mensaje;
    private String codigo;

    public ErrorResponse(String mensaje, String codigo) {
        this.mensaje = mensaje;
        this.codigo = codigo;
    }

    public String getMensaje() { return mensaje; }
    public String getCodigo() { return codigo; }
}
