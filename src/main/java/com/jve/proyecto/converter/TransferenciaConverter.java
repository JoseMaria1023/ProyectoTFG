package com.jve.proyecto.converter;

import com.jve.proyecto.dto.TransferenciaDTO;
import com.jve.proyecto.entity.Transferencia;

public class TransferenciaConverter {

    public static TransferenciaDTO toDto(Transferencia transferencia) {
        return ModelMapperUtils.getMapper().map(transferencia, TransferenciaDTO.class);
    }

    public static Transferencia toEntity(TransferenciaDTO transferenciaDTO) {
        return ModelMapperUtils.getMapper().map(transferenciaDTO, Transferencia.class);
    }
}
