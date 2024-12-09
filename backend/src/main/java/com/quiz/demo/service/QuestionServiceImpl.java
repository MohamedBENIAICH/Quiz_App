package com.quiz.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.quiz.demo.model.Question;
import com.quiz.demo.repository.QuestionRepository;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionServiceImpl(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public List<Question> getAll() {
        return questionRepository.findAll();
    }

    @Override
    public String addQuestion(Question question) {
        if (question == null) {
            return "Question is null";
        } else {
            questionRepository.save(question);
            return "Question added Successfully";
        }
    }

    @Override
    public String deleteQuestion(Long id) {
        questionRepository.deleteById(id);
        return "Question deleted successfully";
    }

    @Override
    public String deleteQuestions() {
        questionRepository.deleteAll();
        return "All questions deleted successfully";
    }

}
