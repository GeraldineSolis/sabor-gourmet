package com.saborgourmet.authservice.service;

// ¡IMPORTANTE! Asegúrate de que este import sea correcto:
import com.saborgourmet.authservice.dto.AuthRequest;
import com.saborgourmet.authservice.dto.AuthResponse;
import com.saborgourmet.authservice.dto.RegisterRequest;
import com.saborgourmet.authservice.entity.Usuario; // <--- NO auditservice
import com.saborgourmet.authservice.exception.UsernameAlreadyExistsException;
import com.saborgourmet.authservice.repository.UsuarioRepository;
import com.saborgourmet.authservice.config.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    public AuthResponse login(AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        Usuario usuario = usuarioRepository.findByUsername(request.getUsername()).get();

        Map<String, Object> claims = new HashMap<>();
        claims.put("rol", usuario.getRol().name());
        claims.put("idUsuario", usuario.getId());
        // Aquí es donde te daba error antes si la entidad estaba mal:
        claims.put("nombreCompleto", usuario.getNombreCompleto());

        String token = jwtUtil.generateToken(usuario.getUsername(), claims);

        return AuthResponse.builder()
                .token(token)
                .userId(usuario.getId())
                .username(usuario.getUsername())
                .rol(usuario.getRol().name())
                .nombreCompleto(usuario.getNombreCompleto())
                .build();
    }

    public Usuario register(RegisterRequest request) {
        if (usuarioRepository.existsByUsername(request.getUsername())) {
            throw new UsernameAlreadyExistsException("El username '" + request.getUsername() + "' ya está registrado.");
        }

        Usuario usuario = Usuario.builder()
                .nombreCompleto(request.getNombreCompleto())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .rol(request.getRol())
                .build();

        return usuarioRepository.save(usuario);
    }
}