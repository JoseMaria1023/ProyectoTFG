package com.jve.proyecto.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.HttpStatus;
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
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaEntrada);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntradaDTO> TraerEntradaPorId(@PathVariable Long id) {
        EntradaDTO entrada = entradaService.TraerEntradaPorId(id);
        return entrada == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(entrada);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<EntradaDTO>> TraerEntradasPorUsuario(@PathVariable Long usuarioId) {
        List<EntradaDTO> entradas = entradaService.TraerEntradasPorUsuario(usuarioId);
        return entradas.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(entradas);
    }

    @GetMapping
    public ResponseEntity<List<EntradaDTO>> TraerTodasLasEntradas() {
        List<EntradaDTO> entradas = entradaService.TraerTodasLasEntradas();
        return entradas.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(entradas);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntradaDTO> actualizarEntrada(@PathVariable Long id, @RequestBody EntradaDTO entradaDTO) {
        EntradaDTO entradaActualizada = entradaService.actualizarEntrada(id, entradaDTO);
        return entradaActualizada == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(entradaActualizada);
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
        return entradaRevendida == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(entradaRevendida);
    }
}
