/**
 * 
 */
package com.ngu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ngu.Model.Forum;
import com.ngu.Service.ForumService;

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
	
	@RequestMapping("/")
	public String getForumPage(Model model)
	{
		model.addAttribute("forum", new Forum());
		return "CreateForum";
	}
	
	@RequestMapping(value = "/create",method = RequestMethod.POST)
	public String CreateForum(@ModelAttribute Forum forum,BindingResult result,Model model)
	{
		
		if(result.hasErrors())
		{
			model.addAttribute("error", "Something went wrong");
			return "CreateForm";
		}
		forumService.createForum(forum);
		model.addAttribute("msg", "Forum Created Successfully");
		return "redirect:/";
	}
	
	@RequestMapping(value = "/delete/{id}")
	public String deleteForum(@PathVariable int id) {
		forumService.deleteById(id);
		
		return "redirect:/forum?deleted"+id; 
	}
}
