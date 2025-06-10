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
        Usuario entidad = usuarioConverter.toEntity(usuarioDTO);
        Usuario guardado = usuarioRepository.save(entidad);
        return usuarioConverter.toDto(guardado);
    }


    public UsuarioDTO TraerUsuarioPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new UsuarioNoEncontradoException());
        return usuarioConverter.toDto(usuario);
    }

    public List<UsuarioDTO> TraerTodosLosUsuarios() {
        return usuarioRepository.findAll().stream()
            .map(usuarioConverter::toDto)
            .collect(Collectors.toList());
    }

    public UsuarioDTO actualizarUsuario(Long id, UsuarioDTO usuarioDTO) {
        Usuario existente = usuarioRepository.findById(id).orElseThrow(() -> new UsuarioNoEncontradoException());
        Usuario actualizado = usuarioConverter.toEntity(usuarioDTO);
        actualizado.setIdUsuario(existente.getIdUsuario());
        Usuario guardado = usuarioRepository.save(actualizado);
        return usuarioConverter.toDto(guardado);
    }

    public void eliminarUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new UsuarioNoEncontradoException());
        usuarioRepository.delete(usuario);
    }

    public Optional<Usuario> findByUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }
}
