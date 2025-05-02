package com.jve.proyecto.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.jve.proyecto.dto.RecintoDTO;
import com.jve.proyecto.entity.Recinto;
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
                new RuntimeException("Recinto no encontrado con ID: " + id));
        return recintoConverter.toDto(recinto); // Usamos el converter
    }

    public List<RecintoDTO> obtenerTodosLosRecintos() {
        return recintoRepository.findAll().stream()
                .map(recinto -> recintoConverter.toDto(recinto)) // Usamos el converter
                .collect(Collectors.toList());
    }

    public RecintoDTO actualizarRecinto(Long id, RecintoDTO recintoDTO) {
        Recinto recinto = recintoRepository.findById(id).orElseThrow(() -> 
                new RuntimeException("Recinto no encontrado con ID: " + id));
        recintoConverter.toEntity(recintoDTO); // Convertimos el DTO a la entidad para actualizarla
        Recinto recintoActualizado = recintoRepository.save(recinto);
        return recintoConverter.toDto(recintoActualizado); // Usamos el converter para devolver el DTO actualizado
    }

    public void eliminarRecinto(Long id) {
        Recinto recinto = recintoRepository.findById(id).orElseThrow(() -> 
                new RuntimeException("Recinto no encontrado con ID: " + id));
        recintoRepository.delete(recinto);
    }
}
