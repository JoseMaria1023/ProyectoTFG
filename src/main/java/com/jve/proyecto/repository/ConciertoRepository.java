package com.jve.proyecto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jve.proyecto.entity.Concierto;

@Repository
public interface ConciertoRepository extends JpaRepository<Concierto, Long> {
    List<Concierto> findByGira_IdGira(Long giraId);
    List<Concierto> findByEstado(Concierto.EstadoConcierto estado);
}
