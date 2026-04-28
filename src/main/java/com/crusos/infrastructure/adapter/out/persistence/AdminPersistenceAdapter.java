package com.crusos.infrastructure.adapter.out.persistence;

import com.crusos.domain.model.Admin;
import com.crusos.domain.port.out.AdminRepositoryPort;
import com.crusos.infrastructure.adapter.out.persistence.entity.AdminEntity;
import com.crusos.infrastructure.adapter.out.persistence.repository.JpaAdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AdminPersistenceAdapter implements AdminRepositoryPort {

    private final JpaAdminRepository jpaAdminRepository;

    @Override
    public Optional<Admin> findByEmail(String email) {
        return jpaAdminRepository.findByEmail(email).map(this::toDomain);
    }

    @Override
    public Admin save(Admin admin) {
        AdminEntity entity = toEntity(admin);
        return toDomain(jpaAdminRepository.save(entity));
    }

    private Admin toDomain(AdminEntity entity) {
        if (entity == null) return null;
        return Admin.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .passwordHash(entity.getPasswordHash())
                .role(entity.getRole())
                .build();
    }

    private AdminEntity toEntity(Admin admin) {
        if (admin == null) return null;
        return AdminEntity.builder()
                .id(admin.getId())
                .email(admin.getEmail())
                .passwordHash(admin.getPasswordHash())
                .role(admin.getRole())
                .build();
    }
}
