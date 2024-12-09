package com.quiz.demo.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.quiz.demo.model.User;

@Service
public interface UserService {

    ResponseEntity<String> addUser(User user);

    User getUserByEmail(String email);

    User getUserByEmailAndPassword(String email, String password);

}
