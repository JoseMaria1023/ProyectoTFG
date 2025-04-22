package com.jve.proyecto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jve.proyecto.entity.Gira;

@Repository
public interface GiraRepository extends JpaRepository<Gira, Long> {
    List<Gira> findByArtista_IdArtista(Long artistaId);
}

