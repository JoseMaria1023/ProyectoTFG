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
public class RecintoDTO {
    private Long idRecinto;
    private String nombre;
    private String ubicacion;
    private Long capacidadTotal;
}

