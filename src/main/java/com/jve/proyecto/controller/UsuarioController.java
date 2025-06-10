package com.jve.proyecto.controller;

import com.jve.proyecto.dto.UsuarioDTO;
import com.jve.proyecto.dto.EntradaDTO;
import com.jve.proyecto.dto.TransferenciaRequest;
import com.jve.proyecto.service.EntradaService;
import com.jve.proyecto.service.UsuarioService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final EntradaService entradaService;

    public UsuarioController(UsuarioService usuarioService, EntradaService entradaService) {
        this.usuarioService = usuarioService;
        this.entradaService = entradaService;
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> TraerUsuarios() {
        List<UsuarioDTO> usuarios = usuarioService.TraerTodosLosUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> TraerUsuarioPorId(@PathVariable Long id) {
        UsuarioDTO usuario = usuarioService.TraerUsuarioPorId(id);
        return ResponseEntity.ok(usuario);
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> crearUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        UsuarioDTO nuevoUsuario = usuarioService.crearUsuario(usuarioDTO);
        return ResponseEntity.status(201).body(nuevoUsuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> actualizarUsuario(@PathVariable Long id, @RequestBody UsuarioDTO usuarioDTO) {
        UsuarioDTO usuarioActualizado = usuarioService.actualizarUsuario(id, usuarioDTO);
        return ResponseEntity.ok(usuarioActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/entradas")
    public ResponseEntity<List<EntradaDTO>> TraerEntradasPorUsuario(@PathVariable Long id) {
        List<EntradaDTO> entradas = entradaService.TraerEntradasPorUsuario(id);
        return ResponseEntity.ok(entradas);
    }

}
