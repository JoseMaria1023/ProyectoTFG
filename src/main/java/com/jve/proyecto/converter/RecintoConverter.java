package com.jve.proyecto.converter;

import com.jve.proyecto.dto.RecintoDTO;
import com.jve.proyecto.entity.Recinto;

public class RecintoConverter {

    public static RecintoDTO toDto(Recinto recinto) {
        return ModelMapperUtils.getMapper().map(recinto, RecintoDTO.class);
    }

    public static Recinto toEntity(RecintoDTO recintoDTO) {
        return ModelMapperUtils.getMapper().map(recintoDTO, Recinto.class);
    }
}