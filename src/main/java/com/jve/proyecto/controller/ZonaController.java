package com.jve.proyecto.controller;

import com.jve.proyecto.dto.ZonaDTO;
import com.jve.proyecto.service.ZonaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/zonas")
public class ZonaController {

    private final ZonaService zonaService;

    public ZonaController(ZonaService zonaService) {
        this.zonaService = zonaService;
    }

    @GetMapping
    public ResponseEntity<List<ZonaDTO>> TraerZonas() {
        List<ZonaDTO> zonas = zonaService.TraerTodasLasZonas();
        return ResponseEntity.ok(zonas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ZonaDTO> TraerZonaPorId(@PathVariable Long id) {
        ZonaDTO zona = zonaService.TraerZonaPorId(id);
        return ResponseEntity.ok(zona);
    }

    @PostMapping
    public ResponseEntity<ZonaDTO> crearZona(@RequestBody ZonaDTO zonaDTO) {
        ZonaDTO nuevaZona = zonaService.crearZona(zonaDTO);
        return ResponseEntity.status(201).body(nuevaZona);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ZonaDTO> actualizarZona(@PathVariable Long id, @RequestBody ZonaDTO zonaDTO) {
        ZonaDTO zonaActualizada = zonaService.actualizarZona(id, zonaDTO);
        return ResponseEntity.ok(zonaActualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarZona(@PathVariable Long id) {
        zonaService.eliminarZona(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/concierto/{conciertoId}")
    public ResponseEntity<ZonaDTO> TraerZonaPorConcierto(@PathVariable Long conciertoId) {
        ZonaDTO zona = zonaService.TraerZonaPorConcierto(conciertoId);
        return ResponseEntity.ok(zona);
    }
}
