package com.jve.proyecto.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.jve.proyecto.dto.PagoDTO;
import com.jve.proyecto.entity.Entrada;
import com.jve.proyecto.entity.Pago;
import com.jve.proyecto.entity.Pago.MetodoPago;
import com.jve.proyecto.entity.Usuario;
import com.jve.proyecto.exceptions.EntradaNoEncontradaException;
import com.jve.proyecto.exceptions.MetodoDePagoValidoException;
import com.jve.proyecto.exceptions.MetodosDePagoException;
import com.jve.proyecto.exceptions.PagoNoEncontradoException;
import com.jve.proyecto.exceptions.UsuarioNoEncontradoException;
import com.jve.proyecto.repository.EntradaRepository;
import com.jve.proyecto.repository.PagoRepository;
import com.jve.proyecto.repository.UsuarioRepository;
import com.jve.proyecto.converter.PagoConverter;

@Service
public class PagoService {

    private final PagoRepository pagoRepository;
    private final EntradaRepository entradaRepository;
    private final UsuarioRepository usuarioRepository;
    private final PagoConverter pagoConverter; 

    public PagoService(PagoRepository pagoRepository, 
                       EntradaRepository entradaRepository,
                       UsuarioRepository usuarioRepository, PagoConverter pagoConverter) {
        this.pagoRepository = pagoRepository;
        this.entradaRepository = entradaRepository;
        this.usuarioRepository = usuarioRepository;
        this.pagoConverter = pagoConverter;
    }

 public PagoDTO crearPago(PagoDTO pagoDTO) {
    if (pagoDTO.getMetodoPago() == null || pagoDTO.getMetodoPago().trim().isEmpty()) {
        throw new MetodosDePagoException();
    }

    MetodoPago metodoPago;
    try {
        metodoPago = MetodoPago.valueOf(pagoDTO.getMetodoPago().trim().toUpperCase());
    } catch (IllegalArgumentException e) {
        throw new MetodoDePagoValidoException();
    }

    Entrada entrada = entradaRepository.findById(pagoDTO.getEntradaId())
        .orElseThrow(EntradaNoEncontradaException::new);

    Usuario usuario = usuarioRepository.findById(pagoDTO.getUsuarioId())
        .orElseThrow(UsuarioNoEncontradoException::new);

    Pago pago = pagoConverter.toEntity(pagoDTO);
    pago.setMetodoPago(metodoPago);
    pago.setEntrada(entrada);
    pago.setUsuario(usuario);
    pago.setFechaPago(LocalDateTime.now());

    Pago pagoGuardado = pagoRepository.save(pago);
    return pagoConverter.toDto(pagoGuardado);
}


    public PagoDTO TraerPagoPorId(Long id) {
        Pago pago = pagoRepository.findById(id).orElseThrow(() -> new PagoNoEncontradoException());
        return pagoConverter.toDto(pago); 
    }

    public List<PagoDTO> TraerTodosLosPagos() {
        return pagoRepository.findAll().stream().map(pago -> pagoConverter.toDto(pago)) 
        .collect(Collectors.toList());
    }

    public PagoDTO actualizarPago(Long id, PagoDTO pagoDTO) {
        Pago pago = pagoRepository.findById(id).orElseThrow(() -> new PagoNoEncontradoException());
        
        pagoConverter.toEntity(pagoDTO); 
        Pago pagoActualizado = pagoRepository.save(pago);
        return pagoConverter.toDto(pagoActualizado); 
    }

    public void eliminarPago(Long id) {
        Pago pago = pagoRepository.findById(id).orElseThrow(() -> new PagoNoEncontradoException());
        pagoRepository.delete(pago);
    }
}
