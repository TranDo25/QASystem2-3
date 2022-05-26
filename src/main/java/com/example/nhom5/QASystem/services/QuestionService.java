package com.example.nhom5.QASystem.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.nhom5.QASystem.entities.Question;
import com.example.nhom5.QASystem.repositories.QuestionRepository;


@Service
public class QuestionService {
	private QuestionRepository rep;
	@Autowired
	public QuestionService(QuestionRepository rep) {
		this.rep=rep;
	}
	public Question getQuestionById(int id) {
		return rep.getById(id);
	}
	public void saveQuestion(Question question) {
		rep.save(question);
	}
	public ArrayList<Question> newQuestion(){
		ArrayList<Question> list=rep.findAllByOrderByCreateAtDesc();
		if(list.size()>10) {
			list=(ArrayList<Question>) list.subList(0, 10);
		}
		return list;
	}
}
