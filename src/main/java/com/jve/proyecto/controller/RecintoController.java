package com.jve.proyecto.controller;

import com.jve.proyecto.dto.RecintoDTO;
import com.jve.proyecto.service.RecintoService;
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
    public List<RecintoDTO> obtenerRecintos() {
        return recintoService.obtenerTodosLosRecintos();
    }

    @GetMapping("/{id}")
    public RecintoDTO obtenerRecintoPorId(@PathVariable Long id) {
        return recintoService.obtenerRecintoPorId(id);
    }

    @PostMapping
    public RecintoDTO crearRecinto(@RequestBody RecintoDTO recintoDTO) {
        return recintoService.crearRecinto(recintoDTO);
    }

    @PutMapping("/{id}")
    public RecintoDTO actualizarRecinto(@PathVariable Long id, @RequestBody RecintoDTO recintoDTO) {
        return recintoService.actualizarRecinto(id, recintoDTO);
    }

    @DeleteMapping("/{id}")
    public void eliminarRecinto(@PathVariable Long id) {
        recintoService.eliminarRecinto(id);
    }
}