package com.jve.proyecto.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.jve.proyecto.dto.GiraDTO;
import com.jve.proyecto.entity.Artista;
import com.jve.proyecto.entity.Gira;
import com.jve.proyecto.exceptions.ArtistaNoEncontradoException;
import com.jve.proyecto.exceptions.GiraNoEncontradaException;
import com.jve.proyecto.repository.ArtistaRepository;
import com.jve.proyecto.repository.GiraRepository;
import com.jve.proyecto.converter.GiraConverter;

@Service
public class GiraService {

    private final GiraRepository giraRepository;
    private final ArtistaRepository artistaRepository;
    private final GiraConverter giraConverter;

    public GiraService(GiraRepository giraRepository, ArtistaRepository artistaRepository, GiraConverter giraConverter) {
        this.giraRepository = giraRepository;
        this.artistaRepository = artistaRepository;
        this.giraConverter = giraConverter;
    }

    public GiraDTO crearGira(GiraDTO giraDTO) {
    if (giraDTO.getArtistaId() == null) {
        throw new RuntimeException("El ID del artista es obligatorio.");
    }

    Artista artista = artistaRepository.findById(giraDTO.getArtistaId())
            .orElseThrow(() -> new ArtistaNoEncontradoException());

    Gira gira = giraConverter.toEntity(giraDTO);

    gira.setArtista(artista);

    Gira giraGuardada = giraRepository.save(gira);

    return giraConverter.toDto(giraGuardada);
}

    public GiraDTO TraerGiraPorId(Long id) {
        Gira gira = giraRepository.findById(id).orElseThrow(() -> new GiraNoEncontradaException());
        GiraDTO dto = giraConverter.toDto(gira);
        dto.setArtistaId(gira.getArtista().getIdArtista());
        return dto;
    }

    public List<GiraDTO> TraerTodasLasGiras() {
        return giraRepository.findAll().stream()
                .map(gira -> {
                    GiraDTO dto = giraConverter.toDto(gira);
                    dto.setArtistaId(gira.getArtista().getIdArtista());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public GiraDTO actualizarGira(Long id, GiraDTO giraDTO) {
    Gira giraExistente = giraRepository.findById(id)
            .orElseThrow(() -> new GiraNoEncontradaException());

    giraExistente.setNombre(giraDTO.getNombre());
    giraExistente.setDescripcion(giraDTO.getDescripcion());

    if (giraDTO.getArtistaId() != null) {
        Artista artista = artistaRepository.findById(giraDTO.getArtistaId())
                .orElseThrow(() -> new ArtistaNoEncontradoException());
        giraExistente.setArtista(artista);
    }

    Gira giraActualizada = giraRepository.save(giraExistente);
    return giraConverter.toDto(giraActualizada);
}


    public void eliminarGira(Long id) {
        Gira gira = giraRepository.findById(id).orElseThrow(() -> new GiraNoEncontradaException());
        giraRepository.delete(gira);
    }
}
