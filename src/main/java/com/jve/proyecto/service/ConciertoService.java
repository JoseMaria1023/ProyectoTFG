package com.jve.proyecto.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.jve.proyecto.dto.ConciertoDTO;
import com.jve.proyecto.entity.Concierto;
import com.jve.proyecto.entity.Concierto.EstadoConcierto;
import com.jve.proyecto.entity.Gira;
import com.jve.proyecto.entity.Zona;
import com.jve.proyecto.repository.ConciertoRepository;
import com.jve.proyecto.repository.GiraRepository;
import com.jve.proyecto.repository.ZonaRepository;

@Service
public class ConciertoService {

    private final ConciertoRepository conciertoRepository;
    private final ZonaRepository zonaRepository;
    private final GiraRepository giraRepository;
    private final ModelMapper modelMapper;

    public ConciertoService(ConciertoRepository conciertoRepository, 
                            ZonaRepository zonaRepository, 
                            GiraRepository giraRepository,
                            ModelMapper modelMapper) {
        this.conciertoRepository = conciertoRepository;
        this.zonaRepository = zonaRepository;
        this.giraRepository = giraRepository;
        this.modelMapper = modelMapper;
    }

    public ConciertoDTO crearConcierto(ConciertoDTO conciertoDTO) {
        Concierto concierto = Concierto.builder()
            .nombre(conciertoDTO.getNombre())
            .fecha(conciertoDTO.getFecha())
            .zona(zonaRepository.findById(conciertoDTO.getZonaId())
                  .orElseThrow(() -> new RuntimeException("Zona no encontrada")))
            .gira(giraRepository.findById(conciertoDTO.getGiraId())
                  .orElseThrow(() -> new RuntimeException("Gira no encontrada")))
            .estado(EstadoConcierto.valueOf(conciertoDTO.getEstado().toUpperCase()))
            .build();

        Concierto conciertoGuardado = conciertoRepository.save(concierto);
        return modelMapper.map(conciertoGuardado, ConciertoDTO.class);
    }

    public ConciertoDTO obtenerConciertoPorId(Long id) {
        Concierto concierto = conciertoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Concierto no encontrado con ID: " + id));
        return modelMapper.map(concierto, ConciertoDTO.class);
    }

    public List<ConciertoDTO> obtenerTodosLosConciertos() {
        return conciertoRepository.findAll().stream()
                .map(concierto -> modelMapper.map(concierto, ConciertoDTO.class))
                .collect(Collectors.toList());
    }

    public List<ConciertoDTO> obtenerConciertosPorArtista(Long artistaId) {
        // Filtramos todos los conciertos cuya gira tenga asignado un artista con el id indicado
        List<Concierto> conciertos = conciertoRepository.findAll().stream()
            .filter(concierto -> concierto.getGira() != null 
                    && concierto.getGira().getArtista() != null 
                    && concierto.getGira().getArtista().getIdArtista().equals(artistaId))
            .collect(Collectors.toList());
    
        return conciertos.stream()
                .map(concierto -> modelMapper.map(concierto, ConciertoDTO.class))
                .collect(Collectors.toList());
    }

    public ConciertoDTO actualizarConcierto(Long id, ConciertoDTO conciertoDTO) {
        Concierto concierto = conciertoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Concierto no encontrado con ID: " + id));
    
        Zona zona = zonaRepository.findById(conciertoDTO.getZonaId())
                .orElseThrow(() -> new RuntimeException("Zona no encontrada con ID: " + conciertoDTO.getZonaId()));
    
        Gira gira = giraRepository.findById(conciertoDTO.getGiraId())
                .orElseThrow(() -> new RuntimeException("Gira no encontrada con ID: " + conciertoDTO.getGiraId()));
    
        concierto.setNombre(conciertoDTO.getNombre());
        concierto.setFecha(conciertoDTO.getFecha());
        concierto.setZona(zona);
        concierto.setGira(gira);
        concierto.setEstado(EstadoConcierto.valueOf(conciertoDTO.getEstado().toUpperCase()));
    
        Concierto conciertoActualizado = conciertoRepository.save(concierto);
        return modelMapper.map(conciertoActualizado, ConciertoDTO.class);
    }

    public void eliminarConcierto(Long id) {
        Concierto concierto = conciertoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Concierto no encontrado con ID: " + id));
        conciertoRepository.delete(concierto);
    }
}
