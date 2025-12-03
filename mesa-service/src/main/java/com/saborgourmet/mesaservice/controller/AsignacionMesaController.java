package com.saborgourmet.mesaservice.controller;

import com.saborgourmet.mesaservice.dto.AsignacionMesaRequest;
import com.saborgourmet.mesaservice.entity.AsignacionMesa;
import com.saborgourmet.mesaservice.service.AsignacionMesaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/asignaciones")
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Asignaciones", description = "Asignación de clientes a mesas")
public class AsignacionMesaController {

    private final AsignacionMesaService asignacionService;

    public AsignacionMesaController(AsignacionMesaService asignacionService) {
        this.asignacionService = asignacionService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MOZO')")
    @Operation(summary = "Asignar mesa a cliente (Cambia estado mesa a OCUPADA)")
    public ResponseEntity<AsignacionMesa> asignarMesa(@Valid @RequestBody AsignacionMesaRequest request) {
        return new ResponseEntity<>(asignacionService.crearAsignacion(request), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}/finalizar")
    @PreAuthorize("hasAnyRole('ADMIN', 'MOZO', 'CAJERO')")
    @Operation(summary = "Liberar mesa y finalizar asignación")
    public ResponseEntity<AsignacionMesa> finalizarAsignacion(@PathVariable Long id) {
        return ResponseEntity.ok(asignacionService.finalizarAsignacion(id));
    }

    @GetMapping("/activas")
    @PreAuthorize("hasAnyRole('ADMIN', 'MOZO', 'CAJERO')")
    public ResponseEntity<List<AsignacionMesa>> getActivas() {
        return ResponseEntity.ok(asignacionService.getAsignacionesActivas());
    }
}