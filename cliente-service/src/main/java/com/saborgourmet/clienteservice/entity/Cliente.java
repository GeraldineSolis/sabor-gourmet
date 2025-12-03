package com.saborgourmet.clienteservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "clientes")
// Implementación de Soft Delete automática en consultas JPA
// @SQLDelete: Ejecuta esto cuando se llama a repository.delete()
@SQLDelete(sql = "UPDATE clientes SET activo = false WHERE id = ?")
// @Where: Filtra automáticamente los inactivos en repository.findAll()
// Nota: En versiones nuevas de Hibernate se usa @SQLRestriction, pero @Where es común en Spring Boot 3.x inicial
//@Where(clause = "activo = true")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombres;

    @Column(nullable = false)
    private String apellidos;

    @Column(nullable = false, unique = true, length = 8)
    private String dni;

    @Column(nullable = true)
    private String email;

    @Column(nullable = true)
    private String telefono;

    @Column(nullable = false)
    @Builder.Default
    private Boolean activo = true;
}