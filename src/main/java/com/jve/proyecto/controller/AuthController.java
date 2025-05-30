package com.jve.proyecto.controller;


import com.jve.proyecto.dto.AuthRequest;
import com.jve.proyecto.dto.LoginResponse;
import com.jve.proyecto.dto.UsuarioDTO;
import com.jve.proyecto.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
    try {
        LoginResponse response = authService.login(authRequest);
        return ResponseEntity.ok(response);
    } catch (RuntimeException ex) {
        return ResponseEntity.status(401).body("Usuario o contraseña incorrectos.");
    }
}

    @PostMapping("/register")
    public ResponseEntity<UsuarioDTO> register(@RequestBody UsuarioDTO usuarioDTO) {
        try {
            UsuarioDTO createdUser = authService.register(usuarioDTO);
            return ResponseEntity.ok(createdUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
