package com.example.e.development.repository;

import com.example.e.development.models.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    boolean existsByUser_IdAndCourse_Id(Long userId, Long courseId);
}
