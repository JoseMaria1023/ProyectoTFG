package com.jve.proyecto.controller;

import com.jve.proyecto.dto.GiraDTO;
import com.jve.proyecto.service.GiraService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/giras")
public class GiraController {

    private final GiraService giraService;

    public GiraController(GiraService giraService) {
        this.giraService = giraService;
    }

    @GetMapping
    public ResponseEntity<List<GiraDTO>> TraerGiras() {
        List<GiraDTO> giras = giraService.TraerTodasLasGiras();
        return giras.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(giras);
    }

    @PostMapping
    public ResponseEntity<GiraDTO> crearGira(@RequestBody GiraDTO giraDTO) {
        GiraDTO nuevaGira = giraService.crearGira(giraDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaGira);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GiraDTO> TraerGiraPorId(@PathVariable Long id) {
        GiraDTO gira = giraService.TraerGiraPorId(id);
        return gira == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(gira);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GiraDTO> actualizarGira(@PathVariable Long id, @RequestBody GiraDTO giraDTO) {
        GiraDTO actualizada = giraService.actualizarGira(id, giraDTO);
        return actualizada == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarGira(@PathVariable Long id) {
        giraService.eliminarGira(id);
        return ResponseEntity.noContent().build();
    }
}
