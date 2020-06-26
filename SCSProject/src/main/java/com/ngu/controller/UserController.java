package com.ngu.controller;

import java.security.Principal;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ngu.Model.User;
import com.ngu.Model.UserModel;
import com.ngu.Service.EmailService;
import com.ngu.Service.PostService;
import com.ngu.Service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@Autowired
	EmailService emailService;
	
	@Autowired
	PostService postService;
	
	@Autowired
	HttpSession session;
	@Autowired
	HttpServletRequest request;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@ModelAttribute("user")
	public User getUser() {
		return new User();
	}

	

	@RequestMapping(value = "/profile/{id}", method = RequestMethod.GET)
	public String ProfilePage(@PathVariable int id, Model model) {

		Optional<User> optional = userService.findById(id);
		if(optional.isPresent())
		{	
			User user = optional.get();
			model.addAttribute("user", user);
			model.addAttribute("posts", postService.findTop4ByOrderByIdDesc());
		}

		
		return "/user/profile";

	}
	
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String updateUser( User user, ModelMap modelMap,@RequestParam Map<String,String> params,Principal principal) {

		user= userService.getUserFromSession();
		
		if(user != null)
		{
			user.setFname(params.get("fname"));
			user.setLname(params.get("lname"));
			user.setUsername(params.get("username"));
			user.setEmail(params.get("email"));
			
			UserModel userModel = new UserModel();
			
			userModel.setId(user.getId());
			userModel.setFname(user.getFname());
			userModel.setLname(user.getLname());
			userModel.setFullName(user.getFname() + " " + user.getLname());
			userModel.setEmail(user.getEmail());
			userModel.setUsername(user.getUsername());
			userModel.setProfilePicture(user.getProfilePicture());
			userModel.setPhone(user.getPhone());
			userModel.setUser(user);
			
			session.setAttribute("userModel", userModel);
			userService.update(user);
		}
		return "redirect:/user/profile/"+user.getId();

	}
	
	
}
