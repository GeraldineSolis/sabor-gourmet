package com.saborgourmet.auditservice.controller;

import com.saborgourmet.auditservice.entity.Usuario;
import com.saborgourmet.auditservice.repository.UsuarioRepository;
import com.saborgourmet.auditservice.exception.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Usuarios", description = "Gestión de Usuarios (Requiere Rol ADMIN)")
@PreAuthorize("hasRole('ADMIN')") // Aplicar seguridad a nivel de clase
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;

    public UsuarioController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    @Operation(summary = "Lista todos los usuarios (con paginación)")
    public ResponseEntity<Page<Usuario>> getAllUsuarios(Pageable pageable) {
        return ResponseEntity.ok(usuarioRepository.findAll(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtiene un usuario por ID")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + id));
        return ResponseEntity.ok(usuario);
    }
}