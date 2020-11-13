/**
 * 
 */
package com.ngu.controller;

import java.io.IOException;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ngu.Enum.PostStatus;
import com.ngu.Model.Blog;
import com.ngu.Model.Like;
import com.ngu.Model.Post;
import com.ngu.Model.User;
import com.ngu.Service.FileStorageService;
import com.ngu.Service.LikeService;
import com.ngu.Service.PostService;
import com.ngu.Service.UserService;

/**
 * @author SURAJ
 *@Date Nov 28, 2019
 */

@Controller
@RequestMapping("/post")
public class PostController {

	@Autowired
	private PostService postService;
	@Autowired
	private FileStorageService fileStorageService;
	
	@Autowired
	private LikeService likeService;
	
	
	@Autowired
	private UserService userService;
	@GetMapping
	public String postPage(Model model) {
		model.addAttribute("post", new Post());
		return "post/CreatePost";
	}
	
	

//	@RequestMapping("/allPosts")
//	public String MyPost(Model model) {
//		
//		model.addAttribute("allPosts", postService.findAllPosts());
//		return "post/AllPosts";
//	}
		
	@RequestMapping(value = "/save-post", method = RequestMethod.POST)
	public String EmployeeCreate(@ModelAttribute @Valid Post post,BindingResult result, @RequestParam(required = true, value = "postImages") MultipartFile[] files,Model model,RedirectAttributes redirectAttributes )
			throws IOException {
		
		
		try
		{
			
			StringBuilder fileNames  = new StringBuilder();
			List<String> downloadUris = new ArrayList<>();
			
			
				for(MultipartFile file :files)
				{
					String fileName = fileStorageService.storeFile(file);
					String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/")
							.path(fileName).toUriString();	
					fileNames.append(fileName);
					
					downloadUris.add(fileDownloadUri);
					
					post.setPostImages(downloadUris);

				}
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
				User user = userService.findByUsername(authentication.getName());
				
				
				post.setUser(user);
				
				post.setStatus(PostStatus.PRIVATE);
				postService.createPost(post);
			
			
			redirectAttributes.addFlashAttribute("msg", "Post created Successfully");
		}
		catch (Exception e) {
			System.out.println(e);
		}
		
		return "redirect:/post";
	}
	
	@RequestMapping(value = "/single/{postid}")
	public String getSinglePost(@PathVariable int postid,Model model) {
		
		Post post =  postService.getPostById(postid);
		
		if(post != null)
		{
			model.addAttribute("post", post);
		}
		
		return "post/singlePost";
	}
	
	@RequestMapping(value = "/delete/{id}")
	public String DeletePost(@PathVariable int id)
	{
		postService.deletepostById(id);
		
		return "redirect:/post?Deleted"+id;
	}
	
	@RequestMapping(value = "/{id}")
	public String getUsersPosts(@PathVariable int id,Model model)
	{
		
		Optional<User> user = userService.findById(id);
		if(user.isPresent()) {
		
			User existsUser = user.get();
			
			model.addAttribute("user", existsUser.getId());
			List<Post> posts = postService.findAllPostsOrderByDesc();
			
			if(posts.isEmpty()) {
			
				model.addAttribute("msg", "Posts Data Not Found");
			}
			
			model.addAttribute("posts", posts);
		}
		
		return "post/myPosts";
		
	}
	
	
	
}
