package com.jve.proyecto.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jve.proyecto.entity.Artista;
import com.jve.proyecto.entity.Usuario;
import com.jve.proyecto.repository.ArtistaRepository;
import com.jve.proyecto.repository.UsuarioRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepo;

    @Autowired
    private ArtistaRepository artistaRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> usuarioOpt = usuarioRepo.findByUsername(username);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            return new org.springframework.security.core.userdetails.User(
                    usuario.getUsername(),
                    usuario.getPassword(),
                    List.of(new SimpleGrantedAuthority(usuario.getRole())) // o un Set<GrantedAuthority>
            );
        }

        Optional<Artista> artistaOpt = artistaRepo.findByUsername(username);
        if (artistaOpt.isPresent()) {
            Artista artista = artistaOpt.get();
            return new org.springframework.security.core.userdetails.User(
                    artista.getUsername(),
                    artista.getPassword(),
                    List.of(new SimpleGrantedAuthority("ROLE_ARTISTA"))
            );
        }

        throw new UsernameNotFoundException("Usuario o artista no encontrado con username: " + username);
    }
}