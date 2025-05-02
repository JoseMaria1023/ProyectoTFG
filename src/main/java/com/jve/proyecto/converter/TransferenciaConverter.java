package com.jve.proyecto.converter;

import com.jve.proyecto.dto.TransferenciaDTO;
import com.jve.proyecto.entity.Transferencia;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransferenciaConverter {

    private final ModelMapper modelMapper;

    @Autowired
    public TransferenciaConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public TransferenciaDTO toDto(Transferencia transferencia) {
        return modelMapper.map(transferencia, TransferenciaDTO.class);
    }

    public Transferencia toEntity(TransferenciaDTO transferenciaDTO) {
        return modelMapper.map(transferenciaDTO, Transferencia.class);
    }
}
