package com.jve.proyecto.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.jve.proyecto.configuration.ModelMapperConfig;
import com.jve.proyecto.dto.GiraDTO;
import com.jve.proyecto.entity.Artista;
import com.jve.proyecto.entity.Gira;
import com.jve.proyecto.repository.ArtistaRepository;
import com.jve.proyecto.repository.GiraRepository;
import org.modelmapper.ModelMapper;

@Service
public class GiraService {

    private final GiraRepository giraRepository;
    private final ArtistaRepository artistaRepository;
    private final ModelMapper modelMapper;

    public GiraService(GiraRepository giraRepository, ArtistaRepository artistaRepository, ModelMapperConfig modelMapperConfig) {
        this.giraRepository = giraRepository;
        this.artistaRepository = artistaRepository;
        this.modelMapper = modelMapperConfig.modelMapper();
    }

    public GiraDTO crearGira(GiraDTO giraDTO) {
        if (giraDTO.getArtistaId() == null) {
            throw new RuntimeException("El ID del artista es obligatorio.");
        }
        Artista artista = artistaRepository.findById(giraDTO.getArtistaId())
                .orElseThrow(() -> new RuntimeException("Artista no encontrado con ID: " + giraDTO.getArtistaId()));
        
        Gira gira = Gira.builder()
                .nombre(giraDTO.getNombre())
                .descripcion(giraDTO.getDescripcion())
                .artista(artista)
                .build();

        Gira giraGuardada = giraRepository.save(gira);
        return modelMapper.map(giraGuardada, GiraDTO.class);
    }

    public GiraDTO obtenerGiraPorId(Long id) {
        Gira gira = giraRepository.findById(id).orElseThrow(() -> 
                new RuntimeException("Gira no encontrada con ID: " + id));
        GiraDTO dto = modelMapper.map(gira, GiraDTO.class);
        dto.setArtistaId(gira.getArtista().getIdArtista());
        return dto;
    }

    public List<GiraDTO> obtenerTodasLasGiras() {
        return giraRepository.findAll().stream()
                .map(gira -> {
                    GiraDTO dto = modelMapper.map(gira, GiraDTO.class);
                    dto.setArtistaId(gira.getArtista().getIdArtista());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public GiraDTO actualizarGira(Long id, GiraDTO giraDTO) {
        Gira gira = giraRepository.findById(id).orElseThrow(() -> 
                new RuntimeException("Gira no encontrada con ID: " + id));
        
        if (giraDTO.getArtistaId() != null) {
            Artista artista = artistaRepository.findById(giraDTO.getArtistaId())
                    .orElseThrow(() -> new RuntimeException("Artista no encontrado con ID: " + giraDTO.getArtistaId()));
            gira.setArtista(artista);
        }
        
        gira.setNombre(giraDTO.getNombre());
        gira.setDescripcion(giraDTO.getDescripcion());

        Gira giraActualizada = giraRepository.save(gira);
        GiraDTO dto = modelMapper.map(giraActualizada, GiraDTO.class);
        dto.setArtistaId(giraActualizada.getArtista().getIdArtista());
        return dto;
    }

    public void eliminarGira(Long id) {
        Gira gira = giraRepository.findById(id).orElseThrow(() -> 
            new RuntimeException("Gira no encontrada con ID: " + id));
        giraRepository.delete(gira);
    }
}
