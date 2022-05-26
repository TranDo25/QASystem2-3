package com.example.nhom5.QASystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.nhom5.QASystem.entities.QuestionComment;



@Repository
public interface QuestionCommentRepository extends JpaRepository<QuestionComment, Integer> {

}
