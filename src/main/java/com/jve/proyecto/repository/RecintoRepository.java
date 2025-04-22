package com.jve.proyecto.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jve.proyecto.entity.Recinto;

@Repository
public interface RecintoRepository extends JpaRepository<Recinto, Long> {
    Optional<Recinto> findByNombre(String nombre);
}

