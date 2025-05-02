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
import com.jve.proyecto.repository.EntradaRepository;
import com.jve.proyecto.repository.PagoRepository;
import com.jve.proyecto.repository.UsuarioRepository;
import com.jve.proyecto.converter.PagoConverter;

@Service
public class PagoService {

    private final PagoRepository pagoRepository;
    private final EntradaRepository entradaRepository;
    private final UsuarioRepository usuarioRepository;
    private final PagoConverter pagoConverter; // Inyectamos el PagoConverter

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
            throw new RuntimeException("El método de pago no puede ser nulo o vacío.");
        }

        // Convertir el método de pago desde el String al Enum
        MetodoPago metodoPago;
        try {
            metodoPago = MetodoPago.valueOf(pagoDTO.getMetodoPago().trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Método de pago no válido: " + pagoDTO.getMetodoPago());
        }

        // Recuperar la entrada usando el ID del DTO
        Entrada entrada = entradaRepository.findById(pagoDTO.getEntradaId()).orElseThrow(() -> 
            new RuntimeException("Entrada no encontrada con ID: " + pagoDTO.getEntradaId())
        );

        // Recuperar el usuario usando el ID del DTO
        Usuario usuario = usuarioRepository.findById(pagoDTO.getUsuarioId()).orElseThrow(() -> 
            new RuntimeException("Usuario no encontrado con ID: " + pagoDTO.getUsuarioId())
        );

        // Mapear el DTO a la entidad Pago y asignar la entrada, el usuario y la fecha actual
        Pago pago = pagoConverter.toEntity(pagoDTO); // Usamos el converter para mapear de DTO a entidad
        pago.setMetodoPago(metodoPago);
        pago.setEntrada(entrada);
        pago.setUsuario(usuario);
        pago.setFechaPago(LocalDateTime.now());

        Pago pagoGuardado = pagoRepository.save(pago);
        return pagoConverter.toDto(pagoGuardado); // Usamos el converter para mapear de entidad a DTO
    }

    public PagoDTO obtenerPagoPorId(Long id) {
        Pago pago = pagoRepository.findById(id).orElseThrow(() -> 
                new RuntimeException("Pago no encontrado con ID: " + id));
        return pagoConverter.toDto(pago); // Usamos el converter
    }

    public List<PagoDTO> obtenerTodosLosPagos() {
        return pagoRepository.findAll().stream()
                .map(pago -> pagoConverter.toDto(pago)) // Usamos el converter
                .collect(Collectors.toList());
    }

    public PagoDTO actualizarPago(Long id, PagoDTO pagoDTO) {
        Pago pago = pagoRepository.findById(id).orElseThrow(() -> 
                new RuntimeException("Pago no encontrado con ID: " + id));
        
        pagoConverter.toEntity(pagoDTO); // Convertimos el DTO a la entidad para actualizarla
        Pago pagoActualizado = pagoRepository.save(pago);
        return pagoConverter.toDto(pagoActualizado); // Usamos el converter para devolver el DTO actualizado
    }

    public void eliminarPago(Long id) {
        Pago pago = pagoRepository.findById(id).orElseThrow(() -> 
                new RuntimeException("Pago no encontrado con ID: " + id));
        pagoRepository.delete(pago);
    }
}
