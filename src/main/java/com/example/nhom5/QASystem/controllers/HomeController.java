package com.example.nhom5.QASystem.controllers;


import java.util.List;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.nhom5.QASystem.entities.Question;
import com.example.nhom5.QASystem.entities.User;
import com.example.nhom5.QASystem.services.QuestionService;
import com.example.nhom5.QASystem.services.UserService;



@Controller
@RequestMapping("/")
public class HomeController {
	private QuestionService questionService;
	private UserService userService;

	@Autowired
	public HomeController(QuestionService questionService, UserService userService) {
		this.questionService = questionService;
		this.userService = userService;
	}
	
	@GetMapping
	public String viewHome(Model model, @CookieValue(value="userId", defaultValue="-1") int userId, @RequestParam("field") Optional<String> field) {
		model.addAttribute("list", questionService.newQuestion());
		try {
			if(userId==-1) {
				model.addAttribute("user", null);
			}
			else {
				User user = userService.get(userId);
				model.addAttribute("user", user);
//				List<Question> ls = questionService.findAll();
//				Sort sort = Sort.by(Direction.DESC, field.orElse("title"));//nếu trường field rỗng thì mặc định xếp theo title
//				model.addAttribute("questions", ls);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "./HomeViews/home";
	}

	@GetMapping("/register")
	public String showSignUpForm(Model model) {
		model.addAttribute("user", new User());
		return "./HomeViews/register";
	}

	@PostMapping(value = "/process_register")
	public String processRegistration(User user, @RequestParam("position") String position,
			HttpServletResponse response) {
		//BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		//String encodedPassword = encoder.encode(user.getPassword());
		user.setPosition(position);
		//user.setPassword(encodedPassword);
		userService.save(user);
		response.addCookie(new Cookie("userId", user.getId() + ""));
		return "redirect:/";
	}

	@GetMapping("/loginProcess")
	public String viewLogin(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		return "./HomeViews/login";
	}

	@PostMapping("/loginProcess")
	public String viewLogin(User user, HttpServletResponse response) {
		boolean test = this.userService.checkLogin(user);
		if (test == true) {
			response.addCookie(new Cookie("userId", user.getId() + ""));
		} else {
			return "redirect:/loginProcess";
		}
		return "redirect:/";
	}
	
	@GetMapping("/logoutProcess")
	public String logoutProcess(HttpServletRequest request, HttpServletResponse response) {
		Cookie cookie = new Cookie("userId", null);
		cookie.setMaxAge(0);
		cookie.setSecure(true);
		cookie.setHttpOnly(true);
		cookie.setPath("/");
		response.addCookie(cookie);
		return "redirect:/";
	}
}
