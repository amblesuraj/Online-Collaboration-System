
package com.ngu.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ngu.Model.Blog;
import com.ngu.Model.Post;
import com.ngu.Model.User;
import com.ngu.Service.BlogService;
import com.ngu.Service.FileStorageService;
import com.ngu.Service.UserService;

/**
 * @author SURAJ
 *@Date Nov 4, 2019
 */
@Controller
@RequestMapping("/blog")
public class BlogController {
	
	@Autowired
	private BlogService blogService;
	@Autowired
	private FileStorageService fileStorageService;
	
	
	@Autowired
	private UserService userService;
	
	@GetMapping
	public String postPage(Model model) {
		model.addAttribute("blog", new Blog());
		return "post/CreateBlog";
	}
	
	

	@RequestMapping("/allBlogs")
	public String MyPost(Model model) {
		
		model.addAttribute("Blogs", blogService.findAllBlogs());
		return "post/myBlogs";
	}
		
	@RequestMapping(value = "/save-blog", method = RequestMethod.POST)
	public String CreateBlog(@ModelAttribute @Valid Blog blog,BindingResult result, @RequestParam(required = true, value = "blogImage") MultipartFile file,Model model,RedirectAttributes redirectAttributes )
			throws IOException {
		
		
		try
		{
			String fileName = fileStorageService.storeFile(file);
			String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/")
					.path(fileName).toUriString();
			
			
			//Employee employee = objectMapper.readValue(empJson, Employee.class);
			blog.setBlogImage(fileDownloadUri);
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User user = userService.findByUsername(authentication.getName());
			
			
			blog.setUser(user);
			blogService.createBlog(blog);
			redirectAttributes.addFlashAttribute("msg", "Blog created Successfully");
		}
		catch (Exception e) {
			System.out.println(e);
		}
		
		return "redirect:/blog";
	}
	
	@RequestMapping(value = "/single/{blogid}")
	public String getSingleBlog(@PathVariable int blogid,Model model) {
		
		Blog blog =  blogService.findById(blogid);
		
		if(blog != null)
		{
			model.addAttribute("blog", blog);
		}
		
		return "post/singleBlog";
	}
	
	@RequestMapping(value = "/delete/{id}")
	public String DeleteBlog(@PathVariable int id) {
		
		blogService.DeleteBlogById(id);
		return "redirect:/blog/?deleted"+id;
	}
	
	@RequestMapping(value = "/{id}")
	public String getUsersBlogs(@PathVariable int id,Model model)
	{
		
		Optional<User> user = userService.findById(id);
		if(user.isPresent()) {
		
			User existsUser = user.get();
			
			model.addAttribute("user", existsUser.getId());
			List<Blog> blogs = blogService.findAllBlogsOrderByDesc();
			
			if(blogs.isEmpty()) {
				
				model.addAttribute("msg", "Blogs Data Not Found");
			}
			
			model.addAttribute("blogs", blogs);
		}
		
		return "post/myBlogs";
		
	}

}
