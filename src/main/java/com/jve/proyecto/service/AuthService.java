package com.jve.proyecto.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import com.jve.proyecto.dto.AuthRequest;
import com.jve.proyecto.dto.LoginResponse;
import com.jve.proyecto.dto.UsuarioDTO;
import com.jve.proyecto.entity.Artista;
import com.jve.proyecto.entity.Usuario;
import com.jve.proyecto.security.JwtTokenProvider;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UsuarioService userService;
    private final PasswordEncoder passwordEncoder;

    public AuthService(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, 
                       UsuarioService userService, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    public UsuarioDTO register(UsuarioDTO userDTO) throws Exception {
        // (Lógica de registro)
        if (userDTO.getPassword().length() < 8) {
            throw new Exception("La contraseña es muy pequeña");
        }
        
        if (userDTO.getRole() == null || userDTO.getRole().isBlank()) {
            userDTO.setRole("USER");
        }
        if (userDTO.getActivo() == null) {
            userDTO.setActivo(true);
        }
    
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
    
        return userService.crearUsuario(userDTO);
    }

    public LoginResponse login(AuthRequest loginDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword())
        );

        String token = jwtTokenProvider.generateToken(authentication);

        Object principal = authentication.getPrincipal();
        Long id;
        String username;

        if (principal instanceof Usuario usuario) {
            id = usuario.getIdUsuario();
            username = usuario.getUsername();
        } else if (principal instanceof Artista artista) {
            id = artista.getIdArtista();
            username = artista.getUsername();
        } else {
            throw new RuntimeException("Tipo de usuario no soportado");
        }
        
        if (id == null) {
            throw new RuntimeException("El objeto LoginResponse no contiene idUser");
        }

        return new LoginResponse(
                username,
                authentication.getAuthorities().stream()
                               .map(GrantedAuthority::getAuthority)
                               .toList(),
                token,
                id
        );
    }
}