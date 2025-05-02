package com.jve.proyecto.converter;

import com.jve.proyecto.dto.RecintoDTO;
import com.jve.proyecto.entity.Recinto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RecintoConverter {

    private final ModelMapper modelMapper;

    @Autowired
    public RecintoConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public RecintoDTO toDto(Recinto recinto) {
        return modelMapper.map(recinto, RecintoDTO.class);
    }

    public Recinto toEntity(RecintoDTO recintoDTO) {
        return modelMapper.map(recintoDTO, Recinto.class);
    }
}
