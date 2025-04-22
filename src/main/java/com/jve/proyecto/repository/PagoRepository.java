package com.jve.proyecto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jve.proyecto.entity.Pago;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {
    List<Pago> findByEntrada_IdEntrada(Long entradaId);
    List<Pago> findByUsuario_IdUsuario(Long usuarioId);
    List<Pago> findByEstado(Pago.EstadoPago estado);
}

