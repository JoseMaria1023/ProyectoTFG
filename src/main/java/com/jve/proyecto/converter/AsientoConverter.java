package com.jve.proyecto.converter;

import com.jve.proyecto.dto.AsientoDTO;
import com.jve.proyecto.entity.Asiento;

public class AsientoConverter {

    public static AsientoDTO toDto(Asiento asiento) {
        return ModelMapperUtils.getMapper().map(asiento, AsientoDTO.class);
    }

    public static Asiento toEntity(AsientoDTO asientoDTO) {
        return ModelMapperUtils.getMapper().map(asientoDTO, Asiento.class);
    }
}