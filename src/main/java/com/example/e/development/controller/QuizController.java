package com.example.e.development.controller;

import com.example.e.development.models.Quiz;
import com.example.e.development.service.QuizService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PutMapping("/edit/{quizId}")
    public ResponseEntity<Quiz> editQuiz(@PathVariable Long quizId, @RequestBody Quiz quiz){
        Quiz updatedQuiz = quizService.editQuiz(quizId, quiz);
        return new ResponseEntity<>(updatedQuiz, HttpStatus.OK);
    }
    @GetMapping()
    public ResponseEntity<List<Quiz>> getAllQuizzes(){
        List<Quiz> quizzes = quizService.getAllQuizzes();
        return ResponseEntity.ok(quizzes);
    }

    @GetMapping("/{quizId}")
    public ResponseEntity<Quiz> getQuizById(@PathVariable Long quizId){
        Quiz quiz = quizService.getQuizById(quizId);
        return ResponseEntity.ok(quiz);
    }

    @DeleteMapping("/delete/{quizId}")
    public ResponseEntity<String> deleteQuiz(@PathVariable Long quizId){

        boolean isDeleted = quizService.deleteQuiz(quizId);

        if (isDeleted){
            return ResponseEntity.ok("successfully deleted!");
        }else {
            return ResponseEntity.notFound().build();
        }
    }

}