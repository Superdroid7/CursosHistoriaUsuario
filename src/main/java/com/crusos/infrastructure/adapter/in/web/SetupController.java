package com.crusos.infrastructure.adapter.in.web;

import com.crusos.infrastructure.adapter.out.persistence.entity.AdminEntity;
import com.crusos.infrastructure.adapter.out.persistence.repository.JpaAdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * CONTROLADOR TEMPORAL DE SETUP - SOLO PARA DESARROLLO
 * Eliminar antes de subir a producción.
 * Crea el primer administrador en la base de datos con el password correctamente encriptado.
 */
@RestController
@RequestMapping("/api/setup")
@RequiredArgsConstructor
public class SetupController {

    private final JpaAdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/admin")
    public ResponseEntity<String> createFirstAdmin(
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam(defaultValue = "ADMIN") String role
    ) {
        if (adminRepository.count() > 0) {
            return ResponseEntity.badRequest()
                    .body("Ya existe un administrador. Elimina este endpoint del código.");
        }

        AdminEntity admin = AdminEntity.builder()
                .email(email)
                .passwordHash(passwordEncoder.encode(password))
                .role(role)
                .build();

        adminRepository.save(admin);

        return ResponseEntity.ok(
                "Admin creado exitosamente.\n" +
                "Email: " + email + "\n" +
                "Ahora puedes hacer login en /api/auth/login\n" +
                "IMPORTANTE: Elimina SetupController.java del proyecto después de hacer login."
        );
    }
}
