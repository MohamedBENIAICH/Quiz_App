package com.quiz.demo.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.quiz.demo.repository.QuizRepository;
import com.quiz.demo.repository.UserRepository;

@SpringBootTest
@Transactional
public class QuizModelTest {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setup() {

        user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("password123");

        userRepository.save(user);
    }

    @Test
    void testQuizCreation() {
        Quiz quiz = new Quiz();
        quiz.setQuizTitle("Sample Quiz");
        quiz.setCreator(user);

        Quiz savedQuiz = quizRepository.save(quiz);

        assertNotNull(savedQuiz.getId());
        assertEquals("Sample Quiz", savedQuiz.getQuizTitle());
        assertEquals(user, savedQuiz.getCreator());
    }

    @Test
    void testQuizWithQuestions() {
        Question question1 = new Question();
        question1.setQuestionTitle("What is Java?");
        question1.setOption1("Language");
        question1.setOption2("Food");
        question1.setOption3("Car");
        question1.setOption4("Animal");
        question1.setRightAnswer("Language");

        Question question2 = new Question();
        question2.setQuestionTitle("What is Spring Boot?");
        question2.setOption1("Framework");
        question2.setOption2("City");
        question2.setOption3("Season");
        question2.setOption4("Car");
        question2.setRightAnswer("Framework");

        // Create a Quiz and set Questions
        Quiz quiz = new Quiz();
        quiz.setQuizTitle("Tech Quiz");
        quiz.setCreator(user);

        question1.setQuiz(quiz);
        question2.setQuiz(quiz);
        quiz.setQuestions(Arrays.asList(question1, question2));

        // Save the Quiz
        Quiz savedQuiz = quizRepository.save(quiz);

        // Validate Quiz and Questions
        assertNotNull(savedQuiz.getId());
        assertEquals(2, savedQuiz.getQuestions().size());
        assertEquals("Tech Quiz", savedQuiz.getQuizTitle());
        assertEquals(user, savedQuiz.getCreator());
        assertEquals("What is Java?", savedQuiz.getQuestions().get(0).getQuestionTitle());
    }

    // @Test
    // void testDeleteQuizCascadesToQuestions() {
    // // Create a Quiz with Questions
    // Quiz quiz = new Quiz();
    // quiz.setQuizTitle("Cascade Test");
    // quiz.setCreator(user);

    // Question question = new Question();
    // question.setQuestionTitle("Sample Question");
    // question.setOption1("A");
    // question.setOption2("B");
    // question.setOption3("C");
    // question.setOption4("D");
    // question.setRightAnswer("A");
    // question.setQuiz(quiz);

    // quiz.setQuestions(Arrays.asList(question));

    // // Save the Quiz
    // Quiz savedQuiz = quizRepository.save(quiz);

    // // Validate saved Quiz and Question
    // assertEquals(1, quizRepository.findAll().size());

    // // Delete the Quiz
    // quizRepository.delete(savedQuiz);

    // // Validate cascading delete
    // assertEquals(0, quizRepository.findAll().size());
    // }
}
