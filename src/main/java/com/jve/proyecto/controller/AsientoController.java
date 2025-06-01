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
    public List<AsientoDTO> TraerAsientos() {
        return asientoService.TraerTodosLosAsientos();
    }

    @GetMapping("/concierto/{id}")
    public List<AsientoDTO> TraerAsientosPorConcierto(@PathVariable("id") Long conciertoId) {
        return asientoService.TraerAsientosPorConcierto(conciertoId);
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
    public AsientoDTO TraerAsientoPorId(@PathVariable Long id) {
    return asientoService.TraerAsientoPorId(id);
}

    
    @DeleteMapping("/{id}")
    public void eliminarAsiento(@PathVariable Long id) {
        asientoService.eliminarAsiento(id);
    }
}
