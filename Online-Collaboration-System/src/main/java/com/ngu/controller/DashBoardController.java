package com.ngu.controller;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.ngu.Model.CustomUserDetails;
import com.ngu.Model.User;
import com.ngu.Service.EmailService;
import com.ngu.Service.UserService;

@Controller
@RequestMapping(value="/dashboard")

public class DashBoardController {

	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@Autowired
	UserService userService;

	@Autowired
	EmailService emailService;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;



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
				return "dashboard/login";
			
		}
		
		else {
			return "dashboard/login";	
		}

	}
	

    @GetMapping("/lock")
    public String lock()
    {
    	return "dashboard/user-lock-screen";
    }
	
	
	@RequestMapping(value="process-lock",method =RequestMethod.POST)
	public String lock(HttpServletRequest request)
	{
		String username = request.getParameter("username");
		userService.loadUserByUsername(username);
		return "index";	
		
	}
	
//	@RequestMapping(value="logout",method =RequestMethod.POST)
//	public String logout(@RequestParam(value = "logout", required = false)String logout,ModelMap modelMap)
//	{
//		if (logout != null)
//		{
//				modelMap.put("logout", "You have been successfully logged out.");
//				modelMap.put("title","Home");
//				return "dashboard/login";
//			
//		}
//		else
//		{
//			return "dashboard/login";
//		}
//	}
	
	

	
	
	
	
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
	
		
		/**
		 * 
		 * Forgot password mechanism
		 * */
		
		
		
			@RequestMapping(value = "forgot-password", method = RequestMethod.GET)
			public String forgotPass(ModelMap modelMap) {
				return "dashboard/user-forgot-password";

			}

			@Autowired
		    private JavaMailSender javaMailSender;

			
			@Autowired
			private SpringTemplateEngine templateEngine;
			
			@RequestMapping(value = "forgot-password", method = RequestMethod.POST)
			public ModelAndView forgotPasswordForm(ModelAndView mv, @RequestParam("email") String email,
					HttpServletRequest request) throws MessagingException, IOException {

				Optional<User> optional = userService.findUserByEmail(email);

				if (!optional.isPresent()) {
					mv.addObject("error", "Account not found with the given Email ID...");
				} else {
					User user = optional.get();
					user.setResetToken(UUID.randomUUID().toString());
					userService.update(user);

					String appUrl = request.getScheme () + "://" + request.getServerName() + ":" + request.getServerPort();
					final Context context = new Context();
					context.setVariable("name", user.getFname()+ " "+user.getLname());
					context.setVariable("subscriptionDate", new Date());
					context.setVariable("appUrl", appUrl+"/dashboard/reset?token="+ user.getResetToken());
					context.setVariable("company", "MateClub");
					
					MimeMessage mimeMessage = javaMailSender.createMimeMessage();
					MimeMessageHelper message= new MimeMessageHelper(mimeMessage,true,"UTF-8");
					message.setSubject("Password Reset Request");
					message.setFrom("NeverGiveUp@gmail.com");
					message.setTo(user.getEmail());
					
					
					
					final String htmlContent = this.templateEngine.process("/user/email.html", context);
					message.setText(htmlContent, true);
					
					emailService.sendSimpleMessage(mimeMessage);
					mv.addObject("msg", "password reset link has been sent to your email "+user.getEmail()+". Please check it.");
				}
				mv.setViewName("dashboard/user-forgot-password");
				return mv;

			}

			@RequestMapping(value = "/reset", method = RequestMethod.GET)
			public ModelAndView displayResetPasswordPage(ModelAndView modelAndView, @RequestParam("token") String token) {

				Optional<User> user = userService.findByResetToken(token);

				if (user.isPresent()) {

					// Send This token as hidder field to reset password page
					// <input type="hidden" name="token" value="${resetToken}">
					modelAndView.addObject("resetToken", token);
					LOGGER.info("Token is ::::::::::::::::::::" + token);
				} else {
					modelAndView.addObject("error", "Oops!  This is an invalid password reset link.");
					LOGGER.info("Something went wrong here");
				}

				modelAndView.setViewName("dashboard/reset-password");
				return modelAndView;
			}

			@RequestMapping(value = "/reset", method = RequestMethod.POST)
			public ModelAndView setNewPassword(ModelAndView modelAndView, @RequestParam Map<String, String> requestParams,
					RedirectAttributes redir, HttpServletRequest request) {

				// Find the user by the reset token that we have sent from above link
				Optional<User> user = userService.findByResetToken(requestParams.get("token"));

				if (user.isPresent()) {

					User resetUser = user.get();

					// Set new password
					resetUser.setPassword(bCryptPasswordEncoder.encode(requestParams.get("password")));
					// resetUser.setPassword(requestParams.get("password"));
					LOGGER.info("Reseted Password is :::::::::::::::\n" + requestParams.get("password"));
					// Set the reset token to null so it cannot be used again
					resetUser.setResetToken(null);

					// Save user
					userService.update(resetUser);

					SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
					simpleMailMessage.setFrom("support@demo.com");
					simpleMailMessage.setTo(resetUser.getEmail());
					simpleMailMessage.setSubject("Password Reset Successfully");
					simpleMailMessage.setText("You have successfully reseted your password: \n" + "Username ::\t"
							+ resetUser.getUsername() + " \n Password ::\t" + requestParams.get("password"));
					emailService.SendEmail(simpleMailMessage);
					LOGGER.info("password reset link has been sent to your email " + resetUser.getEmail());

					// In order to set a model attribute on a redirect, we must use
					// RedirectAttributes
					redir.addFlashAttribute("msg", "You have successfully reset your password.  You may now login.");

					modelAndView.setViewName("redirect:/dashboard/login");
					return modelAndView;

				} else {
					modelAndView.addObject("error", "Oops!  This is an invalid password reset link.");
					modelAndView.setViewName("dashboard/reset-password");
				}

				return modelAndView;
			}

		
	   
	
}





