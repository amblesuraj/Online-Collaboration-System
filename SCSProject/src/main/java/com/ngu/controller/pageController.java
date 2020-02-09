package com.ngu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ngu.Service.UserService;

@Controller

public class pageController {

	@Autowired
	UserService userService;
	
	@RequestMapping(value = { "", "/", "index", "home" }, method = RequestMethod.GET)
	public String index(Model model) {
		model.addAttribute("isUserLayout", true);
		return "index";
	}

	
	
}
