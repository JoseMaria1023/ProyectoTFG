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
public class AsientoDTO {
    private Long idAsiento;
    private Long conciertoId;
    private String numeracion;
    private Integer fila;
    private Integer columna;
    private String tipo;
}
