package com.crusos.infrastructure.adapter.in.web;

import com.crusos.application.usecase.CategoryUseCase;
import com.crusos.application.usecase.GetPublicCoursesUseCase;
import com.crusos.domain.model.Category;
import com.crusos.domain.model.Course;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public")
@RequiredArgsConstructor
@Tag(name = "Public Access", description = "Endpoints públicos para la landing page")
public class PublicCourseController {

    private final GetPublicCoursesUseCase getPublicCoursesUseCase;
    private final CategoryUseCase categoryUseCase;

    @GetMapping("/courses")
    @Operation(summary = "Obtener lista de cursos", description = "Retorna todos los cursos, opcionalmente filtrados por categoría")
    public ResponseEntity<List<Course>> getCourses(
            @RequestParam(value = "categoryId", required = false) Long categoryId) {
        
        List<Course> courses;
        if (categoryId != null) {
            courses = getPublicCoursesUseCase.getCoursesByCategory(categoryId);
        } else {
            courses = getPublicCoursesUseCase.getAllCourses();
        }
        
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/categories")
    @Operation(summary = "Obtener lista de categorías", description = "Retorna todas las categorías disponibles para el filtro")
    public ResponseEntity<List<Category>> getCategories() {
        return ResponseEntity.ok(categoryUseCase.getAllCategories());
    }
}
