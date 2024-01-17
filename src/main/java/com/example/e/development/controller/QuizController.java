package com.example.e.development.controller;

import com.example.e.development.models.Quiz;
import com.example.e.development.service.QuizService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {
    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }
    @PostMapping("/create/{courseId}")
    public ResponseEntity<Quiz> createQuiz(@PathVariable Long courseId, @RequestBody Quiz quiz){
        Quiz createdQuiz = quizService.createQuiz(courseId, quiz);
        return new ResponseEntity<>(createdQuiz, HttpStatus.CREATED);
    }
}