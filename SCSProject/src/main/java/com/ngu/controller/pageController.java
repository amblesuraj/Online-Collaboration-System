package com.ngu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ngu.Model.Like;
import com.ngu.Model.Post;
import com.ngu.Service.LikeService;
import com.ngu.Service.PostService;
import com.ngu.Service.UserService;

@Controller

public class pageController {

	@Autowired
	UserService userService;
	
	@Autowired
	PostService postService;
	
	@Autowired
	LikeService  likeService;
	@RequestMapping(value = { "", "/", "index", "home" }, method = RequestMethod.GET)
	public String index(Model model) {
		model.addAttribute("isUserLayout", true);
		model.addAttribute("users", userService.findTop4ByOrderByIdDesc());
		

		return "index";
	}

	@ModelAttribute("Posts")
	public List<Post> allPosts()
	{
		return postService.findAllPosts();
	}
	

	@ModelAttribute("likes")
	public List<Like> getLikes()
	{
		return likeService.getLikes();
	}
	
}
