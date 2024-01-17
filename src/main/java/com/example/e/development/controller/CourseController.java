package com.example.e.development.controller;

import com.example.e.development.dto.CourseRequest;
import com.example.e.development.models.Course;
import com.example.e.development.models.CourseType;
import com.example.e.development.models.Module;
import com.example.e.development.models.Quiz;
import com.example.e.development.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping("/create/{courseType}")
    public ResponseEntity<String> createCourseWithType(
            @RequestBody CourseRequest courseRequest,
            @PathVariable CourseType courseType) {

        Course course = new Course();
        course.setTitle(courseRequest.getTitle());
        course.setPrice(courseRequest.getPrice());
        course.setCourseType(courseType);

        courseService.createCourse(course);

        return ResponseEntity.ok(courseType + " course created successfully");
    }
    @GetMapping()
    public ResponseEntity<List<Course>> getAllCourses() {
        // Retrieve all courses using the CourseService
        List<Course> courses = courseService.getAllCourses();

        // Return the list of courses in the response
        return ResponseEntity.ok(courses);
    }
    @GetMapping("/{courseId}/modules")
    public ResponseEntity<List<Module>> getModulesForCourse(@PathVariable Long courseId) {
        List<Module> modules = courseService.getModulesForCourse(courseId);
        return new ResponseEntity<>(modules, HttpStatus.OK);
    }

    //
    @GetMapping("/{courseId}/quizzes")
    public ResponseEntity<List<Quiz>> getQuizForCourse(@PathVariable Long courseId){
        List<Quiz> quizzes = courseService.getQuizForCourse(courseId);
        return new ResponseEntity<>(quizzes, HttpStatus.OK);
    }
}
