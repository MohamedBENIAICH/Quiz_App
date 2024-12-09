package com.quiz.demo.service;

import org.springframework.stereotype.Service;

@Service
public interface SessionService {

    String createSession(Long userId, Long quizId);

    String deleteSession(Long session_id);

    String joinSession(Long user_id, Long pincode);

    String stopSession(Long session_id);

}
