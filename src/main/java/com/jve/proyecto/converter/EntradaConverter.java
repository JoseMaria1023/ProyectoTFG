package com.jve.proyecto.converter;

import com.jve.proyecto.dto.EntradaDTO;
import com.jve.proyecto.entity.Entrada;

public class EntradaConverter {

    public static EntradaDTO toDto(Entrada entrada) {
        return ModelMapperUtils.getMapper().map(entrada, EntradaDTO.class);
    }

    public static Entrada toEntity(EntradaDTO entradaDTO) {
        return ModelMapperUtils.getMapper().map(entradaDTO, Entrada.class);
    }
}