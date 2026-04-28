package com.crusos.application.usecase;

import com.crusos.domain.model.Course;
import com.crusos.domain.port.out.CourseRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetPublicCoursesUseCase {

    private final CourseRepositoryPort courseRepositoryPort;

    public List<Course> getAllCourses() {
        return courseRepositoryPort.findAllActive();
    }

    public List<Course> getCoursesByCategory(Long categoryId) {
        return courseRepositoryPort.findByCategoryId(categoryId);
    }
}
