-- Script de creación de base de datos para PostgreSQL (Compatible con NeonDB, Supabase, etc.)

-- 1. Tabla de Categorías (Debe ir primero porque los cursos dependen de ella)
CREATE TABLE categories (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    slug VARCHAR(255) NOT NULL UNIQUE
);

-- 2. Tabla de Cursos
CREATE TABLE courses (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    image_url VARCHAR(255),
    course_url VARCHAR(255),
    category_id BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_active BOOLEAN NOT NULL DEFAULT true,
    CONSTRAINT fk_course_category FOREIGN KEY (category_id) REFERENCES categories (id) ON DELETE SET NULL
);

-- 3. Tabla de Administradores
CREATE TABLE admins (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL
);

-- 4. Tabla de Tokens Revocados (Lista Negra para el Logout)
CREATE TABLE revoked_tokens (
    id BIGSERIAL PRIMARY KEY,
    token VARCHAR(1000) NOT NULL UNIQUE,
    revoked_at TIMESTAMP NOT NULL
);

-- Opcional: Insertar una categoría de prueba
INSERT INTO categories (name, slug) VALUES ('Programación', 'programacion');

