package com.crusos.infrastructure.adapter.in.web;

import com.crusos.application.usecase.AdminCategoryUseCase;
import com.crusos.domain.model.Category;
import com.crusos.infrastructure.adapter.in.web.dto.ApiResponse;
import com.crusos.infrastructure.adapter.in.web.dto.CategoryRequest;
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
@RequestMapping("/api/admin/categories")
@RequiredArgsConstructor
@Tag(name = "Admin Categories", description = "CRUD de categorías para administradores")
@SecurityRequirement(name = "bearerAuth")
public class AdminCategoryController {

    private final AdminCategoryUseCase adminCategoryUseCase;

    @GetMapping
    @Operation(summary = "Listar todas las categorías", description = "Obtiene todas las categorías.")
    public ResponseEntity<ApiResponse<List<Category>>> getAllCategoriesAdmin() {
        return ResponseEntity.ok(new ApiResponse<>("Lista de categorías obtenida exitosamente", adminCategoryUseCase.getAllCategoriesAdmin()));
    }

    @PostMapping
    @Operation(summary = "Crear categoría", description = "Crea una nueva categoría.")
    public ResponseEntity<ApiResponse<Category>> createCategory(@Valid @RequestBody CategoryRequest request) {
        Category created = adminCategoryUseCase.createCategory(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>("Categoría creada exitosamente", created));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Editar categoría", description = "Actualiza una categoría existente.")
    public ResponseEntity<ApiResponse<Category>> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryRequest request) {
        Category updated = adminCategoryUseCase.updateCategory(id, request);
        return ResponseEntity.ok(new ApiResponse<>("Categoría actualizada exitosamente", updated));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar categoría (Borrado físico)", description = "Elimina una categoría. Falla si la categoría tiene cursos asociados.")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(@PathVariable Long id) {
        try {
            adminCategoryUseCase.deleteCategory(id);
            return ResponseEntity.ok(new ApiResponse<>("Categoría eliminada permanentemente con éxito", null));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse<>(e.getMessage(), null));
        }
    }
}
