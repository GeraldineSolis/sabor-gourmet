package com.saborgourmet.clienteservice.repository;

import com.saborgourmet.clienteservice.entity.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    // Buscar por DNI (ignorando soft delete o manejándolo manualmente si no se usa @Where)
    Optional<Cliente> findByDni(String dni);

    // Verificar existencia
    boolean existsByDni(String dni);

    // Buscar solo activos (si no se usa la anotación @Where en la entidad)
    Page<Cliente> findByActivoTrue(Pageable pageable);
    Optional<Cliente> findByIdAndActivoTrue(Long id);
    Optional<Cliente> findByDniAndActivoTrue(String dni);
}