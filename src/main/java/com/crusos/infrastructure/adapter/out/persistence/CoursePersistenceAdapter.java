package com.crusos.infrastructure.adapter.out.persistence;

import com.crusos.domain.model.Course;
import com.crusos.domain.port.out.CourseRepositoryPort;
import com.crusos.infrastructure.adapter.out.persistence.entity.CourseEntity;
import com.crusos.infrastructure.adapter.out.persistence.repository.JpaCourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CoursePersistenceAdapter implements CourseRepositoryPort {

    private final JpaCourseRepository jpaCourseRepository;
    private final CategoryPersistenceAdapter categoryAdapter;

    @Override
    public List<Course> findAll() {
        return jpaCourseRepository.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Course> findByCategoryId(Long categoryId) {
        return jpaCourseRepository.findByCategoryId(categoryId).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Course> findById(Long id) {
        return jpaCourseRepository.findById(id).map(this::toDomain);
    }

    @Override
    public Course save(Course course) {
        CourseEntity entity = toEntity(course);
        return toDomain(jpaCourseRepository.save(entity));
    }

    private Course toDomain(CourseEntity entity) {
        if (entity == null) return null;
        return Course.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .imageUrl(entity.getImageUrl())
                .courseUrl(entity.getCourseUrl())
                .category(categoryAdapter.toDomain(entity.getCategory()))
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    private CourseEntity toEntity(Course course) {
        if (course == null) return null;
        return CourseEntity.builder()
                .id(course.getId())
                .title(course.getTitle())
                .description(course.getDescription())
                .imageUrl(course.getImageUrl())
                .courseUrl(course.getCourseUrl())
                .category(categoryAdapter.toEntity(course.getCategory()))
                .createdAt(course.getCreatedAt())
                .updatedAt(course.getUpdatedAt())
                .build();
    }
}
