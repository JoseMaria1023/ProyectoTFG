package com.jve.proyecto.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jve.proyecto.dto.TransferenciaDTO;
import com.jve.proyecto.entity.Transferencia;
import com.jve.proyecto.entity.Entrada;
import com.jve.proyecto.entity.Usuario;
import com.jve.proyecto.exceptions.EntradaEnPetenenciaException;
import com.jve.proyecto.exceptions.EntradaNoEncontradaException;
import com.jve.proyecto.exceptions.TransferenciaNoEncontradaException;
import com.jve.proyecto.exceptions.UsuarioNoEncontradoException;
import com.jve.proyecto.repository.TransferenciaRepository;
import com.jve.proyecto.repository.EntradaRepository;
import com.jve.proyecto.repository.UsuarioRepository;
import com.jve.proyecto.converter.TransferenciaConverter;

@Service
public class TransferenciaService {

    private final TransferenciaRepository transferenciaRepository;
    private final EntradaRepository entradaRepository;
    private final UsuarioRepository usuarioRepository;
    private final TransferenciaConverter transferenciaConverter; // Inyectamos el TransferenciaConverter

    public TransferenciaService(TransferenciaRepository transferenciaRepository, 
                                 EntradaRepository entradaRepository, 
                                 UsuarioRepository usuarioRepository,
                                 TransferenciaConverter transferenciaConverter) {
        this.transferenciaRepository = transferenciaRepository;
        this.entradaRepository = entradaRepository;
        this.usuarioRepository = usuarioRepository;
        this.transferenciaConverter = transferenciaConverter;
    }

    @Transactional
    public TransferenciaDTO transferirEntrada(Long idEntrada, Long usuarioOrigenId, String telefonoDestino) {
        // Buscar la entrada
        Entrada entrada = entradaRepository.findById(idEntrada)
                .orElseThrow(() -> new EntradaNoEncontradaException());

        // Verificar que la entrada pertenece al usuario que la quiere transferir
        if (!entrada.getUsuario().getIdUsuario().equals(usuarioOrigenId)) {
            throw new EntradaEnPetenenciaException();
        }

        // Buscar al usuario destino por teléfono
        Usuario usuarioDestino = usuarioRepository.findByTelefono(telefonoDestino)
                .orElseThrow(() -> new UsuarioNoEncontradoException());

        // Obtener el usuario origen (cargado completo desde DB)
        Usuario usuarioOrigen = usuarioRepository.findById(usuarioOrigenId)
                .orElseThrow(() -> new UsuarioNoEncontradoException());

        // Crear y guardar la transferencia
        Transferencia transferencia = new Transferencia();
        transferencia.setEntrada(entrada);
        transferencia.setUsuarioOrigen(usuarioOrigen);
        transferencia.setUsuarioDestino(usuarioDestino);
        transferencia.setFechaTransferencia(LocalDateTime.now());
        transferencia.setEstado(Transferencia.EstadoTransferencia.COMPLETADA);
        transferencia.setComentario("Transferencia realizada correctamente");

        Transferencia transferenciaGuardada = transferenciaRepository.save(transferencia);

        // Actualizar el usuario asignado en la entrada
        entrada.setUsuario(usuarioDestino);
        entradaRepository.save(entrada);

        return transferenciaConverter.toDto(transferenciaGuardada); // Usamos el converter
    }

    public TransferenciaDTO crearTransferencia(TransferenciaDTO transferenciaDTO) {
        Transferencia transferencia = transferenciaConverter.toEntity(transferenciaDTO); // Usamos el converter
        Transferencia transferenciaGuardada = transferenciaRepository.save(transferencia);
        return transferenciaConverter.toDto(transferenciaGuardada); // Usamos el converter
    }

    public TransferenciaDTO obtenerTransferenciaPorId(Long id) {
        Transferencia transferencia = transferenciaRepository.findById(id)
            .orElseThrow(() -> new TransferenciaNoEncontradaException());
        return transferenciaConverter.toDto(transferencia); // Usamos el converter
    }

    public List<TransferenciaDTO> obtenerTodasLasTransferencias() {
        return transferenciaRepository.findAll().stream()
                .map(transferencia -> transferenciaConverter.toDto(transferencia)) // Usamos el converter
                .collect(Collectors.toList());
    }

    public TransferenciaDTO actualizarTransferencia(Long id, TransferenciaDTO transferenciaDTO) {
        Transferencia transferencia = transferenciaRepository.findById(id)
            .orElseThrow(() -> new TransferenciaNoEncontradaException());
        transferenciaConverter.toEntity(transferenciaDTO); // Usamos el converter para mapear de DTO a entidad
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
        return transferenciaConverter.toDto(transferenciaActualizada); // Usamos el converter
    }

    public void eliminarTransferencia(Long id) {
        Transferencia transferencia = transferenciaRepository.findById(id)
                .orElseThrow(() -> new TransferenciaNoEncontradaException());
        transferenciaRepository.delete(transferencia);
    }
}
