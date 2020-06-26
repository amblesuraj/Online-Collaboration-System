/**
 * 
 */
package com.ngu.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ngu.Model.Blog;
import com.ngu.Model.Forum;
import com.ngu.Model.Post;
import com.ngu.Model.User;
import com.ngu.Service.ForumService;
import com.ngu.Service.UserService;

/**
 * @author SURAJ
 *@Date Mar 6, 2020
 */
@Controller
@RequestMapping("/forum")
public class ForumController
{

	@Autowired
	private ForumService forumService; 
	
	@Autowired
	private UserService userService;
	
	@GetMapping
	public String getForumPage(Model model)
	{
		model.addAttribute("forum", new Forum());
		return "post/CreateForum";
	}
	
	@RequestMapping(value = "/save-forum",method = RequestMethod.POST)
	public String CreateForum(@ModelAttribute Forum forum,BindingResult result,Model model)
	{
		
		if(result.hasErrors())
		{
			model.addAttribute("error", "Something went wrong");
			return "post/CreateForm";
		}
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findByUsername(authentication.getName());
		
		
		forum.setUser(user);
		
		forumService.createForum(forum);
		model.addAttribute("msg", "Forum Created Successfully");
		return "redirect:/forum";
	}
	
	@RequestMapping(value = "/single/{forumid}")
	public String getSingleForum(@PathVariable int forumid,Model model) {
		
		Forum forum =  forumService.FindById(forumid);
		
		if(forum != null)
		{
			model.addAttribute("forum", forum);
		}
		
		return "post/singleForum";
	}
	
	@RequestMapping(value = "/delete/{id}")
	public String deleteForum(@PathVariable int id) {
		forumService.deleteById(id);
		
		return "redirect:/forum?deleted"+id; 
	}
	
	@RequestMapping(value = "/{id}")
	public String getUsersForums(@PathVariable int id,Model model)
	{
		
		Optional<User> user = userService.findById(id);
		if(user.isPresent()) {
		
			User existsUser = user.get();
			
			model.addAttribute("user", existsUser.getId());
			List<Forum> forums = forumService.findAllForumsOrderByDesc();
			
			if(forums.isEmpty()) {
				
				model.addAttribute("msg", "Forum Data Not Found");
			}
			
			
			model.addAttribute("forums", forums);
		}
		
		return "post/myForum";
		
	}
	
}
