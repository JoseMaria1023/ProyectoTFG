package com.jve.proyecto.dto;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Valid
@Builder
public class ArtistaDTO {
    private Long idArtista;
    private String nombre;
    private String apellidos;
    private String username;
    private String password;
    private String descripcion;
    private String generoMusical;
    private String foto;
}