package com.jve.proyecto.converter;

import com.jve.proyecto.dto.AsientoDTO;
import com.jve.proyecto.entity.Asiento;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AsientoConverter {

    private final ModelMapper modelMapper;

    @Autowired
    public AsientoConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public AsientoDTO toDto(Asiento asiento) {
        return modelMapper.map(asiento, AsientoDTO.class);
    }

    public Asiento toEntity(AsientoDTO asientoDTO) {
        return modelMapper.map(asientoDTO, Asiento.class);
    }
}