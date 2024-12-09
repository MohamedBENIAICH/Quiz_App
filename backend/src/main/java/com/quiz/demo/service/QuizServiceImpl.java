package com.quiz.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.quiz.demo.model.Question;
import com.quiz.demo.model.Quiz;
import com.quiz.demo.model.User;
import com.quiz.demo.repository.QuizRepository;
import com.quiz.demo.repository.UserRepository;

@Service
public class QuizServiceImpl implements QuizService {

    private final QuizRepository quizRepository;
    private final UserRepository userRepository;

    public QuizServiceImpl(QuizRepository quizRepository, UserRepository userRepository) {
        this.quizRepository = quizRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Quiz> getAll() {
        return quizRepository.findAll();
    }

    @Override
    public String deleteQuiz(Long id) {
        quizRepository.deleteById(id);
        return "Quiz deleted successfully";
    }

    @Override
    public Quiz createQuizWithQuestions(Long userId, Quiz quiz) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        quiz.setCreator(user);

        if (quiz.getQuestions() != null) {
            for (Question question : quiz.getQuestions()) {
                question.setQuiz(quiz);
            }
        }
        return quizRepository.save(quiz);
    }

    @Override
    public List<Quiz> getUserQuizz(Long userId) {
        return quizRepository.findByCreatorId(userId);
    }
}