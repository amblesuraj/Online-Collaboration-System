package com.ngu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ngu.Model.Post;
import com.ngu.Service.PostService;
import com.ngu.Service.UserService;

@Controller

public class pageController {

	@Autowired
	UserService userService;
	
	@Autowired
	PostService postService;
	
	@RequestMapping(value = { "", "/", "index", "home" }, method = RequestMethod.GET)
	public String index(Model model) {
		model.addAttribute("isUserLayout", true);
		model.addAttribute("users", userService.findTop4ByOrderByIdDesc());
		
		List<Post> posts = postService.findAllPosts();
		model.addAttribute("Posts", posts);
		return "index";
	}

	
	
}
