package com.ngu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ngu.Model.User;
import com.ngu.Service.BlogService;
import com.ngu.Service.ForumService;
import com.ngu.Service.JobService;
import com.ngu.Service.PostService;
import com.ngu.Service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	UserService userService;

	@Autowired
	PostService postService;
	
	@Autowired
	ForumService forumService; 
	
	@Autowired
	BlogService blogService;
	
	@Autowired
	JobService jobService;
	
	@ModelAttribute("user")
	public User getUser() {
		return new User();
	}
	
	//Admin dashbard Index page
	@RequestMapping(method = RequestMethod.GET)
	public String index(Model model) {
		model.addAttribute("isDashboard", true);
		model.addAttribute("usercount", userService.count());
		model.addAttribute("users", userService.findAllUsers());
		return "admin/dashboard";

	}
	@RequestMapping(method = RequestMethod.GET,value = "dashboard-ecommerce")
	public String dashboard_Ecommerce(Model model) {
		
		model.addAttribute("isDashboardecommerce", true);
		model.addAttribute("postcount", postService.count());
		model.addAttribute("blogcount", blogService.count());
		model.addAttribute("jobcount", jobService.count());
		model.addAttribute("forumcount", forumService.count());
		
		return "admin/dashboard-ecommerce";

	}
	
	@RequestMapping(method = RequestMethod.GET,value = "allPosts")
	public String AllPosts(Model model) {
		
		model.addAttribute("isAllPosts", true);
		model.addAttribute("posts", postService.findAllPostsOrderByDesc());
		model.addAttribute("postcount", postService.count());
		model.addAttribute("blogcount", blogService.count());
		model.addAttribute("jobcount", jobService.count());
		model.addAttribute("forumcount", forumService.count());
		return "admin/AllPosts";

	}
	
	@RequestMapping(method = RequestMethod.GET,value = "allBlogs")
	public String AllBlogs(Model model) {
		
		model.addAttribute("isAllBlogs", true);
		model.addAttribute("blogs", blogService.findAllBlogsOrderByDesc());
		model.addAttribute("blogcount", blogService.count());
		model.addAttribute("jobcount", jobService.count());
		model.addAttribute("forumcount", forumService.count());
		model.addAttribute("postcount", postService.count());
		return "admin/AllBlogs";

	}
	
	@RequestMapping(method = RequestMethod.GET,value = "allJobs")
	public String AllJobs(Model model) {
		
		model.addAttribute("isAllJobs", true);
		model.addAttribute("jobs", jobService.findAllJobsOrderByDesc());
		model.addAttribute("jobcount", jobService.count());
		model.addAttribute("blogcount", blogService.count());
		model.addAttribute("forumcount", forumService.count());
		model.addAttribute("postcount", postService.count());
		
		return "admin/AllJobs"; 

	}
	
	@RequestMapping(method = RequestMethod.GET,value = "allForums")
	public String AllForum(Model model) {
		
		model.addAttribute("isAllForums", true);
		model.addAttribute("forums", forumService.findAllForumsOrderByDesc());
		model.addAttribute("forumcount", forumService.count());
		model.addAttribute("postcount", postService.count());
		model.addAttribute("blogcount", blogService.count());
		model.addAttribute("jobcount", jobService.count());
		
		return "admin/AllForums";

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
