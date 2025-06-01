package com.jve.proyecto.controller;

import com.jve.proyecto.dto.ZonaDTO;
import com.jve.proyecto.service.ZonaService;
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
    public List<ZonaDTO> TraerZonas() {
        return zonaService.TraerTodasLasZonas();
    }

    @GetMapping("/{id}")
    public ZonaDTO TraerZonaPorId(@PathVariable Long id) {
        return zonaService.TraerZonaPorId(id);
    }

    @PostMapping
    public ZonaDTO crearZona(@RequestBody ZonaDTO zonaDTO) {
        return zonaService.crearZona(zonaDTO);
    }
    
    @PutMapping("/{id}")
    public ZonaDTO actualizarZona(@PathVariable Long id, @RequestBody ZonaDTO zonaDTO) {
        return zonaService.actualizarZona(id, zonaDTO);
    }
    
    @DeleteMapping("/{id}")
    public void eliminarZona(@PathVariable Long id) {
        zonaService.eliminarZona(id);
    }
     @GetMapping("/concierto/{conciertoId}")
    public ZonaDTO TraerZonaPorConcierto(@PathVariable Long conciertoId) {
        return zonaService.TraerZonaPorConcierto(conciertoId);
    }
}