package com.jve.proyecto.controller;

import com.jve.proyecto.dto.TransferenciaDTO;
import com.jve.proyecto.dto.TransferenciaRequest;
import com.jve.proyecto.service.TransferenciaService;
import com.jve.proyecto.entity.Usuario;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/api/transferencias")
public class TransferenciaController {

    private final TransferenciaService transferenciaService;

    public TransferenciaController(TransferenciaService transferenciaService) {
        this.transferenciaService = transferenciaService;
    }

    @PostMapping("/transferir")
    public TransferenciaDTO transferirEntrada(@RequestBody Map<String, Object> datos) {
        Long idEntrada = Long.valueOf(datos.get("idEntrada").toString());
        Long usuarioOrigenId = Long.valueOf(datos.get("usuarioOrigenId").toString());
        String telefonoDestino = datos.get("telefonoDestino").toString();

        return transferenciaService.transferirEntrada(idEntrada, usuarioOrigenId, telefonoDestino);
    }

    @GetMapping("/{id}")
    public TransferenciaDTO obtenerTransferenciaPorId(@PathVariable Long id) {
        return transferenciaService.obtenerTransferenciaPorId(id);
    }

    @DeleteMapping("/{id}")
    public void eliminarTransferencia(@PathVariable Long id) {
        transferenciaService.eliminarTransferencia(id);
    }
}
