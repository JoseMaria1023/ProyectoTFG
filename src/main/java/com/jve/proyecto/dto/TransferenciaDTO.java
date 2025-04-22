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
public class TransferenciaDTO {
    private Long idTransferencia;
    private Long entradaId;
    private Long usuarioOrigenId;
    private Long usuarioDestinoId;
    private LocalDateTime fechaTransferencia;
    private String estado;
    private String comentario;
}

