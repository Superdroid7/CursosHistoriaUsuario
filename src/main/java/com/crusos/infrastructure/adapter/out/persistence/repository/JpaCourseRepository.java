package com.crusos.infrastructure.adapter.out.persistence.repository;

import com.crusos.infrastructure.adapter.out.persistence.entity.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaCourseRepository extends JpaRepository<CourseEntity, Long> {
    List<CourseEntity> findByIsActiveTrue();
    List<CourseEntity> findByCategoryIdAndIsActiveTrue(Long categoryId);
    Optional<CourseEntity> findByIdAndIsActiveTrue(Long id);
}
