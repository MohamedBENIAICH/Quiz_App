package com.quiz.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.quiz.demo.service.SessionService;

@RestController
@RequestMapping("/session")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class SessionController {

    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @PostMapping("/createSession")
    public ResponseEntity<String> createSession(@RequestParam Long user_id, @RequestParam Long quiz_id) {
        String response = sessionService.createSession(user_id, quiz_id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/joinSession")
    public ResponseEntity<String> joinSession(@RequestParam Long user_id, @RequestParam Long pincode) {
        String response = sessionService.joinSession(user_id, pincode);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/stopSession")
    public ResponseEntity<String> stopSession(@RequestParam Long session_id) {
        String response = sessionService.stopSession(session_id);
        return ResponseEntity.ok(response);
    }
}