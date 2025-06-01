package com.jve.proyecto.controller;

import com.jve.proyecto.dto.AsientoDTO;
import com.jve.proyecto.dto.ConciertoDTO;
import com.jve.proyecto.service.AsientoService;
import com.jve.proyecto.service.ConciertoService;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    public List<ConciertoDTO> TraerConciertos() {
        return conciertoService.TraerTodosLosConciertos();
    }
      @GetMapping("/{id}")
    public ConciertoDTO TraerConciertoPorId(@PathVariable Long id) {
        return conciertoService.TraerConciertoPorId(id);
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
    
    @GetMapping("/{conciertoId}/asientos")
    public List<AsientoDTO> TraerAsientosPorConcierto(@PathVariable Long conciertoId) {
        return asientoService.TraerAsientosPorConcierto(conciertoId);
    }
    
     @GetMapping("/filtrar")
    public List<ConciertoDTO> filtrarConciertos(
        @RequestParam(required = false) Long artistaId,
        @RequestParam(required = false) 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaDesde,
        @RequestParam(required = false) 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaHasta,
        @RequestParam(required = false) String estado
    ) {
        LocalDateTime desdeDateTime = null;
        LocalDateTime hastaDateTime = null;

        if (fechaDesde != null) {
            desdeDateTime = fechaDesde.atStartOfDay();
        }
        if (fechaHasta != null) {
            hastaDateTime = fechaHasta.atTime(23, 59, 59);
        }
        return conciertoService.filtrarConciertos(artistaId, desdeDateTime, hastaDateTime, estado);
    }
    
    @GetMapping("/artista/{artistaId}")
    public List<ConciertoDTO> TraerConciertosPorArtista(@PathVariable("artistaId") Long artistaId) {
        return conciertoService.TraerConciertosPorArtista(artistaId);
    }
}
