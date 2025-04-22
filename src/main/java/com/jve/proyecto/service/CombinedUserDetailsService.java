package com.jve.proyecto.service;

import com.jve.proyecto.entity.Artista;
import com.jve.proyecto.entity.Usuario;
import com.jve.proyecto.repository.ArtistaRepository;
import com.jve.proyecto.service.UsuarioService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CombinedUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ArtistaRepository artistaRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> optUsuario = usuarioService.findByUsername(username);
        if (optUsuario.isPresent()) {
            return optUsuario.get();
        }
        
        Optional<Artista> optArtista = artistaRepository.findByUsername(username);
        if (optArtista.isPresent()) {
            return optArtista.get();
        }

        throw new UsernameNotFoundException("Usuario o Artista " + username + " no encontrado");
    }
}