package com.jve.proyecto.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jve.proyecto.converter.AsientoConverter;
import com.jve.proyecto.converter.ConciertoConverter;
import com.jve.proyecto.converter.EntradaConverter;
import com.jve.proyecto.dto.AsientoDTO;
import com.jve.proyecto.dto.ConciertoDTO;
import com.jve.proyecto.dto.EntradaDTO;
import com.jve.proyecto.entity.Asiento;
import com.jve.proyecto.entity.Concierto;
import com.jve.proyecto.entity.Entrada;
import com.jve.proyecto.entity.Usuario;
import com.jve.proyecto.entity.Zona;
import com.jve.proyecto.exceptions.EntradaNoEncontradaException;
import com.jve.proyecto.exceptions.EntradasDisponiblesException;
import com.jve.proyecto.exceptions.PrecioEntradaException;
import com.jve.proyecto.entity.Entrada.EstadoEntrada;
import com.jve.proyecto.repository.AsientoRepository;
import com.jve.proyecto.repository.ConciertoRepository;
import com.jve.proyecto.repository.EntradaRepository;
import com.jve.proyecto.repository.UsuarioRepository;
import com.jve.proyecto.security.QRCodeGenerator;

@Service
public class EntradaService {

    private final EntradaRepository entradaRepository;
    private final AsientoRepository asientoRepository;
    private final ConciertoRepository conciertoRepository;
    private final UsuarioRepository usuarioRepository;
    private final EntradaConverter entradaConverter;
    private final AsientoConverter asientoConverter;
    private final ConciertoConverter conciertoConverter;
    private final ModelMapper modelMapper = new ModelMapper();

    public EntradaService(EntradaRepository entradaRepository,
                          AsientoRepository asientoRepository,
                          ConciertoRepository conciertoRepository,
                          UsuarioRepository usuarioRepository,
                          EntradaConverter entradaConverter,
                          AsientoConverter asientoConverter,
                          ConciertoConverter conciertoConverter
                          
                          ) {
        this.entradaRepository = entradaRepository;
        this.asientoRepository = asientoRepository;
        this.conciertoRepository = conciertoRepository;
        this.usuarioRepository = usuarioRepository;
        this.entradaConverter = entradaConverter;
        this.asientoConverter = asientoConverter;
        this.conciertoConverter = conciertoConverter;
    }

