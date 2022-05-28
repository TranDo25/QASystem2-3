package com.example.nhom5.QASystem.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.nhom5.QASystem.entities.Answer;
import com.example.nhom5.QASystem.entities.Question;
import com.example.nhom5.QASystem.entities.User;
import com.example.nhom5.QASystem.services.FieldService;
import com.example.nhom5.QASystem.services.QuestionService;
import com.example.nhom5.QASystem.services.UserService;

@Controller
@RequestMapping("/questions")
public class QuestionController {
	private QuestionService questionService;
	private UserService userService;
	@Autowired
	FieldService fieldService;

	@Autowired
	public QuestionController(QuestionService questionService, UserService userService) {
		this.questionService = questionService;
		this.userService = userService;
	}

	@PostMapping("/save")
	public String saveQuestion(@ModelAttribute("question") Question question) {
		questionService.saveQuestion(question);
		return "";
	}

	@GetMapping("/list")
	public String viewQuestions(Model model, @RequestParam("field") Optional<String> field,
			@RequestParam("field2") Optional<String> field2) {
		// nếu trường field rỗng, thực hiện lấy danh sách bình thường, thay điều kiện
		// sắp xếp là title
		// nếu trường field không rỗng, thực hiện chỉ lấy danh sách 10 đối tượng, viết
		// hàm lấy trong repository
		// lấy ra danh sách 10 đối tượng đã được sắp xếp sẵn, không sử dụng sort nữa,
		// sort vẫn giữ nguyên khi không có điều kiện sắp xếp
		if (field2.isPresent()) {
			model.addAttribute("condition", true);
			// title
			if (field2.get().equals("topquestion")) {
				List<Question> ls = questionService.list10Question();
				model.addAttribute("questions", ls);
				model.addAttribute("field2", "topquestion");
			} else if (field2.get().equals("topfield")) {
				List<Question> ls = questionService.listQuestionByTopField();
				model.addAttribute("questions", ls);
				model.addAttribute("field2", "topfield");
			}
			else if (field2.get().equals("topuser")) {
				List<Question> ls = questionService.listQuestionByTopUser();
				model.addAttribute("questions", ls);
				model.addAttribute("field2", "topuser");
			}
		}

		// bên dưới là điều kiện mặc định, trường field có giá trị hoặc không có giá trị
		else {
			Sort sort = Sort.by(Direction.ASC, field.orElse("title"));// nếu trường field rỗng thì mặc định xếp theo
																		// title
			List<Question> ls = questionService.findAll(sort);
			model.addAttribute("questions", ls);
			model.addAttribute("condition", false);
		}

		return "./HomeViews/home";
	}

	@GetMapping("/{id}")
	public String detailQuestion(@PathVariable("id") int id, Model model) {
		Question question = questionService.getQuestionById(id);
		model.addAttribute("question", question);
		return "./QuestionViews/detailQuestion";
	}

	@PostMapping("/answer")
	public String commentQuestion(@RequestParam("idQuestion") int idQuestion, @RequestParam("content") String content,
			@CookieValue(value = "userId", defaultValue = "-1") int userId) {
		User user;
		if (userId == -1) {
			return "redirect:/loginProcess";
		} else {
			Question question = questionService.getQuestionById(idQuestion);
			Answer answer = new Answer();
			answer.setContent(content);
			answer.setUser(userService.get(userId));
			answer.setQuestion(question);
			answer.setPoint(0);
			question.addAnswer(answer);
			this.questionService.saveQuestion(question);
			return "redirect:/questions/" + question.getId();
		}
	}
}
