package com.ngu.controller;

import java.security.Principal;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.session.Session;
import org.springframework.session.SessionRepository;
import org.springframework.session.web.http.SessionRepositoryFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.ngu.Model.CustomUserDetails;
import com.ngu.Model.User;
import com.ngu.Model.UserModel;
import com.ngu.Service.EmailService;
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
	
		}

		
		return "/user/profile";

	}
	
	@Autowired
	HttpSession session;
	@Autowired
	HttpServletRequest request;
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String updateUser( User user, ModelMap modelMap,@RequestParam Map<String,String> params,Principal principal) {
		user = userService.getUserFromSession();
		user.setFname(params.get("fname"));
		user.setLname(params.get("lname"));
		user.setUsername(params.get("username"));
		user.setEmail(params.get("email"));
		userService.update(user);
		
//		UserModel userModel = new UserModel();
//		userModel.setUser(user);
		((UserModel)session.getAttribute("userModel")).setUser(user);
//		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
//		SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//		RequestContextHolder.currentRequestAttributes().setAttribute("SPRING_SECURITY_CONTEXT",authenticationToken,RequestAttributes.SCOPE_SESSION);
		
		return "redirect:/user/profile/"+user.getId();

	}
	
	// forgot password
	
}
