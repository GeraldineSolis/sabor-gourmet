package com.saborgourmet.auditservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bitacora")
public class Bitacora {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Quién realizó la acción
    @Column(nullable = false)
    private String username;

    // Qué tipo de acción (CREATE, UPDATE, DELETE, LOGIN)
    @Column(nullable = false)
    private String accion;

    // Sobre qué entidad (CLIENTE, MESA, USUARIO)
    @Column(nullable = false)
    private String entidad;

    // ID de la entidad afectada (opcional, para referencia)
    @Column(nullable = true)
    private String entidadId;

    // Fecha y hora del evento
    @Column(nullable = false)
    private LocalDateTime fechaHora;

    // Detalles adicionales o JSON del cambio
    @Column(columnDefinition = "TEXT")
    private String detalle;
}