package com.crusos.domain.port.out;

import com.crusos.domain.model.Admin;
import java.util.Optional;

public interface AdminRepositoryPort {
    Optional<Admin> findByEmail(String email);
    Admin save(Admin admin);
}
