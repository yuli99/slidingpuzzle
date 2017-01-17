package com.wei.slidingpuzzle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wei.slidingpuzzle.service.UserService;


@Controller
@RequestMapping("/users")
public class AdminController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping
	public String users(Model model) {
		model.addAttribute("users", userService.loadAllUser());
		return "users";
	}
	
	@RequestMapping("/{userId}")
	public String detail(Model model, @PathVariable Long userId) {
		model.addAttribute("user", userService.loadUserWithRecordsAndImages(userId));
		return "user_detail";
	}

	@RequestMapping("/remove/{userId}")
	public String removeUser(@PathVariable Long userId) {
		userService.deleteUser(userId);
		return "redirect:/users.html";
	}
	
}
