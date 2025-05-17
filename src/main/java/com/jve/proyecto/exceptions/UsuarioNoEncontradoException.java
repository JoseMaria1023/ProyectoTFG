package com.jve.proyecto.exceptions;

public class UsuarioNoEncontradoException extends BaseException {

 public UsuarioNoEncontradoException() {
        super("No se ha podido subir la imagen", "401");
    }
}
