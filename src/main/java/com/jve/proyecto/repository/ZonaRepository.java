package com.jve.proyecto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jve.proyecto.entity.Zona;

@Repository
public interface ZonaRepository extends JpaRepository<Zona, Long> {
    List<Zona> findByRecinto_IdRecinto(Long recintoId);
}
