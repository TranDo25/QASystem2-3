package com.example.nhom5.QASystem.repositories;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.example.nhom5.QASystem.entities.Question;


@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer>, PagingAndSortingRepository<Question, Integer> {
	public ArrayList<Question> findAllByOrderByCreateAtDesc();
	
}
