package com.example.e.development.service;

import com.example.e.development.models.Course;
import com.example.e.development.models.Module;
import com.example.e.development.models.Quiz;
import com.example.e.development.repository.CourseRepository;
import com.example.e.development.repository.ModuleRepository;
import com.example.e.development.repository.QuizRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final ModuleRepository moduleRepository;
    private final QuizRepository quizRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository, ModuleRepository moduleRepository, QuizRepository quizRepository) {
        this.courseRepository = courseRepository;
        this.moduleRepository = moduleRepository;
        this.quizRepository = quizRepository;
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
    public void completeModule(Long moduleId) {
        Module module = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new EntityNotFoundException("Module not found with id: " + moduleId));
        module.setCompleted(true);
        courseRepository.save(module.getCourse());
    }

    public void completeQuiz(Long quizId) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new EntityNotFoundException("Quiz not found with id: " + quizId));
        quiz.setCompleted(true);
        courseRepository.save(quiz.getCourse());
    }

    public Course editCourse(Long courseId, Course course) {
        Course existingCourse = courseRepository.findById(courseId).orElse(null);
        if (existingCourse != null){
            existingCourse.setCourseTitle(course.getCourseTitle());
            existingCourse.setPrice(course.getPrice());
            existingCourse.setCourseDescription(course.getCourseDescription());
            existingCourse.setCourseDuration(course.getCourseDuration());
            existingCourse.setCourseCategory(course.getCourseCategory());
            existingCourse.setCourseType(course.getCourseType());
            return courseRepository.save(existingCourse);
        }
        return null;
    }

    public Course getCourseById(Long courseId){
        return courseRepository.findById(courseId).orElse(null);
    }

    public boolean deleteCourse(Long courseId) {
        Course existingCourse = courseRepository.findById(courseId).orElse(null);
        if (existingCourse != null) {
            courseRepository.delete(existingCourse);
            return true; // Deletion successful
        }
        return false; // Course not found or deletion unsuccessful
    }
}
