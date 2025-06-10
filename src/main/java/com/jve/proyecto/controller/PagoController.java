package com.jve.proyecto.controller;

import com.jve.proyecto.dto.PagoDTO;
import com.jve.proyecto.service.PagoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<PagoDTO>> TraerPagos() {
        List<PagoDTO> pagos = pagoService.TraerTodosLosPagos();
        return pagos.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(pagos);
    }

    @PostMapping
    public ResponseEntity<PagoDTO> realizarPago(@RequestBody PagoDTO pagoDTO) {
        PagoDTO nuevoPago = pagoService.crearPago(pagoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPago);
    }
}
