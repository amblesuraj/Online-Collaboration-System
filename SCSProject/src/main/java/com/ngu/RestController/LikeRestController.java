/**
 * 
 */
package com.ngu.RestController;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ngu.Model.Like;
import com.ngu.Model.Post;
import com.ngu.Model.User;
import com.ngu.Service.LikeService;
import com.ngu.Service.PostService;
import com.ngu.Service.UserService;

/**
 * @author SURAJ
 *@Date May 22, 2020
 */

@RestController
public class LikeRestController
{

	@Autowired
	private LikeService likeService;
	
	@Autowired
	private PostService postService;
	
	@Autowired
	UserService userService;
	
	@RequestMapping(value = "addlike/{postId}/{userId}",method = RequestMethod.POST)
	public Object addLikeToPost(@PathVariable int postId,@PathVariable int userId)
	{
//		Post post = postService.getPostById(postId);
		
			Like like = new Like();
			
			like.setPostId(postId);
			like.setAction("like");
			like.setUserId(userId);
			likeService.addPostLike(like);
		
		
		return  getLikes(postId);
	}
	
	
	@RequestMapping(value = "deletelike/{postId}/{userId}",method = RequestMethod.POST)
	public Object deleteLike(@PathVariable int postId,@PathVariable int userId)
	{

			Like like = likeService.findByPostIdAndUserId(postId, userId);
			
			likeService.removePostLike(like);
			
		return  getLikes(postId);
	}
	
	@RequestMapping(value = "/getlikes/{postId}",method = RequestMethod.GET)
	public long getLikes(@PathVariable int postId)
	{
		long likes = likeService.countByPostIdAndaction(postId, "like");
		
		return  likes;
	}
	
	
	@RequestMapping(value="userExists/{postId}/{userId}" , method = RequestMethod.GET)
	   //we are sending json data cause ajax access data in jason format
	public boolean CheckUserLikeExists(@PathVariable int postId,@PathVariable int userId)
	{
		Like like = likeService.findByPostIdAndUserId(postId, userId);
		
		if(like != null)
		{
			//Username already exists    cause remote function gives false for form.valid function instead of true
			return true;
		}
		else
		{
			return false;
		}
	}
	
}
