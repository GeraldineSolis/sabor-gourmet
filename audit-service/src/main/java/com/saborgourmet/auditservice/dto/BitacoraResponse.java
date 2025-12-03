package com.saborgourmet.auditservice.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class BitacoraResponse {
    private Long id;
    private String username;
    private String accion;
    private String entidad;
    private String entidadId;
    private LocalDateTime fechaHora;
    private String detalle;
}