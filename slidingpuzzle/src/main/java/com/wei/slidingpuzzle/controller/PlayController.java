package com.wei.slidingpuzzle.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.wei.slidingpuzzle.service.ImageService;
import com.wei.slidingpuzzle.service.PlayService;
import com.wei.slidingpuzzle.service.UserService;
import com.wei.slidingpuzzle.entity.PlayRecord;
import com.wei.slidingpuzzle.entity.PuzzleImage;


@Controller
@RequestMapping("/account")
public class PlayController {
	
	@Autowired
	private PlayService playService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ImageService imageService;
	
	@ModelAttribute("record")
	public PlayRecord constructRecord() {
		return new PlayRecord();
	}
	
	@ModelAttribute("images")
	public List<String> constructImageList() {
		return new ArrayList<String>();
	}
	
	@RequestMapping("/play")
	public String play(Model model, Principal principal, 
			@RequestParam(value="size", required = true) int size,
			@ModelAttribute("record") PlayRecord record,
			@ModelAttribute("images") List<String> images) {
		
		String userName = principal.getName();
		record.setPuzzleSize(size);
		record.setUser(userService.loadUserByUserName(userName));
		
		model.addAttribute(record);
		
		images.add("Number_default_" + size + ".png");
		images.add("Image_default_" + size + ".png");
		for (PuzzleImage puzzleImage : imageService.loadImagesByUser(userName)) {
			images.add(puzzleImage.getImageUri());
		}
		model.addAttribute(images);
		return "play";
	}

	@RequestMapping(value="/play", method=RequestMethod.POST)
	public String playRecord(Model model, Principal principal,
			@Valid @ModelAttribute("record") PlayRecord record, 
			BindingResult result) {
		
		String userName = principal.getName();
		record.setUser(userService.loadUserByUserName(userName));
		
		if (result.hasErrors()) {
			return "redirect:/account.html";
		}
		
		playService.savePlayRecord(record);
		return "redirect:/account.html";
	}
	
	@RequestMapping(value="/solution", method=RequestMethod.POST)
	public String solution(Model model, @RequestParam(value="size", required = true) int size,
			@RequestParam(value="board", required = true) String board,
			@RequestParam(value="mode", required = true) String mode) {
		
		model.addAttribute("puzzleSize", size);
		model.addAttribute("image", mode);
		model.addAttribute("solution", playService.getSolution(size, board));
		
		return "solution";
	}
	
}
