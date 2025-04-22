package com.jve.proyecto.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jve.proyecto.configuration.ModelMapperConfig;
import com.jve.proyecto.dto.TransferenciaDTO;
import com.jve.proyecto.entity.Transferencia;
import com.jve.proyecto.entity.Entrada;
import com.jve.proyecto.entity.Usuario;
import com.jve.proyecto.repository.TransferenciaRepository;
import com.jve.proyecto.repository.EntradaRepository;
import com.jve.proyecto.repository.UsuarioRepository;
import org.modelmapper.ModelMapper;

@Service
public class TransferenciaService {

    private final TransferenciaRepository transferenciaRepository;
    private final EntradaRepository entradaRepository;
    private final UsuarioRepository usuarioRepository;
    private final ModelMapper modelMapper;

    public TransferenciaService(TransferenciaRepository transferenciaRepository, 
                                 EntradaRepository entradaRepository, 
                                 UsuarioRepository usuarioRepository,
                                 ModelMapperConfig modelMapperConfig) {
        this.transferenciaRepository = transferenciaRepository;
        this.entradaRepository = entradaRepository;
        this.usuarioRepository = usuarioRepository;
        this.modelMapper = modelMapperConfig.modelMapper();
    }

    @Transactional
    public TransferenciaDTO transferirEntrada(Long idEntrada, Long usuarioOrigenId, String telefonoDestino) {
        // Buscar la entrada
        Entrada entrada = entradaRepository.findById(idEntrada)
                .orElseThrow(() -> new RuntimeException("Entrada no encontrada con ID: " + idEntrada));

        // Verificar que la entrada pertenece al usuario que la quiere transferir
        if (!entrada.getUsuario().getIdUsuario().equals(usuarioOrigenId)) {
            throw new RuntimeException("La entrada no pertenece al usuario que intenta transferirla.");
        }

        // Buscar al usuario destino por teléfono
        Usuario usuarioDestino = usuarioRepository.findByTelefono(telefonoDestino)
                .orElseThrow(() -> new RuntimeException("Usuario destino no encontrado con teléfono: " + telefonoDestino));

        // Obtener el usuario origen (cargado completo desde DB)
        Usuario usuarioOrigen = usuarioRepository.findById(usuarioOrigenId)
                .orElseThrow(() -> new RuntimeException("Usuario origen no encontrado con ID: " + usuarioOrigenId));

        // Crear y guardar la transferencia
        Transferencia transferencia = new Transferencia();
        transferencia.setEntrada(entrada);
        transferencia.setUsuarioOrigen(usuarioOrigen);
        transferencia.setUsuarioDestino(usuarioDestino);
        transferencia.setFechaTransferencia(LocalDateTime.now());
        transferencia.setEstado(Transferencia.EstadoTransferencia.PENDIENTE);
        transferencia.setComentario("Transferencia realizada correctamente");

        Transferencia transferenciaGuardada = transferenciaRepository.save(transferencia);

        // Actualizar el usuario asignado en la entrada
        entrada.setUsuario(usuarioDestino);
        entradaRepository.save(entrada);

        return modelMapper.map(transferenciaGuardada, TransferenciaDTO.class);
    }

    public TransferenciaDTO crearTransferencia(TransferenciaDTO transferenciaDTO) {
        Transferencia transferencia = modelMapper.map(transferenciaDTO, Transferencia.class);
        Transferencia transferenciaGuardada = transferenciaRepository.save(transferencia);
        return modelMapper.map(transferenciaGuardada, TransferenciaDTO.class);
    }

    public TransferenciaDTO obtenerTransferenciaPorId(Long id) {
        Transferencia transferencia = transferenciaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Transferencia no encontrada con ID: " + id));
        return modelMapper.map(transferencia, TransferenciaDTO.class);
    }

    public List<TransferenciaDTO> obtenerTodasLasTransferencias() {
        return transferenciaRepository.findAll().stream()
                .map(transferencia -> modelMapper.map(transferencia, TransferenciaDTO.class))
                .collect(Collectors.toList());
    }

    public TransferenciaDTO actualizarTransferencia(Long id, TransferenciaDTO transferenciaDTO) {
        Transferencia transferencia = transferenciaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Transferencia no encontrada con ID: " + id));
        modelMapper.map(transferenciaDTO, transferencia);
        if (transferenciaDTO.getEntradaId() != null) {
            Entrada entrada = new Entrada();
            entrada.setIdEntrada(transferenciaDTO.getEntradaId());
            transferencia.setEntrada(entrada);
        }
        if (transferenciaDTO.getUsuarioOrigenId() != null) {
            Usuario usuarioOrigen = new Usuario();
            usuarioOrigen.setIdUsuario(transferenciaDTO.getUsuarioOrigenId());
            transferencia.setUsuarioOrigen(usuarioOrigen);
        }
        if (transferenciaDTO.getUsuarioDestinoId() != null) {
            Usuario usuarioDestino = new Usuario();
            usuarioDestino.setIdUsuario(transferenciaDTO.getUsuarioDestinoId());
            transferencia.setUsuarioDestino(usuarioDestino);
        }
        Transferencia transferenciaActualizada = transferenciaRepository.save(transferencia);
        return modelMapper.map(transferenciaActualizada, TransferenciaDTO.class);
    }

    public void eliminarTransferencia(Long id) {
        Transferencia transferencia = transferenciaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transferencia no encontrada con ID: " + id));
        transferenciaRepository.delete(transferencia);
    }
}
