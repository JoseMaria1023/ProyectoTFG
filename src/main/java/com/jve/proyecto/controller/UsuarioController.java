// src/main/java/com/jve/proyecto/controller/UsuarioController.java
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

    public UsuarioController(UsuarioService usuarioService,EntradaService entradaService) {
        this.usuarioService = usuarioService;
        this.entradaService = entradaService;
    }

    @GetMapping
    public List<UsuarioDTO> TraerUsuarios() {
        return usuarioService.TraerTodosLosUsuarios();
    }

    @GetMapping("/{id}")
    public UsuarioDTO TraerUsuarioPorId(@PathVariable Long id) {
        return usuarioService.TraerUsuarioPorId(id);
    }


    @PostMapping
    public UsuarioDTO crearUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        return usuarioService.crearUsuario(usuarioDTO);
    }

    @PutMapping("/{id}")
    public UsuarioDTO actualizarUsuario(@PathVariable Long id, @RequestBody UsuarioDTO usuarioDTO) {
        return usuarioService.actualizarUsuario(id, usuarioDTO);
    }

    @DeleteMapping("/{id}")
    public void eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
    }
    @GetMapping("/{id}/entradas")
    public List<EntradaDTO> TraerEntradasPorUsuario(@PathVariable Long id) {
        return entradaService.TraerEntradasPorUsuario(id);
    }
    @PutMapping("/{idUsuario}/entradas/{idEntrada}/transferir")
public ResponseEntity<String> transferirEntrada(
        @PathVariable Long idUsuario,
        @PathVariable Long idEntrada,
        @RequestBody TransferenciaRequest request) {
    usuarioService.transferirEntrada(idUsuario, idEntrada, request.getTelefonoDestino());
    return ResponseEntity.ok("Entrada transferida correctamente");
}
}
