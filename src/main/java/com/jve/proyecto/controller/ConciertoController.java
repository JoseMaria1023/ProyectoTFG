package com.jve.proyecto.controller;

import com.jve.proyecto.dto.AsientoDTO;
import com.jve.proyecto.dto.ConciertoDTO;
import com.jve.proyecto.service.AsientoService;
import com.jve.proyecto.service.ConciertoService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<ConciertoDTO>> TraerConciertos() {
        List<ConciertoDTO> conciertos = conciertoService.TraerTodosLosConciertos();
        return conciertos.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(conciertos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConciertoDTO> TraerConciertoPorId(@PathVariable Long id) {
        ConciertoDTO concierto = conciertoService.TraerConciertoPorId(id);
        return concierto == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(concierto);
    }

    @PostMapping
    public ResponseEntity<ConciertoDTO> crearConcierto(@RequestBody ConciertoDTO conciertoDTO) {
        ConciertoDTO nuevo = conciertoService.crearConcierto(conciertoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConciertoDTO> actualizarConcierto(@PathVariable Long id, @RequestBody ConciertoDTO conciertoDTO) {
        ConciertoDTO actualizado = conciertoService.actualizarConcierto(id, conciertoDTO);
        return actualizado == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarConcierto(@PathVariable Long id) {
        conciertoService.eliminarConcierto(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{conciertoId}/asientos")
    public ResponseEntity<List<AsientoDTO>> TraerAsientosPorConcierto(@PathVariable Long conciertoId) {
        List<AsientoDTO> asientos = asientoService.TraerAsientosPorConcierto(conciertoId);
        return asientos.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(asientos);
    }

    @GetMapping("/filtrar")
    public ResponseEntity<List<ConciertoDTO>> filtrarConciertos(
            @RequestParam(required = false) Long artistaId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaDesde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaHasta,
            @RequestParam(required = false) String estado) {

        LocalDateTime desdeDateTime = fechaDesde != null ? fechaDesde.atStartOfDay() : null;
        LocalDateTime hastaDateTime = fechaHasta != null ? fechaHasta.atTime(23, 59, 59) : null;

        List<ConciertoDTO> filtrados = conciertoService.filtrarConciertos(artistaId, desdeDateTime, hastaDateTime, estado);
        return filtrados.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(filtrados);
    }

    @GetMapping("/artista/{artistaId}")
    public ResponseEntity<List<ConciertoDTO>> TraerConciertosPorArtista(@PathVariable("artistaId") Long artistaId) {
        List<ConciertoDTO> conciertos = conciertoService.TraerConciertosPorArtista(artistaId);
        return conciertos.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(conciertos);
    }
}
