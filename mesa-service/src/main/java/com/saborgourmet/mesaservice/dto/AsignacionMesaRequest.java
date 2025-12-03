package com.saborgourmet.mesaservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AsignacionMesaRequest {
    @NotNull(message = "El ID de la mesa es obligatorio")
    private Long idMesa;

    @NotNull(message = "El ID del cliente es obligatorio")
    private Long idCliente;

    @NotNull(message = "La cantidad de comensales es obligatoria")
    @Min(value = 1, message = "Debe haber al menos 1 comensal")
    private Integer cantidadComensales;
}