package com.jve.proyecto.controller;


import com.jve.proyecto.dto.GiraDTO;
import com.jve.proyecto.service.GiraService;
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
    public List<GiraDTO> obtenerGiras() {
        return giraService.obtenerTodasLasGiras();
    }

    @PostMapping
    public GiraDTO crearGira(@RequestBody GiraDTO giraDTO) {
        return giraService.crearGira(giraDTO);
    }

    @GetMapping("/{id}")
    public GiraDTO obtenerGiraPorId(@PathVariable Long id) {
        return giraService.obtenerGiraPorId(id);
    }

    @PutMapping("/{id}")
    public GiraDTO actualizarGira(@PathVariable Long id, @RequestBody GiraDTO giraDTO) {
        return giraService.actualizarGira(id, giraDTO);
    }

    @DeleteMapping("/{id}")
    public void eliminarGira(@PathVariable Long id) {
        giraService.eliminarGira(id);
    }
}
