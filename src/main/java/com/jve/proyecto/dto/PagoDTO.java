package com.jve.proyecto.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
public class PagoDTO {
    private Long idPago;
    private BigDecimal cantidad;
    private LocalDateTime fechaPago;
    private String metodoPago;
    private String estado;
    private Long entradaId;
    private Long usuarioId;
}

