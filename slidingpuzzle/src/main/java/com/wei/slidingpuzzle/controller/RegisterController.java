package com.wei.slidingpuzzle.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wei.slidingpuzzle.entity.PuzzleUser;
import com.wei.slidingpuzzle.service.UserService;


@Controller
@RequestMapping("/register")
public class RegisterController {
	
	@Autowired
	private UserService userService;

	@ModelAttribute("user")
	public PuzzleUser constructUser() {
		return new PuzzleUser();
	}

	@RequestMapping
	public String showRegister() {
		return "register";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String doRegister(@Valid @ModelAttribute("user") PuzzleUser user, 
			BindingResult result) {
		if (result.hasErrors()) {
			return "register";
		}
		userService.createUser(user);
		
		return "redirect:/login.html";
	}
	
	@RequestMapping("/available")
	@ResponseBody
	public String available(@RequestParam String userName) {
		Boolean available = userService.loadUserByUserName(userName) == null;
		return available.toString();
	}

}
