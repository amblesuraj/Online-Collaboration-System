/**
 * 
 */
package com.ngu.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ngu.Enum.NotificationStatus;
import com.ngu.Model.Like;
import com.ngu.Model.Notification;
import com.ngu.Model.Post;
import com.ngu.Model.User;
import com.ngu.Service.LikeService;
import com.ngu.Service.NotificationService;
import com.ngu.Service.PostService;
import com.ngu.Service.UserService;

/**
 * @author SURAJ
 * @Date May 22, 2020
 */

@RestController
public class LikeRestController {

	@Autowired
	private LikeService likeService;

	@Autowired
	private PostService postService;

	@Autowired
	UserService userService;

	@Autowired
	NotificationService notificationService;

	@RequestMapping(value = "/addlike/{postId}/{userId}", method = RequestMethod.POST)
	public String addLikeToPost(@PathVariable int postId, @PathVariable int userId) {
		Post post = postService.getPostById(postId);
		
			
			Like like = new Like();

			like.setPost(post);
			like.setAction("like");
			like.setUser(post.getUser());
			likeService.addPostLike(like);
		
		Optional<User> existinguser = userService.findById(userId);
		if (existinguser.isPresent()) {
			User user = existinguser.get();
			Notification notification = new Notification();
			notification.setType("like");
			Date d = new Date();

			System.out.println(d);

			notification.setDate(d);
			notification.setStatus(NotificationStatus.UNREAD);
			notification.setMessage(user.getUsername() + " liked the post. ");
			notification.setUser(user);
			notification.setPost(post);
			notificationService.save(notification);

		}

		return "added";
	}

	@RequestMapping(value = "/deletelike/{postId}/{userId}", method = RequestMethod.POST)
	public String deleteLike(@PathVariable int postId, @PathVariable int userId) {

		Like like = likeService.findByPostIdAndUserId(postId, userId);

		if (like != null) {
			Optional<Notification> existnotification = notificationService.findByPostIdAndUserId(postId, userId);
			if (existnotification.isPresent()) {

				Notification notification = existnotification.get();

				notificationService.DeleteById(notification.getId());
			}
			likeService.removePostLike(like);
			return "deleted";
		}

		return "notdeleted";

	}

	@RequestMapping(value = "/getlikes/{postId}", method = RequestMethod.GET)
	public long getLikes(@PathVariable int postId) {
		
		long likes = likeService.countByPostIdAndaction(postId, "like");

		return likes;
	}

	@RequestMapping(value = "/userExists/{postId}/{userId}", method = RequestMethod.GET)
	// we are sending json data cause ajax access data in jason format
	public boolean CheckUserLikeExists(@PathVariable int postId, @PathVariable int userId) {
		Like like = likeService.findByPostIdAndUserId(postId, userId);

		if (like != null) {
			// Username already exists cause remote function gives false for form.valid
			// function instead of true
			return true;
		} else {
			return false;
		}
	}

	@PostMapping(value = "/notification/delete/{id}")
	public void deleteNoficationById(@PathVariable int id) {

		notificationService.DeleteById(id);

	}

	@PostMapping(value = "/deleteAll")
	public void deleteAll() {
		notificationService.DeleteAll();
	}

	@PostMapping(value = "/readAll")
	public String readAll() {

		List<Notification> notifications = notificationService.findByStatus(NotificationStatus.UNREAD);

		for (Notification notification : notifications) {

			notification.setStatus(NotificationStatus.READ);
			notificationService.save(notification);
		}

		return "success";
	}

//	@GetMapping("/notifications")
//	public List<Notification> getNotifications() {
//
//		List<Notification> notifications = notificationService.findTop5ByOrderByIdDesc();
//
//		User user = userService.getUserFromSession();
//
//		int size = notifications.size();
//		String output = "";
//		if (size > 0) {
//			for (Notification notification : notifications) {
//
//				List<Notification> newNotifications = new ArrayList<Notification>();
//
//				Notification notify = new Notification();
//
//				notify.setDate(notification.getDate());
//				System.out.println(notification.getDate());
//				notify.setStatus(notification.getStatus());
//				notify.setPost(notification.getPost());
//				notify.setType(notification.getType());
//				notify.setUser(notification.getUser());
//
//				String username = "";
//
//				if (notification.getUser().getId() == user.getId()) {
//					username = "<li><a class='grey-text text-darken-2' href='#!'><span class='material-icons icon-bg-circle red small'>favorite</span>You have";
//				}
//
//				else {
//					username = "<li><a class='grey-text text-darken-2' href='#!'><span class='material-icons icon-bg-circle red small'>favorite</span>"
//							+ notification.getUser().getUsername() + "</a> has";
//				}
//
//				
//				
//				notify.setMessage(username);
//				newNotifications.add(notify);
//
//				notifications = newNotifications;
//
//			}
//		} else {
//			output = "No notifications found";
//		}
//
//		return notifications;
//	}

	@GetMapping("/notifications")
	public List<Notification> getNotifications() {
		
		User user = userService.getUserFromSession();
		
		List<Notification> notifications = notificationService.findByUserIdAndStatus(user.getId(), NotificationStatus.UNREAD);
		
		List<Notification> newNotifications  = new ArrayList<Notification>();
		
		int size = notifications.size();
		
		if(size > 0 ) {
			
			for (Notification notification : newNotifications) {
				
				
				Notification notify = new Notification();
				if(notification.getUser().getId() == user.getId())
				{
					notify.setMessage("You have Liked your post");
				}
				else {
					notify.setMessage(notification.getUser().getUsername()+" has Liked your post");
				}
				
				notify.setDate(new Date());
				newNotifications.add(notify);
				
				notifications = newNotifications;
			}
		}
		
		return notifications;
	}

	@GetMapping(value = "/noOfunreadNotifications")
	public long noOfUnreadNotifications() {

		return notificationService.countByStatus(NotificationStatus.UNREAD);
	}

}
