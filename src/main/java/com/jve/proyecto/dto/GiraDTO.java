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
public class GiraDTO {
    private Long idGira;
    private String nombre;
    private String descripcion;
    private Long artistaId;
}

