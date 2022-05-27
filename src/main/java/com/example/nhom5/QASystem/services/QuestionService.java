package com.example.nhom5.QASystem.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;
import org.springframework.stereotype.Service;

import com.example.nhom5.QASystem.entities.Question;
import com.example.nhom5.QASystem.repositories.QuestionRepository;

@Service
public class QuestionService {
	private QuestionRepository rep;

	@Autowired
	public QuestionService(QuestionRepository rep) {
		this.rep = rep;
	}

	public Question getQuestionById(int id) {
		return rep.getById(id);
	}

	public void saveQuestion(Question question) {
		rep.save(question);
	}

	public ArrayList<Question> newQuestion() {
		ArrayList<Question> list = rep.findAllByOrderByCreateAtDesc();
		if (list.size() > 10) {
			list = (ArrayList<Question>) list.subList(0, 10);
		}
		return list;
	}

	public ArrayList<Question> findAllByOrderByCreateAtDesc() {
		return rep.findAllByOrderByCreateAtDesc();
	}

	public Question save(Question entity) {
		return rep.save(entity);
	}

	public List<Question> findAll() {
		return rep.findAll();
	}

	public List<Question> findAll(Sort sort) {
		return rep.findAll(sort);
	}

	public List<Question> findAllById(List<Integer> ids) {
		return rep.findAllById(ids);
	}

	public List<Question> saveAll(List<Question> entities) {
		return rep.saveAll(entities);
	}

	public boolean existsById(Integer id) {
		return rep.existsById(id);
	}

	public void deleteById(Integer id) {
		rep.deleteById(id);
	}

	public void delete(Question entity) {
		rep.delete(entity);
	}

	public void deleteAllById(List<Integer> ids) {
		rep.deleteAllById(ids);
	}

	public void deleteAll() {
		rep.deleteAll();
	}
}
