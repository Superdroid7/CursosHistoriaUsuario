package com.crusos.infrastructure.adapter.in.web;

import com.crusos.application.usecase.AuthUseCase;
import com.crusos.infrastructure.adapter.in.web.dto.AuthResponse;
import com.crusos.infrastructure.adapter.in.web.dto.LoginRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Endpoints de autenticación para administradores")
public class AuthController {

    private final AuthUseCase authUseCase;

    @PostMapping("/login")
    @Operation(summary = "Iniciar Sesión", description = "Recibe email y password, retorna un token JWT válido por 24 horas.")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        String token = authUseCase.login(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/logout")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Cerrar Sesión", description = "Invalida el token actual enviándolo a la lista negra.")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            authUseCase.logout(authHeader);
        }
        return ResponseEntity.noContent().build();
    }
}
