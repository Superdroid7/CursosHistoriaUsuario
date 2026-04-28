package com.crusos.infrastructure.config;

import com.crusos.domain.model.Admin;
import com.crusos.domain.port.out.AdminRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final AdminRepositoryPort adminRepositoryPort;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        String defaultEmail = "admin@ias.com";
        Optional<Admin> existingAdmin = adminRepositoryPort.findByEmail(defaultEmail);
        
        if (existingAdmin.isEmpty()) {
            Admin defaultAdmin = Admin.builder()
                    .email(defaultEmail)
                    .passwordHash(passwordEncoder.encode("admin123"))
                    .role("ADMIN")
                    .build();
            adminRepositoryPort.save(defaultAdmin);
            System.out.println("✅ Administrador por defecto creado: " + defaultEmail + " / admin123");
        }
    }
}
