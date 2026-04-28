package com.crusos.application.usecase;

import com.crusos.domain.model.Category;
import com.crusos.domain.port.out.CategoryRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryUseCase {

    private final CategoryRepositoryPort categoryRepositoryPort;

    public List<Category> getAllCategories() {
        return categoryRepositoryPort.findAll();
    }
}
