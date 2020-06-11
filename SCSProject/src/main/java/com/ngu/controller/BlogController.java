
package com.ngu.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
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
import com.ngu.Service.FileStorageService;
import com.ngu.Service.PostService;
import com.ngu.Service.BlogService;

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
	
	@GetMapping
	public String postPage(Model model) {
		model.addAttribute("blog", new Blog());
		return "post/CreateBlog";
	}
	
	

	@RequestMapping("/allBlogs")
	public String MyPost(Model model) {
		
		model.addAttribute("Blogs", blogService.findAllBlogs());
		return "post/AllPosts";
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
			blogService.createBlog(blog);
			
			
			redirectAttributes.addFlashAttribute("msg", "Blog created Successfully");
		}
		catch (Exception e) {
			System.out.println(e);
		}
		
		return "redirect:/post";
	}
	
	@RequestMapping(value = "/delete/{id}")
	public String DeleteBlog(@PathVariable int id) {
		
		blogService.DeleteBlogById(id);
		return "redirect:/blog?deleted"+id;
	}
	
	@RequestMapping(value = "/user/blog/{id}")
	public String getUsersPosts(@PathVariable int id,Model model)
	{
		List<Blog> blogs = blogService.findAllBlogsOrderByDesc();
		model.addAttribute("blogs", blogs);
		return "AllBlogs";
	}

}
