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
    private final TransferenciaConverter transferenciaConverter;

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
    Entrada entrada = entradaRepository.findById(idEntrada)
        .orElseThrow(EntradaNoEncontradaException::new);

    if (!entrada.getUsuario().getIdUsuario().equals(usuarioOrigenId)) {
        throw new EntradaEnPetenenciaException();
    }

    Usuario usuarioDestino = usuarioRepository.findByTelefono(telefonoDestino)
        .orElseThrow(UsuarioNoEncontradoException::new);

    Usuario usuarioOrigen = usuarioRepository.findById(usuarioOrigenId)
        .orElseThrow(UsuarioNoEncontradoException::new);

    Transferencia transferencia = Transferencia.builder()
        .entrada(entrada)
        .usuarioOrigen(usuarioOrigen)
        .usuarioDestino(usuarioDestino)
        .fechaTransferencia(LocalDateTime.now())
        .estado(Transferencia.EstadoTransferencia.COMPLETADA)
        .comentario("Transferencia realizada correctamente")
        .build();

    Transferencia transferenciaGuardada = transferenciaRepository.save(transferencia);

    entrada.setUsuario(usuarioDestino);
    entradaRepository.save(entrada);
    return transferenciaConverter.toDto(transferenciaGuardada);
}


    public TransferenciaDTO crearTransferencia(TransferenciaDTO transferenciaDTO) {
        Transferencia transferencia = transferenciaConverter.toEntity(transferenciaDTO); 
        Transferencia transferenciaGuardada = transferenciaRepository.save(transferencia);
        return transferenciaConverter.toDto(transferenciaGuardada); 
    }

    public TransferenciaDTO TraerTransferenciaPorId(Long id) {
        Transferencia transferencia = transferenciaRepository.findById(id)
            .orElseThrow(() -> new TransferenciaNoEncontradaException());
        return transferenciaConverter.toDto(transferencia); 
    }

    public List<TransferenciaDTO> TraerTodasLasTransferencias() {
        return transferenciaRepository.findAll().stream()
                .map(transferencia -> transferenciaConverter.toDto(transferencia)) 
                .collect(Collectors.toList());
    }


    public void eliminarTransferencia(Long id) {
        Transferencia transferencia = transferenciaRepository.findById(id).orElseThrow(() -> new TransferenciaNoEncontradaException());
        transferenciaRepository.delete(transferencia);
    }
}
