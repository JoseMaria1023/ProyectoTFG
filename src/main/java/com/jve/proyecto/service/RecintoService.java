package com.jve.proyecto.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.jve.proyecto.configuration.ModelMapperConfig;
import com.jve.proyecto.dto.RecintoDTO;
import com.jve.proyecto.entity.Recinto;
import com.jve.proyecto.repository.RecintoRepository;
import org.modelmapper.ModelMapper;

@Service
public class RecintoService {

    private final RecintoRepository recintoRepository;
    private final ModelMapper modelMapper;

    public RecintoService(RecintoRepository recintoRepository, ModelMapperConfig modelMapperConfig) {
        this.recintoRepository = recintoRepository;
        this.modelMapper = modelMapperConfig.modelMapper();
    }

    public RecintoDTO crearRecinto(RecintoDTO recintoDTO) {
        Recinto recinto = modelMapper.map(recintoDTO, Recinto.class);
        Recinto recintoGuardado = recintoRepository.save(recinto);
        return modelMapper.map(recintoGuardado, RecintoDTO.class);
    }

    public RecintoDTO obtenerRecintoPorId(Long id) {
        Recinto recinto = recintoRepository.findById(id).orElseThrow(() -> 
                new RuntimeException("Recinto no encontrado con ID: " + id));
        return modelMapper.map(recinto, RecintoDTO.class);
    }

    public List<RecintoDTO> obtenerTodosLosRecintos() {
        return recintoRepository.findAll().stream()
                .map(recinto -> modelMapper.map(recinto, RecintoDTO.class))
                .collect(Collectors.toList());
    }

    public RecintoDTO actualizarRecinto(Long id, RecintoDTO recintoDTO) {
        Recinto recinto = recintoRepository.findById(id).orElseThrow(() -> 
                new RuntimeException("Recinto no encontrado con ID: " + id));
        modelMapper.map(recintoDTO, recinto);
        Recinto recintoActualizado = recintoRepository.save(recinto);
        return modelMapper.map(recintoActualizado, RecintoDTO.class);
    }

    public void eliminarRecinto(Long id) {
        Recinto recinto = recintoRepository.findById(id).orElseThrow(() -> 
                new RuntimeException("Recinto no encontrado con ID: " + id));
        recintoRepository.delete(recinto);
    }
}
