package com.crusos.infrastructure.adapter.in.web;

import com.crusos.application.usecase.AdminCourseUseCase;
import com.crusos.domain.model.Course;
import com.crusos.infrastructure.adapter.in.web.dto.ApiResponse;
import com.crusos.infrastructure.adapter.in.web.dto.CourseRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseEntity<ApiResponse<List<Course>>> getAllCoursesAdmin() {
        return ResponseEntity.ok(new ApiResponse<>("Lista de cursos obtenida exitosamente", adminCourseUseCase.getAllCoursesAdmin()));
    }

    @PostMapping
    @Operation(summary = "Crear curso", description = "Crea un nuevo curso.")
    public ResponseEntity<ApiResponse<Course>> createCourse(@Valid @RequestBody CourseRequest request) {
        Course created = adminCourseUseCase.createCourse(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>("Curso creado exitosamente", created));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Editar curso", description = "Actualiza un curso existente.")
    public ResponseEntity<ApiResponse<Course>> updateCourse(@PathVariable Long id, @Valid @RequestBody CourseRequest request) {
        Course updated = adminCourseUseCase.updateCourse(id, request);
        return ResponseEntity.ok(new ApiResponse<>("Curso actualizado exitosamente", updated));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar curso (Borrado lógico)", description = "Desactiva un curso sin borrarlo de la BD.")
    public ResponseEntity<ApiResponse<Void>> deleteCourse(@PathVariable Long id) {
        adminCourseUseCase.deleteCourse(id);
        return ResponseEntity.ok(new ApiResponse<>("Curso eliminado (ocultado) exitosamente", null));
    }

    @PostMapping(value = "/{id}/image", consumes = "multipart/form-data")
    @Operation(summary = "Subir imagen al curso", description = "Sube un archivo PNG/JPG y actualiza la URL de imagen del curso.")
    public ResponseEntity<ApiResponse<Course>> uploadCourseImage(
            @PathVariable Long id, 
            @RequestParam("file") MultipartFile file) {
        Course updated = adminCourseUseCase.uploadCourseImage(id, file);
        return ResponseEntity.ok(new ApiResponse<>("Imagen subida y enlazada exitosamente", updated));
    }
}
