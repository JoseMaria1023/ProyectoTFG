package com.jve.proyecto.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

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
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime fecha;
    private Long zonaId;
    private Long giraId;
    private String estado;
    private String nombreRecinto;
    private String nombreZona;
    private String nombreArtista;
    private String apellidosArtista;
}

