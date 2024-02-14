package com.example.e.development.enrollment;

import com.example.e.development.enrollment.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    boolean existsByUser_IdAndCourse_Id(Long userId, Long courseId);
}
