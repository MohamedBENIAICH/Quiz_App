package com.quiz.demo.service;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.quiz.demo.model.Quiz;
import com.quiz.demo.model.Session;
import com.quiz.demo.model.User;
import com.quiz.demo.repository.QuizRepository;
import com.quiz.demo.repository.SessionRepository;
import com.quiz.demo.repository.UserRepository;

@Service
public class SessionServiceImp implements SessionService {

    private final SessionRepository sessionRepository;

    private final UserRepository userRepository;

    private final QuizRepository quizRepository;

    public SessionServiceImp(SessionRepository sessionRepository, UserRepository userRepository,
            QuizRepository quizRepository) {
        this.sessionRepository = sessionRepository;
        this.userRepository = userRepository;
        this.quizRepository = quizRepository;
    }

    @Override
    public String createSession(Long userId, Long quizId) {
        User creator = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found with id: " + quizId));

        Long pinCode = generatePinCode();

        Session session = new Session();
        session.setPinCode(pinCode);
        session.setQuiz(quiz);
        session.setCreator(creator);
        session.setStartTime(LocalDateTime.now());
        session.setEndTime(null);
        session.setActive(true);

        sessionRepository.save(session);

        return "Session created successfully with PIN: " + pinCode;
    }

    @Override
    public String joinSession(Long user_id, Long pincode) {
        User guest = userRepository.findById(user_id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + user_id));

        Session session = sessionRepository.findByPinCode(pincode);

        if (session == null) {
            return "Session not found with the given PIN";
        }

        if (!session.getParticipants().contains(guest)) {
            session.getParticipants().add(guest);
            sessionRepository.save(session);
            return "User joined the session successfully!";
        } else {
            return "User already joined the session.";
        }
    }

    public Long generatePinCode() {
        Random random = new Random();
        return 100000L + random.nextInt(900000);
    }

    @Override
    public String deleteSession(Long session_id) {
        sessionRepository.deleteById(session_id);
        return "Session deleted successfully";
    }

    @Override
    public String stopSession(Long session_id) {
        Session session = sessionRepository.findById(session_id)
                .orElseThrow(() -> new RuntimeException("No session found with this id: " + session_id));

        sessionRepository.delete(session);
        return "Session is deleted successfully";
    }

}