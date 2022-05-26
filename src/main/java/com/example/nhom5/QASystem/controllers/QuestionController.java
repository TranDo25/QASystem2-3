package com.example.nhom5.QASystem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.nhom5.QASystem.entities.Answer;
import com.example.nhom5.QASystem.entities.Question;
import com.example.nhom5.QASystem.entities.User;
import com.example.nhom5.QASystem.services.QuestionService;
import com.example.nhom5.QASystem.services.UserService;

@Controller
@RequestMapping("/questions")
public class QuestionController {
	private QuestionService questionService;
	private UserService userService;
	@Autowired
	public QuestionController(QuestionService questionService, UserService userService) {
		this.questionService=questionService;
		this.userService=userService;
	}
	
	@PostMapping("/save")
	public String saveQuestion(@ModelAttribute("question") Question question) {
		questionService.saveQuestion(question);
		return "";
	}
	
	@GetMapping("/{id}")
	public String detailQuestion(@PathVariable("id") int id, Model model) {
		Question question=questionService.getQuestionById(id);
		model.addAttribute("question", question);
		return "./QuestionViews/detailQuestion";
	}
	@PostMapping("/answer")
	public String commentQuestion(@RequestParam("idQuestion") int idQuestion, @RequestParam("content") String content, @CookieValue(value="userId", defaultValue="-1") int userId) {
		User user;
		if(userId==-1) {
			return "redirect:/loginProcess";
		}
		else {
			Question question=questionService.getQuestionById(idQuestion);
			Answer answer=new Answer();
			answer.setContent(content);
			answer.setUser(userService.get(userId));
			answer.setQuestion(question);
			answer.setPoint(0);
			question.addAnswer(answer);
			this.questionService.saveQuestion(question);
			return "redirect:/questions/"+question.getId();
		}
	}
}
