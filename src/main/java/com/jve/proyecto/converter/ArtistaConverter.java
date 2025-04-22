package com.jve.proyecto.converter;

import com.jve.proyecto.dto.ArtistaDTO;
import com.jve.proyecto.entity.Artista;

public class ArtistaConverter {

    public static ArtistaDTO toDto(Artista artista) {
        return ModelMapperUtils.getMapper().map(artista, ArtistaDTO.class);
    }

    public static Artista toEntity(ArtistaDTO artistaDTO) {
        return ModelMapperUtils.getMapper().map(artistaDTO, Artista.class);
    }
}
