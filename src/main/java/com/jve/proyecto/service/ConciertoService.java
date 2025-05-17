package com.jve.proyecto.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.jve.proyecto.dto.ConciertoDTO;
import com.jve.proyecto.entity.Concierto;
import com.jve.proyecto.entity.Gira;
import com.jve.proyecto.entity.Zona;
import com.jve.proyecto.exceptions.ConciertoNoEncontradoException;
import com.jve.proyecto.exceptions.GiraNoEncontradaException;
import com.jve.proyecto.exceptions.ZonaNoEncontradaException;
import com.jve.proyecto.repository.ConciertoRepository;
import com.jve.proyecto.repository.GiraRepository;
import com.jve.proyecto.repository.ZonaRepository;
import com.jve.proyecto.converter.ConciertoConverter;

@Service
public class ConciertoService {

    private final ConciertoRepository conciertoRepository;
    private final ZonaRepository zonaRepository;
    private final GiraRepository giraRepository;
    private final ConciertoConverter conciertoConverter;

    public ConciertoService(ConciertoRepository conciertoRepository,
                            ZonaRepository zonaRepository,
                            GiraRepository giraRepository,
                            ConciertoConverter conciertoConverter) {
        this.conciertoRepository = conciertoRepository;
        this.zonaRepository = zonaRepository;
        this.giraRepository = giraRepository;
        this.conciertoConverter = conciertoConverter;
    }

    public ConciertoDTO crearConcierto(ConciertoDTO conciertoDTO) {
        Concierto concierto = Concierto.builder()
            .nombre(conciertoDTO.getNombre())
            .fecha(conciertoDTO.getFecha())
            .zona(zonaRepository.findById(conciertoDTO.getZonaId())
                .orElseThrow(() -> new ZonaNoEncontradaException()))
            .gira(giraRepository.findById(conciertoDTO.getGiraId())
                .orElseThrow(() -> new GiraNoEncontradaException()))
            .estado(Enum.valueOf(Concierto.EstadoConcierto.class, conciertoDTO.getEstado().toUpperCase()))
            .build();

        Concierto guardado = conciertoRepository.save(concierto);
        return conciertoConverter.toDto(guardado);
    }

    public ConciertoDTO obtenerConciertoPorId(Long id) {
        Concierto c = conciertoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Concierto no encontrado con ID: " + id));
        return conciertoConverter.toDto(c);
    }

    public List<ConciertoDTO> obtenerTodosLosConciertos() {
        return conciertoRepository.findAll().stream()
            .map(conciertoConverter::toDto)
            .collect(Collectors.toList());
    }

    public List<ConciertoDTO> obtenerConciertosEntreFechas(LocalDateTime from, LocalDateTime to) {
        return conciertoRepository.findByFechaBetween(from, to).stream()
            .map(conciertoConverter::toDto)
            .collect(Collectors.toList());
    }

    public List<ConciertoDTO> filtrarConciertos(Long artistaId, LocalDateTime fechaDesde,
                                                LocalDateTime fechaHasta, String estado) {
        return conciertoRepository.findAll().stream()
            .filter(con -> {
                boolean ok = true;
                if (artistaId != null) {
                    ok &= con.getGira() != null
                       && con.getGira().getArtista() != null
                       && con.getGira().getArtista().getIdArtista().equals(artistaId);
                }
                if (fechaDesde != null && fechaHasta != null) {
                    ok &= !con.getFecha().isBefore(fechaDesde)
                       && !con.getFecha().isAfter(fechaHasta);
                }
                if (estado != null && !estado.isEmpty()) {
                    ok &= con.getEstado().name().equalsIgnoreCase(estado);
                }
                return ok;
            })
            .map(conciertoConverter::toDto)
            .collect(Collectors.toList());
    }

    public List<ConciertoDTO> obtenerConciertosPorArtista(Long artistaId) {
        return conciertoRepository.findAll().stream()
            .filter(con -> con.getGira()!=null 
                        && con.getGira().getArtista()!=null 
                        && con.getGira().getArtista().getIdArtista().equals(artistaId))
            .map(conciertoConverter::toDto)
            .collect(Collectors.toList());
    }

    public ConciertoDTO actualizarConcierto(Long id, ConciertoDTO dto) {
    Concierto con = conciertoRepository.findById(id)
        .orElseThrow(() -> new ConciertoNoEncontradoException());

    Zona zona = zonaRepository.findById(dto.getZonaId())
        .orElseThrow(() -> new ZonaNoEncontradaException());

    Gira gira = giraRepository.findById(dto.getGiraId())
        .orElseThrow(() -> new GiraNoEncontradaException());

    con.setNombre(dto.getNombre());
    con.setFecha(dto.getFecha());
    con.setZona(zona);
    con.setGira(gira);  
    con.setEstado(Enum.valueOf(Concierto.EstadoConcierto.class, dto.getEstado().toUpperCase()));

    Concierto updated = conciertoRepository.save(con);
    return conciertoConverter.toDto(updated);
}

    public void eliminarConcierto(Long id) {
        Concierto c = conciertoRepository.findById(id)
            .orElseThrow(() -> new ConciertoNoEncontradoException());
        conciertoRepository.delete(c);
    }
}
