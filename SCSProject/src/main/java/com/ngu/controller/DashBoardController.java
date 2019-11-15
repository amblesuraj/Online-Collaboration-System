package com.ngu.controller;

import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ngu.Model.CustomUserDetails;
import com.ngu.Model.User;
import com.ngu.Service.UserService;

@Controller
@RequestMapping(value="/dashboard")

public class DashBoardController {

	@Autowired
	UserService userService;


	@ModelAttribute("user")
	public User getUser()
	{
		return new User();
	}
	
	
	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String login(@RequestParam(value = "error", required = false) String error,
			User user,@RequestParam(value = "logout", required = false)String logout,ModelMap modelMap) {
		
		
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
		
		 if (!(authentication instanceof AnonymousAuthenticationToken) && roles.contains("SUPERADMIN")) {
	           return "redirect:superadmin";
	        }
	        else if (!(authentication instanceof AnonymousAuthenticationToken) && roles.contains("ADMIN")) {
	        	return "redirect:/admin";
	        }
	        else if (!(authentication instanceof AnonymousAuthenticationToken) && (roles.contains("EMPLOYEE") || roles.contains("USER")))
	        {
	        	return "redirect:/index";
	        }
		

		if (error != null) 
		{
			modelMap.put("error", "Invalid Username and Password.");
			modelMap.put("title","Error");
			return "dashboard/login";	
		}
		
		else if (logout != null)
		{
				modelMap.put("logout", "You have been successfully logged out.");
				modelMap.put("title","Home");
				return "index";
			
		}
		else {
			return "dashboard/login";	
		}

	}
	
	@RequestMapping(value="logout",method =RequestMethod.POST)
	public String logout(@RequestParam(value = "logout", required = false)String logout,ModelMap modelMap)
	{
		if (logout != null)
		{
				modelMap.put("logout", "You have been successfully logged out.");
				modelMap.put("title","Home");
				return "dashboard/login";
			
		}
		else
		{
			return "index";
		}
	}
	
	

	
	
	
	
	//If your role = user and you are trying to access the admin dashboard
	//localhost:9092/admin This page will encounter
	@RequestMapping(value = "accessDenied", method = RequestMethod.GET)
	public String accessDenied() {
	
		return "dashboard/access-denied";
	}

	
	
	@RequestMapping(value = "register")
	public String register(ModelMap modelMap) {
		
		return "dashboard/user-register";
		
	}

	
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String createUser(@Valid @ModelAttribute User user, BindingResult result, ModelMap modelMap,
			@RequestParam(name = "username", required = false) String username,RedirectAttributes redirectAttributes) {
		
		if (result.hasErrors()) {
			
			modelMap.put("error", "Something went wrong");
			return "dashboard/user-register";

		} else if(user.getId() == 0){
			User newUser = new User();
			userService.save(user);
			redirectAttributes.addAttribute("newUser", newUser).addFlashAttribute("msg","user has been registered successfully");
			
			return "redirect:login?AccountCreated/"+user.getId();
			
		}
		return null;
	}

	
	
		@RequestMapping(value="checkUsernameAvail" , method = RequestMethod.POST)
		@ResponseBody    //we are sending json data cause ajax access data in jason format
		public boolean CheckUsernameExists(@RequestParam String username)
		{
			User usernameExists = userService.findByUsername(username);
			
			if(usernameExists == null)
			{
				//Username already exists    cause remote function gives false for form.valid function instead of true
				return true;
			}
			else
			{
				return false;
			}
		}
		
		
		@RequestMapping(value="checkEmailAvail" , method = RequestMethod.POST)
		@ResponseBody    //we are sending json data cause ajax access data in jason format
		public boolean CheckEmailExists(@RequestParam String email)
		{
			User emailExists = userService.findByEmail(email);
			
			if(emailExists == null)
			{
				//Username already exists    cause remote function gives false for form.valid function instead of true
				return true;
			}
			else
			{
				return false;
			}
		}
	
	   
	    @GetMapping("/lock")
	    public String lock()
	    {
	    	return "dashboard/user-lock-screen";
	    }
		
	
}





