package com.jve.proyecto.exceptions;

public class SubirImagenException extends BaseException {

 public SubirImagenException() {
        super("No se ha podido subir la imagen", "401");
    }
}