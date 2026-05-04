package com.crusos;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GenerateHash {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "admin123";
        String hash = encoder.encode(rawPassword);
        System.out.println("===========================================");
        System.out.println("Password: " + rawPassword);
        System.out.println("BCrypt Hash: " + hash);
        System.out.println("===========================================");
        System.out.println("SQL para NeonDB:");
        System.out.println("INSERT INTO admins (email, password_hash, role)");
        System.out.println("VALUES ('admin@iascursos.com', '" + hash + "', 'ADMIN');");
    }
}
