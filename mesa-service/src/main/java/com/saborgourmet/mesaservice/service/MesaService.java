package com.saborgourmet.mesaservice.service;

import com.saborgourmet.mesaservice.dto.MesaRequest;
import com.saborgourmet.mesaservice.entity.EstadoMesa;
import com.saborgourmet.mesaservice.entity.Mesa;
import com.saborgourmet.mesaservice.exception.NumeroMesaAlreadyExistsException;
import com.saborgourmet.mesaservice.exception.ResourceNotFoundException;
import com.saborgourmet.mesaservice.repository.MesaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class MesaService {

    private final MesaRepository mesaRepository;

    public MesaService(MesaRepository mesaRepository) {
        this.mesaRepository = mesaRepository;
    }

    public List<Mesa> getAllMesas() {
        return mesaRepository.findAll();
    }

    public List<Mesa> getMesasDisponibles() {
        return mesaRepository.findByEstado(EstadoMesa.DISPONIBLE);
    }

    @Transactional
    public Mesa createMesa(MesaRequest request) {
        if (mesaRepository.existsByNumeroMesa(request.getNumeroMesa())) {
            throw new NumeroMesaAlreadyExistsException("El número de mesa " + request.getNumeroMesa() + " ya existe");
        }

        Mesa mesa = Mesa.builder()
                .numeroMesa(request.getNumeroMesa())
                .capacidad(request.getCapacidad())
                .ubicacion(request.getUbicacion())
                .estado(EstadoMesa.DISPONIBLE)
                .build();

        return mesaRepository.save(mesa);
    }

    public Mesa getMesaById(Long id) {
        return mesaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mesa no encontrada"));
    }

    @Transactional
    public Mesa updateEstado(Long id, EstadoMesa nuevoEstado) {
        Mesa mesa = getMesaById(id);
        mesa.setEstado(nuevoEstado);
        return mesaRepository.save(mesa);
    }
}