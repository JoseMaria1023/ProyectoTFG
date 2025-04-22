package com.jve.proyecto.controller;

import com.jve.proyecto.dto.AsientoDTO;
import com.jve.proyecto.dto.ConciertoDTO;
import com.jve.proyecto.service.AsientoService;
import com.jve.proyecto.service.ConciertoService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/conciertos")
public class ConciertoController {

    private final ConciertoService conciertoService;
    private final AsientoService asientoService;

    public ConciertoController(ConciertoService conciertoService, AsientoService asientoService) {
        this.conciertoService = conciertoService;
        this.asientoService = asientoService;
    }

    @GetMapping
    public List<ConciertoDTO> obtenerConciertos() {
        return conciertoService.obtenerTodosLosConciertos();
    }

    @PostMapping
    public ConciertoDTO crearConcierto(@RequestBody ConciertoDTO conciertoDTO) {
        return conciertoService.crearConcierto(conciertoDTO);
    }
    
    @PutMapping("/{id}")
    public ConciertoDTO actualizarConcierto(@PathVariable Long id, @RequestBody ConciertoDTO conciertoDTO) {
        return conciertoService.actualizarConcierto(id, conciertoDTO);
    }
    
    @DeleteMapping("/{id}")
    public void eliminarConcierto(@PathVariable Long id) {
        conciertoService.eliminarConcierto(id);
    }
    
    // NUEVO: Obtener asientos por el id del concierto
    @GetMapping("/{conciertoId}/asientos")
    public List<AsientoDTO> obtenerAsientosPorConcierto(@PathVariable Long conciertoId) {
        return asientoService.obtenerAsientosPorConcierto(conciertoId);
    }
    
    // También se mantiene el endpoint para obtener conciertos por artista
    @GetMapping("/artista/{artistaId}")
    public List<ConciertoDTO> obtenerConciertosPorArtista(@PathVariable("artistaId") Long artistaId) {
        return conciertoService.obtenerConciertosPorArtista(artistaId);
    }
}
