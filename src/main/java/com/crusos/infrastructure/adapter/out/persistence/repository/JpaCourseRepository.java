package com.crusos.infrastructure.adapter.out.persistence.repository;

import com.crusos.infrastructure.adapter.out.persistence.entity.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaCourseRepository extends JpaRepository<CourseEntity, Long> {
    List<CourseEntity> findByCategoryId(Long categoryId);
}
