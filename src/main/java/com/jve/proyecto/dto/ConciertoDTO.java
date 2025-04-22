package com.jve.proyecto.dto;

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
public class ConciertoDTO {
    private Long idConcierto;
    private String nombre;
    private LocalDateTime fecha;
    private Long zonaId;
    private Long giraId;
    private String estado;
}

