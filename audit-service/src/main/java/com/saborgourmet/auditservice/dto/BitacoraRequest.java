package com.saborgourmet.auditservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BitacoraRequest {

    @NotBlank(message = "El username es obligatorio")
    private String username;

    @NotBlank(message = "La acción es obligatoria")
    private String accion;

    @NotBlank(message = "La entidad es obligatoria")
    private String entidad;

    private String entidadId;
    private String detalle;
}