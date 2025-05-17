package com.jve.proyecto.exceptions;

public class ArtistaNoEncontradoException extends BaseException{

     public ArtistaNoEncontradoException() {
        super("No se ha encontrado el artista", "401");
    }
}
