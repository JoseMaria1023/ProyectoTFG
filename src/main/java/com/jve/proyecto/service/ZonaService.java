package com.jve.proyecto.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jve.proyecto.converter.ZonaConverter;
import com.jve.proyecto.dto.ZonaDTO;
import com.jve.proyecto.entity.Concierto;
import com.jve.proyecto.entity.Recinto;
import com.jve.proyecto.entity.Zona;
import com.jve.proyecto.exceptions.ConciertoNoEncontradoException;
import com.jve.proyecto.exceptions.RecintoNoEncontradoException;
import com.jve.proyecto.exceptions.ZonaNoEncontradaException;
import com.jve.proyecto.repository.ConciertoRepository;
import com.jve.proyecto.repository.RecintoRepository;
import com.jve.proyecto.repository.ZonaRepository;

@Service
public class ZonaService {

    private final ZonaRepository zonaRepository;
    private final RecintoRepository recintoRepository;
    private final ConciertoRepository conciertoRepository;
    private final ZonaConverter zonaConverter;

    public ZonaService(ZonaRepository zonaRepository,
                       RecintoRepository recintoRepository,
                       ZonaConverter zonaConverter,ConciertoRepository conciertoRepository) {
        this.zonaRepository = zonaRepository;
        this.recintoRepository = recintoRepository;
        this.zonaConverter = zonaConverter;
        this.conciertoRepository = conciertoRepository;
    }

    public ZonaDTO crearZona(ZonaDTO zonaDTO) {
        if (zonaDTO.getRecintoId() == null) {
            throw new IllegalArgumentException("El ID del recinto es obligatorio.");
        }

        Recinto recinto = recintoRepository.findById(zonaDTO.getRecintoId()).orElseThrow(() -> new RecintoNoEncontradoException());

        Zona zona = zonaConverter.toEntity(zonaDTO);
        zona.setRecinto(recinto);

        Zona guardada = zonaRepository.save(zona);
        return zonaConverter.toDto(guardada);
    }

    public ZonaDTO TraerZonaPorId(Long id) {
        Zona zona = zonaRepository.findById(id).orElseThrow(() -> new ZonaNoEncontradaException());
        return zonaConverter.toDto(zona);
    }

    public List<ZonaDTO> TraerTodasLasZonas() {
        return zonaRepository.findAll().stream().map(zonaConverter::toDto).collect(Collectors.toList());
    }

    @Transactional
    public ZonaDTO actualizarZona(Long id, ZonaDTO zonaDTO) {
        Zona existente = zonaRepository.findById(id).orElseThrow(() -> new ZonaNoEncontradaException());

        if (zonaDTO.getRecintoId() != null) {
            Recinto recinto = recintoRepository.findById(zonaDTO.getRecintoId()).orElseThrow(() -> new RecintoNoEncontradoException());
            existente.setRecinto(recinto);
        }

        existente.setNombre(zonaDTO.getNombre());
        existente.setPrecioBase(zonaDTO.getPrecioBase());
        existente.setPrecioVIP(zonaDTO.getPrecioVIP());

        Zona actualizada = zonaRepository.save(existente);
        return zonaConverter.toDto(actualizada);
    }

     @Transactional(readOnly = true)
    public ZonaDTO TraerZonaPorConcierto(Long conciertoId) {
        Concierto concierto = conciertoRepository.findById(conciertoId).orElseThrow(() -> new ConciertoNoEncontradoException());
        Zona zona = concierto.getZona();
        if (zona == null) {
            throw new ZonaNoEncontradaException();
        }
        return zonaConverter.toDto(zona);
    }

    public void eliminarZona(Long id) {
        Zona zona = zonaRepository.findById(id).orElseThrow(() -> new ZonaNoEncontradaException());
        zonaRepository.delete(zona);
    }
}
