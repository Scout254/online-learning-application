package com.example.e.development.repository;

import com.example.e.development.models.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quiz , Long> {
}
