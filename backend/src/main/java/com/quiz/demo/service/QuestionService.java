package com.quiz.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.quiz.demo.model.Question;

@Service
public interface QuestionService {

    List<Question> getAll();

    String addQuestion(Question question);

    String deleteQuestion(Long id);

    String deleteQuestions();

}
