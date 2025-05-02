package com.jve.proyecto.converter;

import com.jve.proyecto.dto.GiraDTO;
import com.jve.proyecto.entity.Gira;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GiraConverter {

    private final ModelMapper modelMapper;

    @Autowired
    public GiraConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public GiraDTO toDto(Gira gira) {
        return modelMapper.map(gira, GiraDTO.class);
    }

    public Gira toEntity(GiraDTO giraDTO) {
        return modelMapper.map(giraDTO, Gira.class);
    }
}
