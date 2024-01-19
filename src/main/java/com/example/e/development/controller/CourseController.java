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
        course.setCourseTitle(courseRequest.getCourseTitle());
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
    @PostMapping("/{courseId}/modules/{moduleId}/complete")
    public ResponseEntity<String> completeModule(@PathVariable Long courseId, @PathVariable Long moduleId) {
        courseService.completeModule(moduleId);
        return ResponseEntity.ok("Module marked as completed");
    }

    @PostMapping("/{courseId}/quizzes/{quizId}/complete")
    public ResponseEntity<String> completeQuiz(@PathVariable Long courseId, @PathVariable Long quizId) {
        courseService.completeQuiz(quizId);
        return ResponseEntity.ok("Quiz marked as completed");
    }
    @PutMapping("/edit/{courseId}")
    public ResponseEntity<Course> editCourse(@PathVariable Long courseId, @RequestBody Course course){
        Course updatedCourse = courseService.editCourse(courseId, course);
        return new ResponseEntity<>(updatedCourse, HttpStatus.OK);
    }


    @DeleteMapping("/delete/{courseId}")
    public ResponseEntity<String> deleteCourse(@PathVariable Long courseId){

        boolean isDeleted = courseService.deleteCourse(courseId);

        if (isDeleted){
            return ResponseEntity.ok("successfully deleted!");
        }else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/{courseId}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long courseId){
        try{
            Course course = courseService.getCourseById(courseId);
            return ResponseEntity.ok(course);
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

}
