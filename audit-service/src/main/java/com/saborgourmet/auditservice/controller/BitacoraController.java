package com.saborgourmet.auditservice.controller;

import com.saborgourmet.auditservice.dto.BitacoraRequest;
import com.saborgourmet.auditservice.dto.BitacoraResponse;
import com.saborgourmet.auditservice.service.BitacoraService;
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
@RequestMapping("/api/bitacora")
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Bitácora", description = "Registro y consulta de auditoría")
public class BitacoraController {

    private final BitacoraService bitacoraService;

    public BitacoraController(BitacoraService bitacoraService) {
        this.bitacoraService = bitacoraService;
    }

    @PostMapping
    @Operation(summary = "Registrar un nuevo evento (Uso interno por microservicios)")
    public ResponseEntity<BitacoraResponse> registrarEvento(@Valid @RequestBody BitacoraRequest request) {
        // En un entorno real, este endpoint podría estar protegido para que solo
        // otros microservicios (con un token de sistema o red privada) puedan escribir.
        return new ResponseEntity<>(bitacoraService.registrarEvento(request), HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Listar toda la bitácora")
    public ResponseEntity<List<BitacoraResponse>> getAll() {
        return ResponseEntity.ok(bitacoraService.getAllEventos());
    }

    @GetMapping("/usuario/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Filtrar eventos por usuario")
    public ResponseEntity<List<BitacoraResponse>> getByUsername(@PathVariable String username) {
        return ResponseEntity.ok(bitacoraService.getEventosByUsername(username));
    }
}