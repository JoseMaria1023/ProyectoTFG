package com.jve.proyecto.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jve.proyecto.converter.ZonaConverter;
import com.jve.proyecto.dto.ZonaDTO;
import com.jve.proyecto.entity.Recinto;
import com.jve.proyecto.entity.Zona;
import com.jve.proyecto.repository.RecintoRepository;
import com.jve.proyecto.repository.ZonaRepository;

@Service
public class ZonaService {

    private final ZonaRepository zonaRepository;
    private final RecintoRepository recintoRepository;
    private final ZonaConverter zonaConverter;

    public ZonaService(ZonaRepository zonaRepository,
                       RecintoRepository recintoRepository,
                       ZonaConverter zonaConverter) {
        this.zonaRepository = zonaRepository;
        this.recintoRepository = recintoRepository;
        this.zonaConverter = zonaConverter;
    }

    public ZonaDTO crearZona(ZonaDTO zonaDTO) {
        if (zonaDTO.getRecintoId() == null) {
            throw new IllegalArgumentException("El ID del recinto es obligatorio.");
        }

        Recinto recinto = recintoRepository.findById(zonaDTO.getRecintoId())
                .orElseThrow(() -> new RuntimeException("Recinto no encontrado con ID: " + zonaDTO.getRecintoId()));

        // Convertir DTO a entidad y asignar el recinto cargado
        Zona zona = zonaConverter.toEntity(zonaDTO);
        zona.setRecinto(recinto);

        Zona guardada = zonaRepository.save(zona);
        return zonaConverter.toDto(guardada);
    }

    public ZonaDTO obtenerZonaPorId(Long id) {
        Zona zona = zonaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Zona no encontrada con ID: " + id));
        return zonaConverter.toDto(zona);
    }

    public List<ZonaDTO> obtenerTodasLasZonas() {
        return zonaRepository.findAll().stream()
                .map(zonaConverter::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public ZonaDTO actualizarZona(Long id, ZonaDTO zonaDTO) {
        Zona existente = zonaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Zona no encontrada con ID: " + id));

        if (zonaDTO.getRecintoId() != null) {
            Recinto recinto = recintoRepository.findById(zonaDTO.getRecintoId())
                    .orElseThrow(() -> new RuntimeException("Recinto no encontrado con ID: " + zonaDTO.getRecintoId()));
            existente.setRecinto(recinto);
        }

        // Sobreescribir campos desde el DTO
        existente.setNombre(zonaDTO.getNombre());
        existente.setPrecioBase(zonaDTO.getPrecioBase());
        existente.setPrecioVIP(zonaDTO.getPrecioVIP());

        Zona actualizada = zonaRepository.save(existente);
        return zonaConverter.toDto(actualizada);
    }

    public void eliminarZona(Long id) {
        Zona zona = zonaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Zona no encontrada con ID: " + id));
        zonaRepository.delete(zona);
    }
}
