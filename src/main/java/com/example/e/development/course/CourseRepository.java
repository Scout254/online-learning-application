package com.example.e.development.course;

import com.example.e.development.course.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> findByEnrollmentsUserId(Long userId);
}
