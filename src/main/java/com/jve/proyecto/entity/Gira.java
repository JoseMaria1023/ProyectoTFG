package com.jve.proyecto.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Gira")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Gira {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idGira;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(columnDefinition = "LONGTEXT")
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "artista_id", nullable = false)
    private Artista artista;
}