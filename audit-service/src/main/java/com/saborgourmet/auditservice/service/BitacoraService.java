package com.saborgourmet.auditservice.service;

import com.saborgourmet.auditservice.dto.BitacoraRequest;
import com.saborgourmet.auditservice.dto.BitacoraResponse;
import com.saborgourmet.auditservice.entity.Bitacora;
import com.saborgourmet.auditservice.repository.BitacoraRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BitacoraService {

    private final BitacoraRepository bitacoraRepository;

    public BitacoraService(BitacoraRepository bitacoraRepository) {
        this.bitacoraRepository = bitacoraRepository;
    }

    @Transactional
    public BitacoraResponse registrarEvento(BitacoraRequest request) {
        Bitacora bitacora = Bitacora.builder()
                .username(request.getUsername())
                .accion(request.getAccion())
                .entidad(request.getEntidad())
                .entidadId(request.getEntidadId())
                .detalle(request.getDetalle())
                .fechaHora(LocalDateTime.now())
                .build();

        Bitacora saved = bitacoraRepository.save(bitacora);
        return mapToResponse(saved);
    }

    public List<BitacoraResponse> getAllEventos() {
        return bitacoraRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<BitacoraResponse> getEventosByUsername(String username) {
        return bitacoraRepository.findByUsername(username).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private BitacoraResponse mapToResponse(Bitacora bitacora) {
        return BitacoraResponse.builder()
                .id(bitacora.getId())
                .username(bitacora.getUsername())
                .accion(bitacora.getAccion())
                .entidad(bitacora.getEntidad())
                .entidadId(bitacora.getEntidadId())
                .fechaHora(bitacora.getFechaHora())
                .detalle(bitacora.getDetalle())
                .build();
    }
}