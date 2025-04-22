package com.jve.proyecto.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.jve.proyecto.dto.AsientoDTO;
import com.jve.proyecto.entity.Asiento;
import com.jve.proyecto.entity.Concierto;
import com.jve.proyecto.repository.AsientoRepository;
import com.jve.proyecto.repository.ConciertoRepository;

@Service
public class AsientoService {

    private final AsientoRepository asientoRepository;
    private final ConciertoRepository conciertoRepository;
    private final ModelMapper modelMapper;

    public AsientoService(AsientoRepository asientoRepository, ConciertoRepository conciertoRepository, ModelMapper modelMapper) {
        this.asientoRepository = asientoRepository;
        this.conciertoRepository = conciertoRepository;
        this.modelMapper = modelMapper;
    }

    public AsientoDTO crearAsiento(AsientoDTO asientoDTO) {
        if (asientoDTO.getConciertoId() == null) {
            throw new RuntimeException("El ID del concierto es obligatorio.");
        }

        Concierto concierto = conciertoRepository.findById(asientoDTO.getConciertoId())
                .orElseThrow(() -> new RuntimeException("Concierto no encontrado con ID: " + asientoDTO.getConciertoId()));

        Asiento asiento = Asiento.builder()
                .numeracion(asientoDTO.getNumeracion())
                .fila(asientoDTO.getFila())
                .columna(asientoDTO.getColumna())
                .tipo(asientoDTO.getTipo() != null
                        ? Asiento.TipoAsiento.valueOf(asientoDTO.getTipo().toUpperCase())
                        : Asiento.TipoAsiento.NORMAL)
                .concierto(concierto)
                .build();

        Asiento asientoGuardado = asientoRepository.save(asiento);

        return modelMapper.map(asientoGuardado, AsientoDTO.class);
    }

    public AsientoDTO obtenerAsientoPorId(Long id) {
        Asiento asiento = asientoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Asiento no encontrado con ID: " + id));
        return modelMapper.map(asiento, AsientoDTO.class);
    }

    public List<AsientoDTO> obtenerTodosLosAsientos() {
        return asientoRepository.findAll().stream()
                .map(asiento -> modelMapper.map(asiento, AsientoDTO.class))
                .collect(Collectors.toList());
    }

    public List<AsientoDTO> obtenerAsientosPorConcierto(Long conciertoId) {
        return asientoRepository.findByConciertoIdConcierto(conciertoId)
                .stream()
                .map(asiento -> modelMapper.map(asiento, AsientoDTO.class))
                .collect(Collectors.toList());
    }

    public AsientoDTO actualizarAsiento(Long id, AsientoDTO asientoDTO) {
        Asiento asiento = asientoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Asiento no encontrado con ID: " + id));
        modelMapper.map(asientoDTO, asiento);
        Asiento asientoActualizado = asientoRepository.save(asiento);
        return modelMapper.map(asientoActualizado, AsientoDTO.class);
    }

    public void eliminarAsiento(Long id) {
        Asiento asiento = asientoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Asiento no encontrado con ID: " + id));
        asientoRepository.delete(asiento);
    }
}
