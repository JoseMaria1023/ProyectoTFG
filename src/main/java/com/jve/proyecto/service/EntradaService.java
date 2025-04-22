package com.jve.proyecto.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.jve.proyecto.dto.AsientoDTO;
import com.jve.proyecto.dto.ConciertoDTO;
import com.jve.proyecto.dto.EntradaDTO;
import com.jve.proyecto.entity.Asiento;
import com.jve.proyecto.entity.Concierto;
import com.jve.proyecto.entity.Entrada;
import com.jve.proyecto.entity.Usuario;
import com.jve.proyecto.repository.AsientoRepository;
import com.jve.proyecto.repository.ConciertoRepository;
import com.jve.proyecto.repository.EntradaRepository;
import com.jve.proyecto.repository.UsuarioRepository;

@Service
public class EntradaService {

    private final EntradaRepository entradaRepository;
    private final AsientoRepository asientoRepository;
    private final ConciertoRepository conciertoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ModelMapper modelMapper;

    public EntradaService(EntradaRepository entradaRepository, AsientoRepository asientoRepository,
                          ConciertoRepository conciertoRepository, UsuarioRepository usuarioRepository,
                          ModelMapper modelMapper) {
        this.entradaRepository = entradaRepository;
        this.asientoRepository = asientoRepository;
        this.conciertoRepository = conciertoRepository;
        this.usuarioRepository = usuarioRepository;
        this.modelMapper = modelMapper;
    }

    public EntradaDTO crearEntrada(EntradaDTO entradaDTO) {
        if (entradaDTO.getAsientoId() == null) {
            throw new RuntimeException("El ID del asiento es obligatorio.");
        }

        if (entradaDTO.getConciertoId() == null) {
            throw new RuntimeException("El ID del concierto es obligatorio.");
        }

        if (entradaDTO.getUsuarioId() == null) {
            throw new RuntimeException("El ID del usuario es obligatorio.");
        }

        Asiento asiento = asientoRepository.findById(entradaDTO.getAsientoId())
                .orElseThrow(() -> new RuntimeException("Asiento no encontrado con ID: " + entradaDTO.getAsientoId()));

        Concierto concierto = conciertoRepository.findById(entradaDTO.getConciertoId())
                .orElseThrow(() -> new RuntimeException("Concierto no encontrado con ID: " + entradaDTO.getConciertoId()));

        Usuario usuario = usuarioRepository.findById(entradaDTO.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + entradaDTO.getUsuarioId()));

        LocalDateTime fechaCompra = (entradaDTO.getFechaCompra() == null)
                ? LocalDateTime.now()
                : entradaDTO.getFechaCompra();

        Entrada entrada = Entrada.builder()
                .codigoQR(entradaDTO.getCodigoQR())
                .tipo(Entrada.TipoEntrada.valueOf(entradaDTO.getTipo().toUpperCase()))
                .precioVenta(entradaDTO.getPrecioVenta())
                .precioReventa(entradaDTO.getPrecioReventa())
                .fechaCompra(fechaCompra)
                .estado(Entrada.EstadoEntrada.valueOf(entradaDTO.getEstado().toUpperCase()))
                .asiento(asiento)
                .concierto(concierto)
                .usuario(usuario)
                .build();

        Entrada entradaGuardada = entradaRepository.save(entrada);

        EntradaDTO dto = modelMapper.map(entradaGuardada, EntradaDTO.class);
        dto.setAsiento(modelMapper.map(entradaGuardada.getAsiento(), AsientoDTO.class));
        dto.setConcierto(modelMapper.map(entradaGuardada.getConcierto(), ConciertoDTO.class));
        return dto;
    }

    public EntradaDTO obtenerEntradaPorId(Long id) {
        Entrada entrada = entradaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entrada no encontrada con ID: " + id));

        EntradaDTO dto = modelMapper.map(entrada, EntradaDTO.class);
        dto.setAsiento(modelMapper.map(entrada.getAsiento(), AsientoDTO.class));
        dto.setConcierto(modelMapper.map(entrada.getConcierto(), ConciertoDTO.class));
        return dto;
    }

    public List<EntradaDTO> obtenerEntradasPorUsuario(Long id) {
        return entradaRepository.findByUsuario_IdUsuario(id)
                .stream()
                .map(entrada -> {
                    EntradaDTO dto = modelMapper.map(entrada, EntradaDTO.class);
                    dto.setAsiento(modelMapper.map(entrada.getAsiento(), AsientoDTO.class));
                    dto.setConcierto(modelMapper.map(entrada.getConcierto(), ConciertoDTO.class));
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public List<EntradaDTO> obtenerTodasLasEntradas() {
        return entradaRepository.findAll().stream()
                .map(entrada -> {
                    EntradaDTO dto = modelMapper.map(entrada, EntradaDTO.class);
                    dto.setAsiento(modelMapper.map(entrada.getAsiento(), AsientoDTO.class));
                    dto.setConcierto(modelMapper.map(entrada.getConcierto(), ConciertoDTO.class));
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public EntradaDTO actualizarEntrada(Long id, EntradaDTO entradaDTO) {
        Entrada entrada = entradaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entrada no encontrada con ID: " + id));

        modelMapper.map(entradaDTO, entrada);
        Entrada entradaActualizada = entradaRepository.save(entrada);

        EntradaDTO dto = modelMapper.map(entradaActualizada, EntradaDTO.class);
        dto.setAsiento(modelMapper.map(entradaActualizada.getAsiento(), AsientoDTO.class));
        dto.setConcierto(modelMapper.map(entradaActualizada.getConcierto(), ConciertoDTO.class));
        return dto;
    }

    public void eliminarEntrada(Long id) {
        Entrada entrada = entradaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entrada no encontrada con ID: " + id));
        entradaRepository.delete(entrada);
    }
}
