package com.jve.proyecto.exceptions;

public class UsuarioNoEncontradoException extends BaseException {

 public UsuarioNoEncontradoException() {
        super("No se encontrado al usuario", "401");
    }
}
