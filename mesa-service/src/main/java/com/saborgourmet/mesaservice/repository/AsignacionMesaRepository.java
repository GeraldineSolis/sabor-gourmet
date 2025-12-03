package com.saborgourmet.mesaservice.repository;

import com.saborgourmet.mesaservice.entity.AsignacionMesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AsignacionMesaRepository extends JpaRepository<AsignacionMesa, Long> {
    // Buscar si hay una asignación activa para una mesa específica
    Optional<AsignacionMesa> findByMesaIdAndActivoTrue(Long mesaId);

    // Listar todas las asignaciones activas
    List<AsignacionMesa> findByActivoTrue();

    // Buscar asignaciones de un cliente específico
    List<AsignacionMesa> findByIdCliente(Long idCliente);
}