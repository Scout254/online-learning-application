package com.example.e.development.service;

import com.example.e.development.models.Course;
import com.example.e.development.models.Module;
import com.example.e.development.models.Quiz;
import com.example.e.development.repository.CourseRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public void createCourse(Course course) {

        // Save the course to the database
        courseRepository.save(course);
    }
    public List<Course> getAllCourses() {
        // Retrieve all courses from the database
        return courseRepository.findAll();
    }

    public List<Quiz> getQuizForCourse(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(()-> new EntityNotFoundException("Course not found with id:" + courseId));
        return course.getQuizzes();
    }

    public List<Module> getModulesForCourse(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + courseId));
        return course.getModules();
    }
}
