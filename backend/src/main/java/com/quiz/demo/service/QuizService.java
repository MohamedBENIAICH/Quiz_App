package com.quiz.demo.service;

import java.util.List;
import org.springframework.stereotype.Service;

import com.quiz.demo.model.Quiz;

@Service
public interface QuizService {

    List<Quiz> getAll();

    String deleteQuiz(Long id);

    List<Quiz> getUserQuizz(Long userId);

    Quiz createQuizWithQuestions(Long userId, Quiz quizRequest);

}
