// src/main/java/com/jve/proyecto/controller/AsientoController.java
package com.jve.proyecto.controller;

import com.jve.proyecto.dto.AsientoDTO;
import com.jve.proyecto.service.AsientoService;
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
    public List<AsientoDTO> obtenerAsientos() {
        return asientoService.obtenerTodosLosAsientos();
    }

    @GetMapping("/concierto/{id}")
    public List<AsientoDTO> obtenerAsientosPorConcierto(@PathVariable("id") Long conciertoId) {
        return asientoService.obtenerAsientosPorConcierto(conciertoId);
    }

    @PostMapping
    public AsientoDTO crearAsiento(@RequestBody AsientoDTO asientoDTO) {
        return asientoService.crearAsiento(asientoDTO);
    }
    
    @PutMapping("/{id}")
    public AsientoDTO actualizarAsiento(@PathVariable Long id, @RequestBody AsientoDTO asientoDTO) {
        return asientoService.actualizarAsiento(id, asientoDTO);
    }
    @GetMapping("/{id}")
    public AsientoDTO obtenerAsientoPorId(@PathVariable Long id) {
    return asientoService.obtenerAsientoPorId(id);
}

    
    @DeleteMapping("/{id}")
    public void eliminarAsiento(@PathVariable Long id) {
        asientoService.eliminarAsiento(id);
    }
}
