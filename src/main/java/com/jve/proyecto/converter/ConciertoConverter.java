package com.jve.proyecto.converter;

import com.jve.proyecto.dto.ConciertoDTO;
import com.jve.proyecto.entity.Concierto;

public class ConciertoConverter {

    public static ConciertoDTO toDto(Concierto concierto) {
        return ModelMapperUtils.getMapper().map(concierto, ConciertoDTO.class);
    }

    public static Concierto toEntity(ConciertoDTO conciertoDTO) {
        return ModelMapperUtils.getMapper().map(conciertoDTO, Concierto.class);
    }
}
