package com.jve.proyecto.controller;

import com.jve.proyecto.dto.PagoDTO;
import com.jve.proyecto.service.PagoService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/pagos")
public class PagoController {

    private final PagoService pagoService;

    public PagoController(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    @GetMapping
    public List<PagoDTO> obtenerPagos() {
        return pagoService.obtenerTodosLosPagos();
    }

    @PostMapping
    public PagoDTO realizarPago(@RequestBody PagoDTO pagoDTO) {
        return pagoService.crearPago(pagoDTO);
    }
}

