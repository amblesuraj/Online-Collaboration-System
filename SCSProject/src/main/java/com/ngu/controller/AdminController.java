package com.ngu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ngu.Model.User;
import com.ngu.Service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	UserService userService;

	@ModelAttribute("user")
	public User getUser() {
		return new User();
	}
	
	//Admin dashbard Index page
	@RequestMapping(method = RequestMethod.GET)
	public String index(Model model) {
		model.addAttribute("isDashboard", true);
		
		return "admin/dashboard";

	}
	@RequestMapping(method = RequestMethod.GET,value = "dashboard-ecommerce")
	public String dashboard_Ecommerce(Model model) {
		
		model.addAttribute("isDashboardecommerce", true);
		return "admin/dashboard-ecommerce";

	}
	
	@RequestMapping("/advance-ui-carousel")
	public String advance_ui_carousel() {
		return "admin/advance-ui-carousel";
	}
	
	@RequestMapping("/dashboard-analytics")
	public String dashboard_analytics(Model model) {
		model.addAttribute("isFooter", true);
		model.addAttribute("isDashboardanalytics", true);
		return "admin/dashboard-analytics";
	}
	@GetMapping("/dashboard1")
	public String dashboard1(Model model)
	{
		
		model.addAttribute("isDashboard1", true);
		return "admin/dashboard1";
	}
	
	@GetMapping("/dashboard2")
	public String dashboard2()
	{
		return "admin/dashboard2";
	}
	
	
	@GetMapping("/app-chat")
	public String appchat(Model model)
	{
		model.addAttribute("isAppChat", true);
		return "admin/app-chat";
	}

}
