package com.quiz.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.quiz.demo.model.Quiz;
import com.quiz.demo.service.QuizService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/quiz")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class QuizController {

    private final QuizService quizService;
    private final HttpServletRequest request;

    public QuizController(QuizService quizService, HttpServletRequest request) {
        this.quizService = quizService;
        this.request = request;
    }

    @PostMapping("/createQuiz")
    public Quiz createQuiz(@RequestParam Long userId,
            @RequestBody Quiz quiz) {
        Quiz createdQuiz = quizService.createQuizWithQuestions(userId, quiz);

        request.getSession().setAttribute("quiz", createdQuiz);
        request.getSession().setAttribute("quizId", createdQuiz.getId());

        return createdQuiz;
    }

    @GetMapping("/getQuiz")
    public List<Quiz> getAll() {
        return quizService.getAll();
    }

    @DeleteMapping("/delete/{id}")
    public String deleteQuiz(@PathVariable Long id) {
        return quizService.deleteQuiz(id);
    }

    @GetMapping("/userQuiz")
    public List<Quiz> getUserQuiz(@RequestParam Long userId) {
        return quizService.getUserQuizz(userId);
    }
}