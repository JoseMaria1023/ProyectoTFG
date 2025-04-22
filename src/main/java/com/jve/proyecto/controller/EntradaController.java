package com.jve.proyecto.controller;

import com.jve.proyecto.dto.EntradaDTO;
import com.jve.proyecto.service.EntradaService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/entradas")
public class EntradaController {

    private final EntradaService entradaService;

    public EntradaController(EntradaService entradaService) {
        this.entradaService = entradaService;
    }

    @GetMapping("/TodasEntradas")
    public List<EntradaDTO> obtenerEntradas() {
        return entradaService.obtenerTodasLasEntradas();
    }

    @PostMapping
    public EntradaDTO crearEntrada(@RequestBody EntradaDTO entradaDTO) {
        return entradaService.crearEntrada(entradaDTO);
    }

    @GetMapping("/{id}")
    public EntradaDTO obtenerEntradaPorId(@PathVariable Long id) {
        return entradaService.obtenerEntradaPorId(id);
    }

    @DeleteMapping("/{id}")
    public void eliminarEntrada(@PathVariable Long id) {
        entradaService.eliminarEntrada(id);
    }
}

