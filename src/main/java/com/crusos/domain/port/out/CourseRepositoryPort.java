package com.crusos.domain.port.out;

import com.crusos.domain.model.Course;
import java.util.List;
import java.util.Optional;

public interface CourseRepositoryPort {
    List<Course> findAllActive();
    List<Course> findAllAdmin();
    List<Course> findByCategoryId(Long categoryId);
    Optional<Course> findById(Long id);
    Course save(Course course);
}
