package com.example.e.development.service;

import com.example.e.development.models.Course;
import com.example.e.development.models.Quiz;
import com.example.e.development.repository.CourseRepository;
import com.example.e.development.repository.QuizRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuizService {
    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private CourseRepository courseRepository;

    public Quiz createQuiz(Long courseId, Quiz quiz) {
        //find the course by id
        Course course = courseRepository.findById(courseId)
                .orElseThrow(()-> new EntityNotFoundException("Course not found with id:" + courseId));
        //set the course for the quiz
        quiz.setCourse(course);
        //save the quiz within the context of the course
        quizRepository.save(quiz);
        return quiz;
    }
}