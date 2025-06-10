package com.jve.proyecto.controller;

import com.jve.proyecto.dto.RecintoDTO;
import com.jve.proyecto.service.RecintoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/recintos")
public class RecintoController {

    private final RecintoService recintoService;

    public RecintoController(RecintoService recintoService) {
        this.recintoService = recintoService;
    }

    @GetMapping
    public ResponseEntity<List<RecintoDTO>> TraerRecintos() {
        List<RecintoDTO> recintos = recintoService.TraerTodosLosRecintos();
        return recintos.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(recintos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecintoDTO> TraerRecintoPorId(@PathVariable Long id) {
        RecintoDTO recinto = recintoService.TraerRecintoPorId(id);
        return ResponseEntity.ok(recinto);
    }

    @PostMapping
    public ResponseEntity<RecintoDTO> crearRecinto(@RequestBody RecintoDTO recintoDTO) {
        RecintoDTO nuevoRecinto = recintoService.crearRecinto(recintoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoRecinto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecintoDTO> actualizarRecinto(@PathVariable Long id, @RequestBody RecintoDTO recintoDTO) {
        RecintoDTO recintoActualizado = recintoService.actualizarRecinto(id, recintoDTO);
        return ResponseEntity.ok(recintoActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRecinto(@PathVariable Long id) {
        recintoService.eliminarRecinto(id);
        return ResponseEntity.noContent().build();
    }
}
