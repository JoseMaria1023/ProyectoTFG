package com.jve.proyecto.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.jve.proyecto.dto.UsuarioDTO;
import com.jve.proyecto.configuration.ModelMapperConfig;
import com.jve.proyecto.dto.EntradaDTO;
import com.jve.proyecto.entity.Entrada;
import com.jve.proyecto.entity.Usuario;
import com.jve.proyecto.repository.UsuarioRepository;
import com.jve.proyecto.repository.EntradaRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final EntradaRepository entradaRepository;
    private final ModelMapper modelMapper;

    public UsuarioService(UsuarioRepository usuarioRepository, EntradaRepository entradaRepository, 
                          ModelMapperConfig modelMapperConfig) {
        this.usuarioRepository = usuarioRepository;
        this.entradaRepository = entradaRepository;
        this.modelMapper = modelMapperConfig.modelMapper();
    }

    public UsuarioDTO crearUsuario(UsuarioDTO usuarioDTO) {
        Usuario usuario = modelMapper.map(usuarioDTO, Usuario.class);
        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        return modelMapper.map(usuarioGuardado, UsuarioDTO.class);
    }

    @Transactional
    public void transferirEntrada(Long idUsuario, Long idEntrada, String telefonoDestino) {
        Entrada entrada = entradaRepository.findById(idEntrada)
            .orElseThrow(() -> new RuntimeException("Entrada no encontrada con ID: " + idEntrada));
    
        if (!entrada.getUsuario().getIdUsuario().equals(idUsuario)) {
            throw new RuntimeException("El usuario no es el propietario de la entrada.");
        }
    
        Usuario nuevoUsuario = usuarioRepository.findByTelefono(telefonoDestino)
            .orElseThrow(() -> new RuntimeException("No se encontró usuario con el teléfono: " + telefonoDestino));
    
        entrada.setUsuario(nuevoUsuario);
        entradaRepository.save(entrada);
    }

    public UsuarioDTO obtenerUsuarioPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
        return modelMapper.map(usuario, UsuarioDTO.class);
    }

    public List<UsuarioDTO> obtenerTodosLosUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(usuario -> modelMapper.map(usuario, UsuarioDTO.class))
                .collect(Collectors.toList());
    }

    public UsuarioDTO actualizarUsuario(Long id, UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
        modelMapper.map(usuarioDTO, usuario);
        Usuario usuarioActualizado = usuarioRepository.save(usuario);
        return modelMapper.map(usuarioActualizado, UsuarioDTO.class);
    }

    public void eliminarUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
        usuarioRepository.delete(usuario);
    }

    public Optional<Usuario> findByUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }
}
