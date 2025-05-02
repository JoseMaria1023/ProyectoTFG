package com.jve.proyecto.converter;

import com.jve.proyecto.dto.ZonaDTO;
import com.jve.proyecto.entity.Zona;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ZonaConverter {

    private final ModelMapper modelMapper;

    @Autowired
    public ZonaConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ZonaDTO toDto(Zona zona) {
        return modelMapper.map(zona, ZonaDTO.class);
    }

    public Zona toEntity(ZonaDTO zonaDTO) {
        return modelMapper.map(zonaDTO, Zona.class);
    }
}
