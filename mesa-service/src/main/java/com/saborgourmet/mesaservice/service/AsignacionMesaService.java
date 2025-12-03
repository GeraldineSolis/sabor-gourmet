package com.saborgourmet.mesaservice.service;

import com.saborgourmet.mesaservice.dto.AsignacionMesaRequest;
import com.saborgourmet.mesaservice.entity.AsignacionMesa;
import com.saborgourmet.mesaservice.entity.EstadoMesa;
import com.saborgourmet.mesaservice.entity.Mesa;
import com.saborgourmet.mesaservice.exception.MesaNoDisponibleException;
import com.saborgourmet.mesaservice.exception.ResourceNotFoundException;
import com.saborgourmet.mesaservice.repository.AsignacionMesaRepository;
import com.saborgourmet.mesaservice.repository.MesaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AsignacionMesaService {

    private final AsignacionMesaRepository asignacionRepository;
    private final MesaRepository mesaRepository;

    public AsignacionMesaService(AsignacionMesaRepository asignacionRepository, MesaRepository mesaRepository) {
        this.asignacionRepository = asignacionRepository;
        this.mesaRepository = mesaRepository;
    }

    @Transactional
    public AsignacionMesa crearAsignacion(AsignacionMesaRequest request) {
        Mesa mesa = mesaRepository.findById(request.getIdMesa())
                .orElseThrow(() -> new ResourceNotFoundException("Mesa no encontrada"));

        // Validar que la mesa esté DISPONIBLE
        if (mesa.getEstado() != EstadoMesa.DISPONIBLE) {
            throw new MesaNoDisponibleException("La mesa " + mesa.getNumeroMesa() + " no está disponible. Estado actual: " + mesa.getEstado());
        }

        // Crear asignación
        AsignacionMesa asignacion = AsignacionMesa.builder()
                .mesa(mesa)
                .idCliente(request.getIdCliente())
                .cantidadComensales(request.getCantidadComensales())
                .fechaHoraInicio(LocalDateTime.now())
                .activo(true)
                .build();

        // Cambiar estado de la mesa a OCUPADA
        mesa.setEstado(EstadoMesa.OCUPADA);
        mesaRepository.save(mesa);

        return asignacionRepository.save(asignacion);
    }

    @Transactional
    public AsignacionMesa finalizarAsignacion(Long idAsignacion) {
        AsignacionMesa asignacion = asignacionRepository.findById(idAsignacion)
                .orElseThrow(() -> new ResourceNotFoundException("Asignación no encontrada"));

        if (!asignacion.getActivo()) {
            throw new RuntimeException("La asignación ya está finalizada");
        }

        // Finalizar asignación
        asignacion.setActivo(false);
        asignacion.setFechaHoraFin(LocalDateTime.now());

        // Liberar mesa
        Mesa mesa = asignacion.getMesa();
        mesa.setEstado(EstadoMesa.DISPONIBLE);
        mesaRepository.save(mesa);

        return asignacionRepository.save(asignacion);
    }

    public List<AsignacionMesa> getAsignacionesActivas() {
        return asignacionRepository.findByActivoTrue();
    }
}