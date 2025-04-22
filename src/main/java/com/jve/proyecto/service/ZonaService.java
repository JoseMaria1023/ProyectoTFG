package com.jve.proyecto.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.jve.proyecto.configuration.ModelMapperConfig;
import com.jve.proyecto.dto.ZonaDTO;
import com.jve.proyecto.entity.Recinto;
import com.jve.proyecto.entity.Zona;
import com.jve.proyecto.repository.RecintoRepository;
import com.jve.proyecto.repository.ZonaRepository;
import org.modelmapper.ModelMapper;

@Service
public class ZonaService {

    private final ZonaRepository zonaRepository;
    private final RecintoRepository recintoRepository;
    private final ModelMapper modelMapper;

    public ZonaService(ZonaRepository zonaRepository, RecintoRepository recintoRepository, 
                       ModelMapperConfig modelMapperConfig) {
        this.zonaRepository = zonaRepository;
        this.recintoRepository = recintoRepository;
        this.modelMapper = modelMapperConfig.modelMapper();
    }

    public ZonaDTO crearZona(ZonaDTO zonaDTO) {
        if (zonaDTO.getRecintoId() == null) {
            throw new RuntimeException("El ID del recinto es obligatorio.");
        }

        Recinto recinto = recintoRepository.findById(zonaDTO.getRecintoId())
                .orElseThrow(() -> new RuntimeException("Recinto no encontrado con ID: " + zonaDTO.getRecintoId()));

        Zona zona = Zona.builder()
                .nombre(zonaDTO.getNombre())
                .recinto(recinto)  
                .precioBase(zonaDTO.getPrecioBase())
                .precioVIP(zonaDTO.getPrecioVIP())
                .build();

        Zona zonaGuardada = zonaRepository.save(zona);

        return modelMapper.map(zonaGuardada, ZonaDTO.class);
    }

    public ZonaDTO obtenerZonaPorId(Long id) {
        Zona zona = zonaRepository.findById(id).orElseThrow(() -> 
                new RuntimeException("Zona no encontrada con ID: " + id));
        
        return modelMapper.map(zona, ZonaDTO.class);
    }

    public List<ZonaDTO> obtenerTodasLasZonas() {
        return zonaRepository.findAll().stream()
                .map(zona -> modelMapper.map(zona, ZonaDTO.class))
                .collect(Collectors.toList());
    }

    public ZonaDTO actualizarZona(Long id, ZonaDTO zonaDTO) {
        Zona zona = zonaRepository.findById(id).orElseThrow(() -> 
                new RuntimeException("Zona no encontrada con ID: " + id));

        if (zonaDTO.getRecintoId() != null) {
            Recinto recinto = recintoRepository.findById(zonaDTO.getRecintoId())
                    .orElseThrow(() -> new RuntimeException("Recinto no encontrado con ID: " + zonaDTO.getRecintoId()));
            zona.setRecinto(recinto);
        }

        zona.setNombre(zonaDTO.getNombre());
        zona.setPrecioBase(zonaDTO.getPrecioBase());
        zona.setPrecioVIP(zonaDTO.getPrecioVIP());

        Zona zonaActualizada = zonaRepository.save(zona);

        return modelMapper.map(zonaActualizada, ZonaDTO.class);
    }

    public void eliminarZona(Long id) {
        Zona zona = zonaRepository.findById(id).orElseThrow(() -> 
                new RuntimeException("Zona no encontrada con ID: " + id));
        zonaRepository.delete(zona);
    }
}
