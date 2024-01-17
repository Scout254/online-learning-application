package com.example.e.development.service;

import com.example.e.development.models.Course;
import com.example.e.development.models.Module;
import com.example.e.development.repository.CourseRepository;
import com.example.e.development.repository.ModuleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModuleService {
    @Autowired
    private ModuleRepository moduleRepository;

    @Autowired
    private CourseRepository courseRepository;

    public Module createModule(Long courseId, Module module) {
        // Find the course by ID
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + courseId));

        // Set the course for the module
        module.setCourse(course);

        // Save the module within the context of the course
        moduleRepository.save(module);

        return module;
    }


}