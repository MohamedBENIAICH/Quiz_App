package com.quiz.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quiz.demo.model.Question;
import com.quiz.demo.service.QuestionService;

// import java.util.HashMap;
// import java.util.Map;

@RestController
@RequestMapping("/questions")
@CrossOrigin(origins = "http://localhost:5173")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    // private Map<String, List<Question>> sessionMap = new HashMap<>();

    @GetMapping("/getAll")
    public List<Question> getQuestions() {
        return questionService.getAll();
    }

    @PostMapping("/add")
    public String addQuestion(@RequestBody Question question) {
        return questionService.addQuestion(question);
    }

    @DeleteMapping("/deleteAll")
    public String deleteQuestions() {
        return questionService.deleteQuestions();
    }

    @DeleteMapping("/delete/{id}")
    public String deleteQuestion(@PathVariable Long id) {
        return questionService.deleteQuestion(id);
    }

}
