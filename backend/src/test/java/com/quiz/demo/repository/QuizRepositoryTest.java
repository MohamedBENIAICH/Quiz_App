package com.quiz.demo.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.quiz.demo.model.Quiz;
import com.quiz.demo.model.User;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
public class QuizRepositoryTest {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void testFindByCreatorId() {

        User creator = new User();
        creator.setFirstName("John");
        creator.setLastName("Doe");
        creator.setEmail("john.doe@example.com");
        creator.setPassword("password123");
        userRepository.save(creator);

        Quiz quiz1 = new Quiz();
        quiz1.setQuizTitle("Quiz 1");
        quiz1.setCreator(creator);
        quizRepository.save(quiz1);

        Quiz quiz2 = new Quiz();
        quiz2.setQuizTitle("Quiz 2");
        quiz2.setCreator(creator);
        quizRepository.save(quiz2);

        Quiz quiz3 = new Quiz();
        quiz3.setQuizTitle("Quiz 3");
        quiz3.setCreator(creator);
        quizRepository.save(quiz3);

        List<Quiz> quizzes = quizRepository.findByCreatorId(creator.getId());

        assertThat(quizzes).isNotNull();
        assertThat(quizzes).hasSize(3);
        assertThat(quizzes).extracting(Quiz::getQuizTitle)
                .containsExactlyInAnyOrder("Quiz 1", "Quiz 2", "Quiz 3");

        assertThat(quizzes).allMatch(quiz -> quiz.getCreator().equals(creator));
    }
}
