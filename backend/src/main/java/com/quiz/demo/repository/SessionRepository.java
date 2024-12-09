package com.quiz.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quiz.demo.model.Session;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
    Session findByPinCode(Long pincode);
}