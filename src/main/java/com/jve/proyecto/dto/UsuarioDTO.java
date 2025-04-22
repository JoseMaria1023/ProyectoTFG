package com.jve.proyecto.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioDTO {
    private Long idUsuario;
    private String nombre;
    private String apellidos;
    private String username;
    private String password;
    private String email;
    private String telefono;
    private String role;
    private Boolean activo;
}