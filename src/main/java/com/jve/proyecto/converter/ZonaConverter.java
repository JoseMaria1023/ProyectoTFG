package com.jve.proyecto.converter;

import com.jve.proyecto.dto.ZonaDTO;
import com.jve.proyecto.entity.Zona;

public class ZonaConverter {

    public static ZonaDTO toDto(Zona zona) {
        return ModelMapperUtils.getMapper().map(zona, ZonaDTO.class);
    }

    public static Zona toEntity(ZonaDTO zonaDTO) {
        return ModelMapperUtils.getMapper().map(zonaDTO, Zona.class);
    }
}
