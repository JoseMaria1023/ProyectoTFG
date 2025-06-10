package com.jve.proyecto.controller;

import com.jve.proyecto.dto.AsientoDTO;
import com.jve.proyecto.service.AsientoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/asientos")
public class AsientoController {

    private final AsientoService asientoService;

    public AsientoController(AsientoService asientoService) {
        this.asientoService = asientoService;
    }

    @GetMapping
    public ResponseEntity<List<AsientoDTO>> TraerAsientos() {
        List<AsientoDTO> asientos = asientoService.TraerTodosLosAsientos();
        return ResponseEntity.ok(asientos);
    }

    @GetMapping("/concierto/{id}")
    public ResponseEntity<List<AsientoDTO>> TraerAsientosPorConcierto(@PathVariable("id") Long conciertoId) {
        List<AsientoDTO> asientos = asientoService.TraerAsientosPorConcierto(conciertoId);
        return ResponseEntity.ok(asientos);
    }

    @PostMapping
    public ResponseEntity<AsientoDTO> crearAsiento(@RequestBody AsientoDTO asientoDTO) {
        AsientoDTO nuevoAsiento = asientoService.crearAsiento(asientoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoAsiento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AsientoDTO> actualizarAsiento(@PathVariable Long id, @RequestBody AsientoDTO asientoDTO) {
        AsientoDTO actualizado = asientoService.actualizarAsiento(id, asientoDTO);
        return ResponseEntity.ok(actualizado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AsientoDTO> TraerAsientoPorId(@PathVariable Long id) {
        AsientoDTO asiento = asientoService.TraerAsientoPorId(id);
        return ResponseEntity.ok(asiento);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAsiento(@PathVariable Long id) {
        asientoService.eliminarAsiento(id);
        return ResponseEntity.noContent().build();
    }
}
