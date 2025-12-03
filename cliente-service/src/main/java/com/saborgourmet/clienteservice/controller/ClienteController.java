package com.saborgourmet.clienteservice.controller;

import com.saborgourmet.clienteservice.dto.ClienteRequest;
import com.saborgourmet.clienteservice.dto.ClienteResponse;
import com.saborgourmet.clienteservice.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clientes")
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Clientes", description = "Gestión de comensales")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    @Operation(summary = "Listar clientes paginados (Solo activos)")
    @PreAuthorize("hasAnyRole('ADMIN', 'MOZO', 'CAJERO')")
    public ResponseEntity<Page<ClienteResponse>> getAll(Pageable pageable) {
        return ResponseEntity.ok(clienteService.getAllClientes(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener cliente por ID")
    @PreAuthorize("hasAnyRole('ADMIN', 'MOZO', 'CAJERO')")
    public ResponseEntity<ClienteResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(clienteService.getClienteById(id));
    }

    @GetMapping("/dni/{dni}")
    @Operation(summary = "Buscar cliente por DNI")
    @PreAuthorize("hasAnyRole('ADMIN', 'MOZO', 'CAJERO')")
    public ResponseEntity<ClienteResponse> getByDni(@PathVariable String dni) {
        return ResponseEntity.ok(clienteService.getClienteByDni(dni));
    }

    @PostMapping
    @Operation(summary = "Crear nuevo cliente")
    @PreAuthorize("hasAnyRole('ADMIN', 'MOZO')")
    public ResponseEntity<ClienteResponse> create(@Valid @RequestBody ClienteRequest request) {
        return new ResponseEntity<>(clienteService.createCliente(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar cliente")
    @PreAuthorize("hasAnyRole('ADMIN', 'MOZO')")
    public ResponseEntity<ClienteResponse> update(@PathVariable Long id, @Valid @RequestBody ClienteRequest request) {
        return ResponseEntity.ok(clienteService.updateCliente(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar cliente (Soft Delete)")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        clienteService.deleteCliente(id);
        return ResponseEntity.noContent().build();
    }
}