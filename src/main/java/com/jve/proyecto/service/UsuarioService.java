package com.jve.proyecto.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.jve.proyecto.converter.UsuarioConverter;
import com.jve.proyecto.dto.UsuarioDTO;
import com.jve.proyecto.entity.Entrada;
import com.jve.proyecto.entity.Usuario;
import com.jve.proyecto.exceptions.EntradaEnPetenenciaException;
import com.jve.proyecto.exceptions.EntradaNoEncontradaException;
import com.jve.proyecto.exceptions.UsuarioNoEncontradoException;
import com.jve.proyecto.repository.EntradaRepository;
import com.jve.proyecto.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final EntradaRepository entradaRepository;
    private final UsuarioConverter usuarioConverter;

    public UsuarioService(UsuarioRepository usuarioRepository,
                          EntradaRepository entradaRepository,
                          UsuarioConverter usuarioConverter) {
        this.usuarioRepository = usuarioRepository;
        this.entradaRepository = entradaRepository;
        this.usuarioConverter = usuarioConverter;
    }

    public UsuarioDTO crearUsuario(UsuarioDTO usuarioDTO) {
        // DTO → entidad
        Usuario entidad = usuarioConverter.toEntity(usuarioDTO);
        Usuario guardado = usuarioRepository.save(entidad);
        // entidad → DTO
        return usuarioConverter.toDto(guardado);
    }

    @Transactional
    public void transferirEntrada(Long idUsuario, Long idEntrada, String telefonoDestino) {
        Entrada entrada = entradaRepository.findById(idEntrada)
            .orElseThrow(() -> new EntradaNoEncontradaException());

        if (!entrada.getUsuario().getIdUsuario().equals(idUsuario)) {
            throw new EntradaEnPetenenciaException();
        }

        Usuario nuevoUsuario = usuarioRepository.findByTelefono(telefonoDestino)
            .orElseThrow(() -> new UsuarioNoEncontradoException());

        entrada.setUsuario(nuevoUsuario);
        entradaRepository.save(entrada);
    }

    public UsuarioDTO obtenerUsuarioPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new UsuarioNoEncontradoException());
        return usuarioConverter.toDto(usuario);
    }

    public List<UsuarioDTO> obtenerTodosLosUsuarios() {
        return usuarioRepository.findAll().stream()
            .map(usuarioConverter::toDto)
            .collect(Collectors.toList());
    }

    public UsuarioDTO actualizarUsuario(Long id, UsuarioDTO usuarioDTO) {
        Usuario existente = usuarioRepository.findById(id)
            .orElseThrow(() -> new UsuarioNoEncontradoException());
        // Mapear propiedades del DTO sobre la entidad existente
        Usuario actualizado = usuarioConverter.toEntity(usuarioDTO);
        actualizado.setIdUsuario(existente.getIdUsuario());
        Usuario guardado = usuarioRepository.save(actualizado);
        return usuarioConverter.toDto(guardado);
    }

    public void eliminarUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new UsuarioNoEncontradoException());
        usuarioRepository.delete(usuario);
    }

    public Optional<Usuario> findByUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }
}
