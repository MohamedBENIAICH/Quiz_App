package com.quiz.demo.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quiz.demo.model.Question;
import com.quiz.demo.service.QuestionService;

@WebMvcTest(QuestionController.class) // Only load the QuestionController
public class QuestionControllerTest {

    @MockBean // Mock the QuestionService to inject into the controller
    private QuestionService questionService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        // Initialize MockMvc
        mockMvc = MockMvcBuilders.standaloneSetup(new QuestionController(questionService)).build();
    }

    @Test
    public void testGetAllQuestions() throws Exception {
        // Setup the mock behavior for QuestionService
        when(questionService.getAll()).thenReturn(someMockedQuestionsList());

        // Perform the test
        mockMvc.perform(get("/questions/getAll"))
                .andExpect(status().isOk());
    }

    @Test
    public void testAddQuestion() throws Exception {
        // Create a new Question object
        Question question = new Question();
        question.setId(1L);
        question.setQuestionTitle("What is Spring Boot?");
        question.setRightAnswer("A framework for building Java applications");

        // Mock the addQuestion method in QuestionService
        when(questionService.addQuestion(question)).thenReturn("Question added Successfully");

        // Convert the Question object to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String questionJson = objectMapper.writeValueAsString(question);

        // Perform the POST request and verify the result
        mockMvc.perform(post("/questions/add")
                .contentType("application/json")
                .content(questionJson)) // Set the request body to the question JSON
                .andExpect(status().isOk()) // Expect HTTP status 200 OK
                .andExpect(content().string("Question added Successfully")); // Expect the response content
    }

    @Test
    public void testDeleteQuestions() throws Exception {
        // Mock the deleteQuestions method in QuestionService
        when(questionService.deleteQuestions()).thenReturn("Questions deleted successfully");

        // Perform the test
        mockMvc.perform(delete("/questions/deleteAll"))
                .andExpect(status().isOk());
    };

    @Test
    public void testDeleteQuestion() throws Exception {
        // Create a new Question object
        Question question = new Question();
        question.setId(1L);
        question.setQuestionTitle("What is Spring Boot?");
        question.setRightAnswer("A framework for building Java applications");

        // Mock the deleteQuestion method in QuestionService
        when(questionService.deleteQuestion(question.getId())).thenReturn("Question deleted successfully");

        // Perform the DELETE request and verify the result
        mockMvc.perform(delete("/questions/delete/{id}", question.getId())) // Correct URL with ID substitution
                .andExpect(status().isOk()) // Expect HTTP status 200 OK
                .andExpect(content().string("Question deleted successfully")); // Expect the response content
    }

    private List<Question> someMockedQuestionsList() {
        return new ArrayList<>();
    }
}
