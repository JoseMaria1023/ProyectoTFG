package com.jve.proyecto.converter;

import com.jve.proyecto.dto.ArtistaDTO;
import com.jve.proyecto.entity.Artista;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ArtistaConverter {

    private final ModelMapper modelMapper;

    @Autowired
    public ArtistaConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ArtistaDTO toDto(Artista artista) {
        return modelMapper.map(artista, ArtistaDTO.class);
    }

    public Artista toEntity(ArtistaDTO artistaDTO) {
        return modelMapper.map(artistaDTO, Artista.class);
    }
}
