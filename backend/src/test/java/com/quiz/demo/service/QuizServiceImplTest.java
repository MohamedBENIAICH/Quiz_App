package com.quiz.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.quiz.demo.model.Question;
import com.quiz.demo.model.Quiz;
import com.quiz.demo.model.User;
import com.quiz.demo.repository.QuizRepository;
import com.quiz.demo.repository.UserRepository;

public class QuizServiceImplTest {

    @Mock
    private QuizRepository quizRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private QuizServiceImpl quizService;

    private User testUser;
    private Quiz testQuiz;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Setup test user
        testUser = new User();
        testUser.setId(1L);
        testUser.setFirstName("testUser");

        // Setup test quiz
        testQuiz = new Quiz();
        testQuiz.setId(1L);
        testQuiz.setQuizTitle("Sample Quiz");
        testQuiz.setCreator(testUser);
    }

    @Test
    void testGetAllQuizzes() {
        // Mock behavior of quizRepository
        when(quizRepository.findAll()).thenReturn(Arrays.asList(testQuiz));

        // Call the service method
        List<Quiz> quizzes = quizService.getAll();

        // Verify the result
        assertNotNull(quizzes);
        assertEquals(1, quizzes.size());
        assertEquals("Sample Quiz", quizzes.get(0).getQuizTitle());

        // Verify interaction with repository
        verify(quizRepository, times(1)).findAll();
    }

    @Test
    void testDeleteQuiz() {
        Long quizId = 1L;

        // Call the service method
        String response = quizService.deleteQuiz(quizId);

        // Verify the response
        assertEquals("Quiz deleted successfully", response);

        // Verify that the repository's deleteById method was called
        verify(quizRepository, times(1)).deleteById(quizId);
    }

    @Test
    void testCreateQuizWithQuestions() {
        // Prepare the mock behavior of userRepository
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

        // Create quiz with questions
        Quiz newQuiz = new Quiz();
        newQuiz.setQuizTitle("New Quiz");
        newQuiz.setQuestions(Arrays.asList(new Question("Sample Question 1")));

        // Mock the save method of quizRepository
        when(quizRepository.save(any(Quiz.class))).thenReturn(newQuiz);

        // Call the service method
        Quiz createdQuiz = quizService.createQuizWithQuestions(1L, newQuiz);

        // Verify the result
        assertNotNull(createdQuiz);
        assertEquals("New Quiz", createdQuiz.getQuizTitle());
        assertEquals(1, createdQuiz.getQuestions().size());

        // Verify interactions with the repository
        verify(userRepository, times(1)).findById(1L);
        verify(quizRepository, times(1)).save(any(Quiz.class));
    }

    @Test
    void testCreateQuizWithQuestions_UserNotFound() {
        // Prepare the mock behavior of userRepository to return empty
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Create quiz
        Quiz newQuiz = new Quiz();
        newQuiz.setQuizTitle("New Quiz");

        // Expect an exception to be thrown
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            quizService.createQuizWithQuestions(1L, newQuiz);
        });

        assertEquals("User not found", exception.getMessage());

        // Verify that neither the save nor the repository's findById method were called
        // again
        verify(quizRepository, never()).save(any(Quiz.class));
    }

    @Test
    void testGetUserQuizzes() {
        // Prepare mock behavior for finding quizzes by userId
        when(quizRepository.findByCreatorId(1L)).thenReturn(Arrays.asList(testQuiz));

        // Call the service method
        List<Quiz> quizzes = quizService.getUserQuizz(1L);

        // Verify the result
        assertNotNull(quizzes);
        assertEquals(1, quizzes.size());
        assertEquals("Sample Quiz", quizzes.get(0).getQuizTitle());

        // Verify interaction with repository
        verify(quizRepository, times(1)).findByCreatorId(1L);
    }
}
