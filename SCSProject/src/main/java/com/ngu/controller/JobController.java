/**
 * 
 */
package com.ngu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ngu.Model.Job;
import com.ngu.Model.Post;
import com.ngu.Service.JobService;

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

	@RequestMapping(value = "/")
	public String getJobPage(Model model)
	{
		model.addAttribute("job", new Job());
		return "CreateJob";
	}
	
	@RequestMapping(value = "/create",method = RequestMethod.POST)
	public String CreateForum(@ModelAttribute Job job,BindingResult result,Model model)
	{
		
		if(result.hasErrors())
		{
			model.addAttribute("error", "Something went wrong");
			return "CreateForm";
		}
		
		jobService.createJob(job);
		model.addAttribute("msg", "Forum Created Successfully");
		return "redirect:/";
	}
	
	@RequestMapping(value = "/delete/{id}")
	public String deleteForum(@PathVariable int id) {
		jobService.deleteJob(id);
		return "redirect:/forum?deleted"+id; 
	}
	
	@RequestMapping(value = "/user/job/{id}")
	public String getUsersPosts(@PathVariable int id,Model model)
	{
		List<Job> jobs = jobService.findAllJobsOrderByDesc();
		model.addAttribute("jobs", jobs);
		return "AllJobs";
	}
	
	
}
