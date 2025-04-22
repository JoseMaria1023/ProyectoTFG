package com.jve.proyecto.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jve.proyecto.entity.Entrada;

@Repository
public interface EntradaRepository extends JpaRepository<Entrada, Long> {
    Optional<Entrada> findByCodigoQR(String codigoQR);
    List<Entrada> findByUsuario_IdUsuario(Long usuarioId);
    List<Entrada> findByConcierto_IdConcierto(Long conciertoId);
    List<Entrada> findByEstado(Entrada.EstadoEntrada estado);
}

