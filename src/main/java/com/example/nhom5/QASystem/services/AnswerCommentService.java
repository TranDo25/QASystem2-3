package com.example.nhom5.QASystem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.nhom5.QASystem.repositories.AnswerCommentRepository;



@Service
public class AnswerCommentService {

	@Autowired
	private AnswerCommentRepository repository;
}
