package com.ngu.controller;

import java.io.IOException;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.ngu.Model.User;
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

	

	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public ModelAndView ShowEditInfo(@PathVariable int id) throws EntityNotFoundException {
		ModelAndView mv = new ModelAndView("user/Profile");

		Optional<User> currentUser = userService.findById(id);

		if (!currentUser.isPresent())
			throw new EntityNotFoundException();

		mv.addObject("user", currentUser);
		return mv;
	}

	@RequestMapping(value = "/profilepost", method = RequestMethod.POST)
	public String updateUser(@ModelAttribute("user") User user, ModelMap modelMap) {

		userService.update(user);

		modelMap.put("msg", "User Updated successfully");

		return "redirect:/user/profile";

	}

	// Footer Pages
	@RequestMapping(value = "profile", method = RequestMethod.GET)
	public String AdminPrivate1(User user, HttpSession session, ModelMap modelMap) {

		
		return "user/profile";

	}

	@RequestMapping(value = "policy", method = RequestMethod.GET)
	public String PrivacyPolicy(ModelMap modelMap) {
		modelMap.put("userClickPrivacyPlicy", true);
		return "index";

	}

	@RequestMapping(value = "terms", method = RequestMethod.GET)
	public String terms(ModelMap modelMap) {
		modelMap.put("userClickTerms", true);
		return "index";

	}

	// forgot password
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
			mv.addObject("errorMessage", "Account not found with the given Email ID...");
		} else {
			User user = optional.get();
			user.setResetToken(UUID.randomUUID().toString());
			userService.update(user);

			String appUrl = request.getScheme () + "://" + request.getServerName() + ":" + request.getServerPort();

//			SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
//			simpleMailMessage.setFrom("support@demo.com");
//			simpleMailMessage.setTo(user.getEmail());
//			simpleMailMessage.setSubject("Password Reset Request");
//			simpleMailMessage.setText("To reset your password, click the link below:\n" + appUrl + "/user/reset?token="
//					+ user.getResetToken());
//			emailService.SendEmail(simpleMailMessage);
//			mv.addObject("successMessage", "password reset link has been sent to your email " + email);
//			LOGGER.info("password reset link has been sent to your email " + email);
			
			final Context context = new Context();
			context.setVariable("name", user.getFname()+ " "+user.getLname());
			context.setVariable("subscriptionDate", new Date());
			context.setVariable("appUrl", appUrl+"/user/reset?token="+ user.getResetToken());
			context.setVariable("company", "NeverGiveUp");
//			context.setVariable("image", new ClassPathResource("/app-assets/images/user/2.jpg"));
			
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper message= new MimeMessageHelper(mimeMessage,true,"UTF-8");
			message.setSubject("Password Reset Request");
			message.setFrom("NeverGiveUp@gmail.com");
			message.setTo(user.getEmail());
			
			message.addAttachment("logo.png", new ClassPathResource("static/app-assets/images/logo/materialize-logo-big.png"));
			
			final String htmlContent = this.templateEngine.process("/user/email.html", context);
			message.setText(htmlContent, true);
			
			emailService.sendSimpleMessage(mimeMessage);
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
			modelAndView.addObject("errorMessage", "Oops!  This is an invalid password reset link.");
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
			modelAndView.addObject("errorMessage", "Oops!  This is an invalid password reset link.");
			modelAndView.setViewName("dashboard/reset-password");
		}

		return modelAndView;
	}

}
