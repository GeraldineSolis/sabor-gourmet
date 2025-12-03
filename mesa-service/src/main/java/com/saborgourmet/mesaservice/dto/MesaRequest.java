package com.saborgourmet.mesaservice.dto;

import com.saborgourmet.mesaservice.entity.EstadoMesa;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MesaRequest {
    @NotBlank(message = "El número de mesa es obligatorio")
    private String numeroMesa;

    @NotNull(message = "La capacidad es obligatoria")
    @Min(value = 1, message = "La capacidad mínima es 1")
    private Integer capacidad;

    @NotBlank(message = "La ubicación es obligatoria")
    private String ubicacion;

    // Opcional en creación, obligatorio en actualización
    private EstadoMesa estado;
}