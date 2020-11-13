package com.ngu.controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ngu.Model.Like;
import com.ngu.Model.Post;
import com.ngu.Model.User;
import com.ngu.Service.LikeService;
import com.ngu.Service.PostService;
import com.ngu.Service.UserService;

@Controller

public class pageController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private PostService postService;
	
//	@Autowired
//	private NotificationService notificationService;
	
	@Autowired
	LikeService  likeService;
	@RequestMapping(value = { "", "/", "index", "home" }, method = RequestMethod.GET)
	public String index(Model model,@RequestParam Map<String,String> requestParams,HttpServletRequest request,Principal principal) {
		model.addAttribute("isUserLayout", true);
		
		List<User> allusers = userService.findTop4ByOrderByIdDesc();
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findByUsername(principal.getName());
		List<User> usersWthoutCurrentUser = allusers.stream()
				.filter(u-> u.getId() != user.getId())
				.collect(Collectors.toList());
		
		model.addAttribute("users", usersWthoutCurrentUser);
		List<Post> posts = postService.findAllPostsOrderByDesc();
		model.addAttribute("Posts", posts);
		
//		for(Like like: likeService.getLikes())
//		{
//			model.addAttribute("likePostID", like.getPost().getId());
//			model.addAttribute("likeUserID", like.getUserId());
//		}
		
		model.addAttribute("like", likeService.getLikes());
		return "index";
	}

	@ModelAttribute("likes")
	public List<Like> getLikes()
	{
		return likeService.getLikes();
	}
	
	
//	@ModelAttribute("notifications")
//	public List<Notification> getNotifications(){
//		
//	List<Notification> unread = notificationService.findByStatus(NotificationStatus.UNREAD);
//		
//		return unread;
//	}
//	
//	@ModelAttribute("notificationsCount")
//	public long countUnreadNotifications(){
//	
//		List<Notification> notifications = notificationService.findByStatus(NotificationStatus.UNREAD);
//		
//		long unread = notifications.stream().count();		
//		if(unread == 0)
//		{
//			
//			return 0;
//		}
//		return unread;
//	}
	
}
