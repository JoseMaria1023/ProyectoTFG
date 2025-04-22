package com.jve.proyecto.converter;

import com.jve.proyecto.dto.PagoDTO;
import com.jve.proyecto.entity.Pago;

public class PagoConverter {

    public static PagoDTO toDto(Pago pago) {
        return ModelMapperUtils.getMapper().map(pago, PagoDTO.class);
    }

    public static Pago toEntity(PagoDTO pagoDTO) {
        return ModelMapperUtils.getMapper().map(pagoDTO, Pago.class);
    }
}
