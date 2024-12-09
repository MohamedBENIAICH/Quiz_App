package com.quiz.demo.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.quiz.demo.model.Quiz;
import com.quiz.demo.model.Session;
import com.quiz.demo.model.User;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class SessionRepositoryTest {

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuizRepository quizRepository;

    @Test
    void testFindByPinCode() {

        User creator = new User();
        creator.setFirstName("John");
        creator.setLastName("Doe");
        creator.setEmail("john.doe@example.com");
        creator.setPassword("password123");
        userRepository.save(creator);

        Quiz quiz = new Quiz();
        quiz.setQuizTitle("Sample Quiz");
        quiz.setCreator(creator);
        quizRepository.save(quiz);

        Session session = new Session();
        session.setPinCode(1234L);
        session.setQuiz(quiz);
        session.setCreator(creator);
        session.setActive(false);
        sessionRepository.save(session);

        Session findSession = sessionRepository.findByPinCode(1234L);

        assertThat(findSession).isNotNull();
        assertThat(findSession.getPinCode()).isEqualTo(1234L);
        assertThat(findSession.getQuiz()).isEqualTo(quiz);
        assertThat(findSession.getCreator()).isEqualTo(creator);
    }
}
