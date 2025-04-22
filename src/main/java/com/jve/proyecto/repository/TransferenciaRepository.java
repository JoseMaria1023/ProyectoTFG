package com.jve.proyecto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jve.proyecto.entity.Transferencia;

@Repository
public interface TransferenciaRepository extends JpaRepository<Transferencia, Long> {
    List<Transferencia> findByEntrada_IdEntrada(Long entradaId);
    List<Transferencia> findByUsuarioOrigen_IdUsuario(Long usuarioOrigenId);
    List<Transferencia> findByUsuarioDestino_IdUsuario(Long usuarioDestinoId);
}

