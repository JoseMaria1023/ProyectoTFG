package com.jve.proyecto.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jve.proyecto.dto.AsientoDTO;
import com.jve.proyecto.entity.Asiento;
import com.jve.proyecto.entity.Concierto;
import com.jve.proyecto.exceptions.AsientoNoEncontradoException;
import com.jve.proyecto.exceptions.ConciertoNoEncontradoException;
import com.jve.proyecto.repository.AsientoRepository;
import com.jve.proyecto.repository.ConciertoRepository;


import com.jve.proyecto.converter.AsientoConverter;

@Service
public class AsientoService {

    private final AsientoRepository asientoRepository;
    private final ConciertoRepository conciertoRepository;
    private final AsientoConverter asientoConverter;

    public AsientoService(AsientoRepository asientoRepository, ConciertoRepository conciertoRepository, AsientoConverter asientoConverter) {
        this.asientoRepository = asientoRepository;
        this.conciertoRepository = conciertoRepository;
        this.asientoConverter = asientoConverter;
    }

 public AsientoDTO crearAsiento(AsientoDTO dto) {
    if (dto.getConciertoId() == null) {
        throw new RuntimeException("El ID del concierto es obligatorio.");
    }

    Asiento asiento = asientoConverter.toEntity(dto);

    Concierto concierto = conciertoRepository.findById(dto.getConciertoId()).orElseThrow(ConciertoNoEncontradoException::new);
    asiento.setConcierto(concierto);

    if (dto.getTipo() == null || dto.getTipo().isBlank()) {
        asiento.setTipo(Asiento.TipoAsiento.NORMAL);
    } else {
        asiento.setTipo(Asiento.TipoAsiento.valueOf(dto.getTipo().toUpperCase()));
    }

    Asiento guardado = asientoRepository.save(asiento);
    return asientoConverter.toDto(guardado);
}


    public AsientoDTO TraerAsientoPorId(Long id) {
        Asiento asiento = asientoRepository.findById(id).orElseThrow(() -> new AsientoNoEncontradoException());
        return asientoConverter.toDto(asiento);
    }

    
    public List<AsientoDTO> TraerTodosLosAsientos() {
        return asientoRepository.findAll().stream().map(asiento -> asientoConverter.toDto(asiento))
        .collect(Collectors.toList());
    }

    public List<AsientoDTO> TraerAsientosPorConcierto(Long conciertoId) {
        return asientoRepository.findByConciertoIdConcierto(conciertoId).stream().map(asiento -> {
        AsientoDTO dto = asientoConverter.toDto(asiento);
        dto.setOcupado(asiento.getEntradas() != null && !asiento.getEntradas().isEmpty());
        return dto;
                })
        .collect(Collectors.toList());
    }
    
    
    @Transactional
    public AsientoDTO actualizarAsiento(Long id, AsientoDTO asientoDTO) {
        Asiento asiento = asientoRepository.findById(id).orElseThrow(() -> new AsientoNoEncontradoException());

        if (asientoDTO.getNumeracion() != null) {
            asiento.setNumeracion(asientoDTO.getNumeracion());
        }
        if (asientoDTO.getFila() != null) {
            asiento.setFila(asientoDTO.getFila());
        }
        if (asientoDTO.getColumna() != null) {
            asiento.setColumna(asientoDTO.getColumna());
        }
        if (asientoDTO.getTipo() != null) {
            asiento.setTipo(Asiento.TipoAsiento.valueOf(asientoDTO.getTipo().toUpperCase()));
        }

        Asiento actualizado = asientoRepository.save(asiento);

        return asientoConverter.toDto(actualizado);
    }

    public void eliminarAsiento(Long id) {
        Asiento asiento = asientoRepository.findById(id).orElseThrow(() -> new AsientoNoEncontradoException());
        asientoRepository.delete(asiento);
    }
}
