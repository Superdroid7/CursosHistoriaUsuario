package com.crusos.application.usecase;

import com.crusos.domain.model.Category;
import com.crusos.domain.model.Course;
import com.crusos.domain.port.out.CategoryRepositoryPort;
import com.crusos.domain.port.out.CourseRepositoryPort;
import com.crusos.domain.port.out.FileStoragePort;
import com.crusos.infrastructure.adapter.in.web.dto.CourseRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminCourseUseCase {

    private final CourseRepositoryPort courseRepositoryPort;
    private final CategoryRepositoryPort categoryRepositoryPort;
    private final FileStoragePort fileStoragePort;

    public Course createCourse(CourseRequest request) {
        Category category = categoryRepositoryPort.findById(request.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada"));

        Course course = Course.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .imageUrl(request.getImageUrl())
                .courseUrl(request.getCourseUrl())
                .category(category)
                .isActive(true)
                .build();

        return courseRepositoryPort.save(course);
    }

    public Course updateCourse(Long id, CourseRequest request) {
        Course existingCourse = courseRepositoryPort.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Curso no encontrado"));

        Category category = categoryRepositoryPort.findById(request.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada"));

        existingCourse.setTitle(request.getTitle());
        existingCourse.setDescription(request.getDescription());
        existingCourse.setImageUrl(request.getImageUrl());
        existingCourse.setCourseUrl(request.getCourseUrl());
        existingCourse.setCategory(category);

        return courseRepositoryPort.save(existingCourse);
    }

    public void deleteCourse(Long id) {
        Course existingCourse = courseRepositoryPort.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Curso no encontrado"));
        existingCourse.setIsActive(false);
        courseRepositoryPort.save(existingCourse);
    }

    public List<Course> getAllCoursesAdmin() {
        return courseRepositoryPort.findAllAdmin();
    }

    public Course uploadCourseImage(Long id, MultipartFile file) {
        Course existingCourse = courseRepositoryPort.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Curso no encontrado"));

        String fileUrl = fileStoragePort.storeFile(file);
        existingCourse.setImageUrl(fileUrl);
        
        return courseRepositoryPort.save(existingCourse);
    }
}
