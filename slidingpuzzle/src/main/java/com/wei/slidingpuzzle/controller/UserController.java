package com.wei.slidingpuzzle.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.wei.slidingpuzzle.service.PlayService;
import com.wei.slidingpuzzle.entity.PlayRecord;
import com.wei.slidingpuzzle.entity.PuzzleImage;
import com.wei.slidingpuzzle.service.ImageService;
import com.wei.slidingpuzzle.service.UserService;


@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PlayService playService;
	
	@Autowired
	private ImageService imageService;
	
	@RequestMapping("/account")
	public String account(Model model, Principal principal) {
		String userName = principal.getName();
		model.addAttribute("user", userService.loadUserWithRecordsAndImages(userName));
		return "account";
	}
	
	@RequestMapping(value = "/account", method = RequestMethod.POST)
	public String doAddImage(Model model, @RequestParam("imageFile") MultipartFile imageFile, 
			Principal principal) {
		
		String userName = principal.getName();
		// String rootDirectory = "F:/Yuli_Java/Git/Git_Puzzle/slidingpuzzle/src/main/webapp/";
		String rootDirectory = "F:/Yuli_Java/MyProjects/puzzle_images/";
		imageService.resizeAndSaveImage(userName, imageFile, rootDirectory);;		
		return "redirect:/account.html";
	}
	
	@RequestMapping("/records/remove/{recordId}")
	public String removeRecord(@PathVariable Long recordId) {
		PlayRecord record = playService.loadRecordById(recordId);
		playService.deleteRecord(record);
		return "redirect:/account.html";
	}
	
	@RequestMapping("/images/remove/{imageId}")
	public String removeImage(@PathVariable Long imageId, Model model) {
		// String rootDirectory = "F:/Yuli_Java/Git/Git_Puzzle/slidingpuzzle/src/main/webapp/";
		String rootDirectory = "F:/Yuli_Java/MyProjects/puzzle_images/";
	    PuzzleImage image = imageService.loadImageById(imageId);
		imageService.deleteImage(image, rootDirectory);
		return "redirect:/account.html";
	}
	
}
