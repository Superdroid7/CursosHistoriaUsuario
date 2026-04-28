package com.crusos.infrastructure.adapter.in.web;

import com.crusos.application.usecase.AdminCourseUseCase;
import com.crusos.domain.model.Course;
import com.crusos.infrastructure.adapter.in.web.dto.CourseRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/courses")
@RequiredArgsConstructor
@Tag(name = "Admin Courses", description = "CRUD de cursos para administradores")
@SecurityRequirement(name = "bearerAuth")
public class AdminCourseController {

    private final AdminCourseUseCase adminCourseUseCase;

    @GetMapping
    @Operation(summary = "Listar todos los cursos (Admin)", description = "Obtiene todos los cursos, activos e inactivos.")
    public ResponseEntity<List<Course>> getAllCoursesAdmin() {
        return ResponseEntity.ok(adminCourseUseCase.getAllCoursesAdmin());
    }

    @PostMapping
    @Operation(summary = "Crear curso", description = "Crea un nuevo curso.")
    public ResponseEntity<Course> createCourse(@Valid @RequestBody CourseRequest request) {
        Course created = adminCourseUseCase.createCourse(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Editar curso", description = "Actualiza un curso existente.")
    public ResponseEntity<Course> updateCourse(@PathVariable Long id, @Valid @RequestBody CourseRequest request) {
        Course updated = adminCourseUseCase.updateCourse(id, request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar curso (Borrado lógico)", description = "Desactiva un curso sin borrarlo de la BD.")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        adminCourseUseCase.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }
}
