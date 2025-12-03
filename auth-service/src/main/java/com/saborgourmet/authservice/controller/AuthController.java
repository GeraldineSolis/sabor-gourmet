package com.saborgourmet.authservice.controller;

import com.saborgourmet.authservice.config.JwtUtil;
import com.saborgourmet.authservice.dto.AuthRequest;
import com.saborgourmet.authservice.dto.AuthResponse;
import com.saborgourmet.authservice.dto.RegisterRequest;
import com.saborgourmet.authservice.entity.Usuario;
import com.saborgourmet.authservice.service.AuthService;
import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticación", description = "Endpoints de Login, Registro y Validación de Tokens")
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthService authService, JwtUtil jwtUtil) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    @Operation(summary = "Inicia sesión y genera un token JWT")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    @Operation(summary = "Registra un nuevo usuario (solo para pruebas iniciales o ADMIN)")
    public ResponseEntity<Usuario> register(@Valid @RequestBody RegisterRequest request) {
        Usuario usuario = authService.register(request);
        return new ResponseEntity<>(usuario, HttpStatus.CREATED);
    }

    @GetMapping("/validate")
    @Operation(summary = "Valida un token JWT para otros microservicios")
    public ResponseEntity<Map<String, Object>> validateToken(@RequestHeader("Authorization") String tokenHeader) {
        if (tokenHeader == null || !tokenHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String token = tokenHeader.substring(7);

        try {
            Claims claims = jwtUtil.validateToken(token);
            // Convertir claims a Map<String, Object> para la respuesta
            Map<String, Object> responseClaims = claims;
            responseClaims.put("isValid", true);
            return ResponseEntity.ok(responseClaims);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}