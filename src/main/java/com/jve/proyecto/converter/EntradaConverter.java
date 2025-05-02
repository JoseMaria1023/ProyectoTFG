package com.jve.proyecto.converter;

import com.jve.proyecto.dto.EntradaDTO;
import com.jve.proyecto.entity.Entrada;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EntradaConverter {

    private final ModelMapper modelMapper;

    @Autowired
    public EntradaConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public EntradaDTO toDto(Entrada entrada) {
        return modelMapper.map(entrada, EntradaDTO.class);
    }

    public Entrada toEntity(EntradaDTO entradaDTO) {
        return modelMapper.map(entradaDTO, Entrada.class);
    }
}
