package com.quiz.demo.repository;

// import java.util.List;

// import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.quiz.demo.model.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

}
