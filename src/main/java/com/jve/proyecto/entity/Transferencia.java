package com.jve.proyecto.entity;

import java.time.LocalDateTime;

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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Transferencia")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transferencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTransferencia;

    @ManyToOne
    @JoinColumn(name = "entrada_id", nullable = false)
    private Entrada entrada;

    @ManyToOne
    @JoinColumn(name = "usuarioOrigen_id", nullable = false)
    private Usuario usuarioOrigen;

    @ManyToOne
    @JoinColumn(name = "usuarioDestino_id", nullable = false)
    private Usuario usuarioDestino;

    @Column(nullable = false)
    private LocalDateTime fechaTransferencia;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoTransferencia estado;

    @Column(length = 255)
    private String comentario;

    public enum EstadoTransferencia {
        COMPLETADA, PENDIENTE, FALLIDA, CANCELADA
    }
}
