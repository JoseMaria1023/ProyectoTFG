package com.jve.proyecto.controller;

import com.jve.proyecto.dto.TransferenciaDTO;
import com.jve.proyecto.service.TransferenciaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/transferencias")
public class TransferenciaController {

    private final TransferenciaService transferenciaService;

    public TransferenciaController(TransferenciaService transferenciaService) {
        this.transferenciaService = transferenciaService;
    }

    @PostMapping("/transferir")
    public ResponseEntity<TransferenciaDTO> transferirEntrada(@RequestBody Map<String, Object> datos) {
        Long idEntrada = Long.valueOf(datos.get("idEntrada").toString());
        Long usuarioOrigenId = Long.valueOf(datos.get("usuarioOrigenId").toString());
        String telefonoDestino = datos.get("telefonoDestino").toString();

        TransferenciaDTO transferencia = transferenciaService.transferirEntrada(idEntrada, usuarioOrigenId, telefonoDestino);
        return ResponseEntity.status(HttpStatus.CREATED).body(transferencia);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransferenciaDTO> TraerTransferenciaPorId(@PathVariable Long id) {
        TransferenciaDTO transferencia = transferenciaService.TraerTransferenciaPorId(id);
        return ResponseEntity.ok(transferencia);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTransferencia(@PathVariable Long id) {
        transferenciaService.eliminarTransferencia(id);
        return ResponseEntity.noContent().build();
    }
}
