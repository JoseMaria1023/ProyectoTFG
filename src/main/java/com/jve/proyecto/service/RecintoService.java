package com.jve.proyecto.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.jve.proyecto.dto.RecintoDTO;
import com.jve.proyecto.entity.Recinto;
import com.jve.proyecto.exceptions.RecintoNoEncontradoException;
import com.jve.proyecto.repository.RecintoRepository;
import com.jve.proyecto.converter.RecintoConverter;

@Service
public class RecintoService {

    private final RecintoRepository recintoRepository;
    private final RecintoConverter recintoConverter; // Inyectamos el RecintoConverter

    public RecintoService(RecintoRepository recintoRepository, RecintoConverter recintoConverter) {
        this.recintoRepository = recintoRepository;
        this.recintoConverter = recintoConverter;
    }

    public RecintoDTO crearRecinto(RecintoDTO recintoDTO) {
        Recinto recinto = recintoConverter.toEntity(recintoDTO); // Usamos el converter para mapear de DTO a entidad
        Recinto recintoGuardado = recintoRepository.save(recinto);
        return recintoConverter.toDto(recintoGuardado); // Usamos el converter para mapear de entidad a DTO
    }

    public RecintoDTO obtenerRecintoPorId(Long id) {
        Recinto recinto = recintoRepository.findById(id).orElseThrow(() -> 
                new RecintoNoEncontradoException());
        return recintoConverter.toDto(recinto); // Usamos el converter
    }

    public List<RecintoDTO> obtenerTodosLosRecintos() {
        return recintoRepository.findAll().stream()
                .map(recinto -> recintoConverter.toDto(recinto)) // Usamos el converter
                .collect(Collectors.toList());
    }

   public RecintoDTO actualizarRecinto(Long id, RecintoDTO recintoDTO) {
    Recinto recinto = recintoRepository.findById(id).orElseThrow(() -> 
            new RecintoNoEncontradoException());

    recinto.setNombre(recintoDTO.getNombre());
    recinto.setUbicacion(recintoDTO.getUbicacion());
    recinto.setCapacidadTotal(recintoDTO.getCapacidadTotal());

    Recinto recintoActualizado = recintoRepository.save(recinto);
    return recintoConverter.toDto(recintoActualizado);
}

    public void eliminarRecinto(Long id) {
        Recinto recinto = recintoRepository.findById(id).orElseThrow(() -> 
                new RecintoNoEncontradoException());
        recintoRepository.delete(recinto);
    }
}
