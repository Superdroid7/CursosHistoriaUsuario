package com.crusos.application.usecase;

import com.crusos.domain.model.Category;
import com.crusos.domain.model.Course;
import com.crusos.domain.port.out.CategoryRepositoryPort;
import com.crusos.domain.port.out.CourseRepositoryPort;
import com.crusos.infrastructure.adapter.in.web.dto.CategoryRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminCategoryUseCase {

    private final CategoryRepositoryPort categoryRepositoryPort;
    private final CourseRepositoryPort courseRepositoryPort;

    public Category createCategory(CategoryRequest request) {
        Category category = Category.builder()
                .name(request.getName())
                .build();
        return categoryRepositoryPort.save(category);
    }

    public Category updateCategory(Long id, CategoryRequest request) {
        Category existingCategory = categoryRepositoryPort.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada"));

        existingCategory.setName(request.getName());
        return categoryRepositoryPort.save(existingCategory);
    }

    public void deleteCategory(Long id) {
        categoryRepositoryPort.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada"));
        List<Course> courses = courseRepositoryPort.findByCategoryId(id);
        if (!courses.isEmpty()) {
            throw new IllegalStateException("No se puede eliminar la categoría porque tiene cursos asociados.");
        }

        categoryRepositoryPort.deleteById(id);
    }

    public List<Category> getAllCategoriesAdmin() {
        return categoryRepositoryPort.findAll();
    }
}
