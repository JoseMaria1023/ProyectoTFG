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
public class EntradaDTO {
    private Long idEntrada;
    private String codigoQR;
    private String tipo;
    private BigDecimal precioVenta;
    private BigDecimal precioReventa;
    private LocalDateTime fechaCompra;
    private Long usuarioId;
    private Long conciertoId;
    private Long asientoId;
    private String estado;
    private ConciertoDTO concierto; // 👈 necesario para mostrar en frontend
    private AsientoDTO asiento;     //
}