package com.saborgourmet.mesaservice.repository;

import com.saborgourmet.mesaservice.entity.EstadoMesa;
import com.saborgourmet.mesaservice.entity.Mesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MesaRepository extends JpaRepository<Mesa, Long> {
    Optional<Mesa> findByNumeroMesa(String numeroMesa);
    boolean existsByNumeroMesa(String numeroMesa);
    List<Mesa> findByEstado(EstadoMesa estado);
}