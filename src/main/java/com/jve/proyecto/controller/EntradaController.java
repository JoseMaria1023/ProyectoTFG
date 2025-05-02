package com.jve.proyecto.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.jve.proyecto.dto.EntradaDTO;
import com.jve.proyecto.service.EntradaService;

@RestController
@RequestMapping("/api/entradas")
public class EntradaController {

    private final EntradaService entradaService;

    public EntradaController(EntradaService entradaService) {
        this.entradaService = entradaService;
    }

    @PostMapping
    public ResponseEntity<EntradaDTO> crearEntrada(@RequestBody EntradaDTO entradaDTO) {
        EntradaDTO nuevaEntrada = entradaService.crearEntrada(entradaDTO);
        return ResponseEntity.ok(nuevaEntrada);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntradaDTO> obtenerEntradaPorId(@PathVariable Long id) {
        EntradaDTO entrada = entradaService.obtenerEntradaPorId(id);
        return ResponseEntity.ok(entrada);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<EntradaDTO>> obtenerEntradasPorUsuario(@PathVariable Long usuarioId) {
        List<EntradaDTO> entradas = entradaService.obtenerEntradasPorUsuario(usuarioId);
        return ResponseEntity.ok(entradas);
    }

    @GetMapping
    public ResponseEntity<List<EntradaDTO>> obtenerTodasLasEntradas() {
        List<EntradaDTO> entradas = entradaService.obtenerTodasLasEntradas();
        return ResponseEntity.ok(entradas);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntradaDTO> actualizarEntrada(@PathVariable Long id, @RequestBody EntradaDTO entradaDTO) {
        EntradaDTO entradaActualizada = entradaService.actualizarEntrada(id, entradaDTO);
        return ResponseEntity.ok(entradaActualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEntrada(@PathVariable Long id) {
        entradaService.eliminarEntrada(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/revender")
    public ResponseEntity<EntradaDTO> revenderEntrada(
            @PathVariable Long id,
            @RequestParam BigDecimal precioReventa) {
        EntradaDTO entradaRevendida = entradaService.revenderEntrada(id, precioReventa);
        return ResponseEntity.ok(entradaRevendida);
    }
}
