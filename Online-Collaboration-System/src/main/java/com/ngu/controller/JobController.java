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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ngu.Model.Forum;
import com.ngu.Model.Job;
import com.ngu.Model.User;
import com.ngu.Service.JobService;
import com.ngu.Service.UserService;

/**
 * @author SURAJ
 *@Date Mar 6, 2020
 */
@Controller
@RequestMapping("/job")
public class JobController
{
	
	@Autowired 
	private JobService jobService;
	
	@Autowired
	private UserService userService;

	@RequestMapping(value = {"","/"})
	public String getJobPage(Model model)
	{
		model.addAttribute("job", new Job());
		return "post/CreateJob";
	}
	
	@RequestMapping(value = "/save-job",method = RequestMethod.POST)
	public String CreateForum(@ModelAttribute Job job,BindingResult result,Model model,RedirectAttributes redirectAttributes)
	{
		
		if(result.hasErrors())
		{
			model.addAttribute("error", "Something went wrong");
			return "post/CreateForm";
		}
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findByUsername(authentication.getName());
		
		
		job.setUser(user);
		jobService.createJob(job);
		redirectAttributes.addFlashAttribute("msg", "Job Description created Successfully");
		return "redirect:/job";
	}
	
	@RequestMapping(value = "/single/{jobid}")
	public String getSingleJob(@PathVariable int jobid,Model model) {
		
		Job job =  jobService.findById(jobid);
		
		if(job != null)
		{
			model.addAttribute("job", job);
		}
		
		return "post/singleJob";
	}
	
	@RequestMapping(value = "/delete/{id}")
	public String deleteForum(@PathVariable int id) {
		jobService.deleteJob(id);
		return "redirect:/job?deleted"+id; 
	}
	
	@RequestMapping(value = "/{id}")
	public String getUsersJobs(@PathVariable int id,Model model)
	{
		
		Optional<User> user = userService.findById(id);
		if(user.isPresent()) {
		
			User existsUser = user.get();
			
			model.addAttribute("user", existsUser.getId());
			List<Job> jobs = jobService.findAllJobsOrderByDesc();
			
			if(jobs.isEmpty()) {
				model.addAttribute("msg", "Job Description List Data not found");
			}
			model.addAttribute("jobs", jobs);
		}
		
		return "post/myJobs";
		
	}
	
	
}