    @Transactional
    public EntradaDTO crearEntrada(EntradaDTO dto) {
        // 1) Validaciones básicas
        if (dto.getAsientoId() == null) {
            throw new RuntimeException("El ID del asiento es obligatorio.");
        }
        if (dto.getConciertoId() == null) {
            throw new RuntimeException("El ID del concierto es obligatorio.");
        }
        if (dto.getUsuarioId() == null) {
            throw new RuntimeException("El ID del usuario es obligatorio.");
        }
    
        // 2) Buscar Usuario, Asiento y Concierto
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + dto.getUsuarioId()));
        Asiento asiento = asientoRepository.findById(dto.getAsientoId())
            .orElseThrow(() -> new RuntimeException("Asiento no encontrado con ID: " + dto.getAsientoId()));
        Concierto concierto = conciertoRepository.findById(dto.getConciertoId())
            .orElseThrow(() -> new RuntimeException("Concierto no encontrado con ID: " + dto.getConciertoId()));
    
        // 3) Obtener el precioBase de la zona asociada al concierto
        Zona zona = concierto.getZona();
        if (zona == null) {
            throw new RuntimeException("El concierto no tiene zona asignada");
        }
        BigDecimal precioVentaBase = zona.getPrecioBase();
    
        // 4) Generar contenido del QR
        String qrContent = String.format(
            "Entrada para %s, Asiento %s, Usuario %s, Fecha %s",
            concierto.getNombre(),
            asiento.getNumeracion(),
            usuario.getNombre(),
            LocalDateTime.now()
        );
        String codigoQR = QRCodeGenerator.generateBase64QRCode(qrContent, 250, 250);
    
        // 5) Construir la entidad Entrada
        Entrada entrada = Entrada.builder()
            .codigoQR(codigoQR)
            .tipo(Entrada.TipoEntrada.NORMAL)          // siempre NORMAL al crear; puedes cambiar si dto trae VIP
            .precioVenta(precioVentaBase)             // tomado de la zona
            .precioReventa(dto.getPrecioReventa())    // si quieres permitir reventa inmediata
            .fechaCompra(LocalDateTime.now())         // fecha actual
            .estado(Entrada.EstadoEntrada.DISPONIBLE) // siempre DISPONIBLE al crear
            .asiento(asiento)
            .concierto(concierto)
            .usuario(usuario)
            .build();
    
        Entrada guardada = entradaRepository.save(entrada);
    
        // 6) Mapear a DTO e inyectar los objetos anidados
        EntradaDTO salida = entradaConverter.toDto(guardada);
        salida.setAsiento(asientoConverter.toDto(guardada.getAsiento()));
        salida.setConcierto(conciertoConverter.toDto(guardada.getConcierto()));
        return salida;
    }
    

    public EntradaDTO obtenerEntradaPorId(Long id) {
        Entrada e = entradaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Entrada no encontrada: " + id));
        EntradaDTO dto = entradaConverter.toDto(e);
        dto.setAsiento(asientoConverter.toDto(e.getAsiento()));
        dto.setConcierto(conciertoConverter.toDto(e.getConcierto()));
        return dto;
    }

    public List<EntradaDTO> obtenerEntradasPorUsuario(Long usuarioId) {
        return entradaRepository.findByUsuario_IdUsuario(usuarioId).stream()
            .map(e -> {
                EntradaDTO dto = entradaConverter.toDto(e);
                dto.setAsiento(asientoConverter.toDto(e.getAsiento()));
                dto.setConcierto(conciertoConverter.toDto(e.getConcierto()));
                return dto;
            })
            .collect(Collectors.toList());
    }

    public List<EntradaDTO> obtenerTodasLasEntradas() {
        return entradaRepository.findAll().stream()
            .map(e -> {
                EntradaDTO dto = entradaConverter.toDto(e);
                dto.setAsiento(asientoConverter.toDto(e.getAsiento()));
                dto.setConcierto(conciertoConverter.toDto(e.getConcierto()));
                return dto;
            })
            .collect(Collectors.toList());
    }

    public EntradaDTO actualizarEntrada(Long id, EntradaDTO dto) {
        Entrada existente = entradaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Entrada no encontrada: " + id));

        existente.setEstado( Entrada.EstadoEntrada.valueOf(dto.getEstado().toUpperCase()) );
        existente.setPrecioReventa(dto.getPrecioReventa());
        existente.setPrecioVenta(dto.getPrecioVenta());
        existente.setTipo( Entrada.TipoEntrada.valueOf(dto.getTipo().toUpperCase()) );
        Entrada actualizada = entradaRepository.save(existente);
        EntradaDTO salida = entradaConverter.toDto(actualizada);
        salida.setAsiento(asientoConverter.toDto(actualizada.getAsiento()));
        salida.setConcierto(conciertoConverter.toDto(actualizada.getConcierto()));
        return salida;
    }

    public void eliminarEntrada(Long id) {
        entradaRepository.delete(
            entradaRepository.findById(id)
                .orElseThrow(() -> new EntradaNoEncontradaException())
        );
    }

    public EntradaDTO revenderEntrada(Long idEntrada, BigDecimal precioReventa) {
        Entrada e = entradaRepository.findById(idEntrada)
            .orElseThrow(() -> new EntradaNoEncontradaException());

        if (e.getEstado() != Entrada.EstadoEntrada.DISPONIBLE) {
            throw new EntradasDisponiblesException();
        }

        BigDecimal max = e.getPrecioVenta()
            .multiply(BigDecimal.valueOf(1.10))
            .setScale(2, RoundingMode.HALF_UP);

        BigDecimal pr = precioReventa.setScale(2, RoundingMode.HALF_UP);
        if (pr.compareTo(max) > 0) {
            throw new PrecioEntradaException();
        }

        e.setPrecioReventa(pr);
        e.setEstado(Entrada.EstadoEntrada.VENDIDA);

        Entrada guardada = entradaRepository.save(e);
        EntradaDTO dto = entradaConverter.toDto(guardada);
        dto.setAsiento(asientoConverter.toDto(guardada.getAsiento()));
        dto.setConcierto(conciertoConverter.toDto(guardada.getConcierto()));
        return dto;
    }
}