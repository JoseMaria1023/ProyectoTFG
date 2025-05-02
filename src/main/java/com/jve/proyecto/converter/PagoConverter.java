package com.jve.proyecto.converter;

import com.jve.proyecto.dto.PagoDTO;
import com.jve.proyecto.entity.Pago;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PagoConverter {

    private final ModelMapper modelMapper;

    @Autowired
    public PagoConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public PagoDTO toDto(Pago pago) {
        return modelMapper.map(pago, PagoDTO.class);
    }

    public Pago toEntity(PagoDTO pagoDTO) {
        return modelMapper.map(pagoDTO, Pago.class);
    }
}
