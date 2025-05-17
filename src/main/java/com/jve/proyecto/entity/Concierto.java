package com.jve.proyecto.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Concierto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Concierto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idConcierto;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime fecha;

    @ManyToOne
    @JoinColumn(name = "zona_id", nullable = false)
    private Zona zona;

    @ManyToOne
    @JoinColumn(name = "gira_id", nullable = false)
    private Gira gira;

    @Enumerated(EnumType.STRING)
    private EstadoConcierto estado;

    public enum EstadoConcierto {
        ACTIVO, CANCELADO, POSPUESTO
    }
}
