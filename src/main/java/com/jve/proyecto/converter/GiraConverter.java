package com.jve.proyecto.converter;

import com.jve.proyecto.dto.GiraDTO;
import com.jve.proyecto.entity.Gira;

public class GiraConverter {

    public static GiraDTO toDto(Gira gira) {
        return ModelMapperUtils.getMapper().map(gira, GiraDTO.class);
    }

    public static Gira toEntity(GiraDTO giraDTO) {
        return ModelMapperUtils.getMapper().map(giraDTO, Gira.class);
    }
}
