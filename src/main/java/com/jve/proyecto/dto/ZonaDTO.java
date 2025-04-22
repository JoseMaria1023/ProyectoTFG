package com.jve.proyecto.dto;

import java.math.BigDecimal;

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
public class ZonaDTO {
    private Long idZona;
    private String nombre;
    private Long recintoId;
    private BigDecimal precioBase;
    private BigDecimal precioVIP;
}

