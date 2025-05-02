package com.jve.proyecto.converter;

import com.jve.proyecto.dto.ConciertoDTO;
import com.jve.proyecto.entity.Concierto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConciertoConverter {

    private final ModelMapper modelMapper;

    @Autowired
    public ConciertoConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ConciertoDTO toDto(Concierto concierto) {
        return modelMapper.map(concierto, ConciertoDTO.class);
    }

    public Concierto toEntity(ConciertoDTO conciertoDTO) {
        return modelMapper.map(conciertoDTO, Concierto.class);
    }
}
