package com.saborgourmet.mesaservice.controller;

import com.saborgourmet.mesaservice.dto.MesaRequest;
import com.saborgourmet.mesaservice.entity.EstadoMesa;
import com.saborgourmet.mesaservice.entity.Mesa;
import com.saborgourmet.mesaservice.service.MesaService;
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
@RequestMapping("/api/mesas")
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Mesas", description = "Gestión física de mesas")
public class MesaController {

    private final MesaService mesaService;

    public MesaController(MesaService mesaService) {
        this.mesaService = mesaService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MOZO', 'CAJERO')")
    public ResponseEntity<List<Mesa>> getAll() {
        return ResponseEntity.ok(mesaService.getAllMesas());
    }

    @GetMapping("/disponibles")
    @PreAuthorize("hasAnyRole('ADMIN', 'MOZO')")
    public ResponseEntity<List<Mesa>> getDisponibles() {
        return ResponseEntity.ok(mesaService.getMesasDisponibles());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Registrar nueva mesa")
    public ResponseEntity<Mesa> create(@Valid @RequestBody MesaRequest request) {
        return new ResponseEntity<>(mesaService.createMesa(request), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}/estado")
    @PreAuthorize("hasAnyRole('ADMIN', 'MOZO')")
    @Operation(summary = "Cambiar estado manualmente (Mantenimiento/Reservada)")
    public ResponseEntity<Mesa> updateEstado(@PathVariable Long id, @RequestParam EstadoMesa estado) {
        return ResponseEntity.ok(mesaService.updateEstado(id, estado));
    }
}