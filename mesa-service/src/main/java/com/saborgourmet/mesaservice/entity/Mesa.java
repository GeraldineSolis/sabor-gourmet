package com.saborgourmet.mesaservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "mesas")
public class Mesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String numeroMesa; // Ej: "M-01", "Terraza-05"

    @Column(nullable = false)
    private Integer capacidad;

    @Column(nullable = false)
    private String ubicacion; // "Salon", "Terraza", "VIP"

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private EstadoMesa estado = EstadoMesa.DISPONIBLE;
}