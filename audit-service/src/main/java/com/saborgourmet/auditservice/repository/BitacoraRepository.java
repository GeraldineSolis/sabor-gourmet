package com.saborgourmet.auditservice.repository;

import com.saborgourmet.auditservice.entity.Bitacora;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BitacoraRepository extends JpaRepository<Bitacora, Long> {
    List<Bitacora> findByUsername(String username);
    List<Bitacora> findByEntidad(String entidad);
    List<Bitacora> findByAccion(String accion);
}