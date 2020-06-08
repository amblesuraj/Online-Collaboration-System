/**
 * 
 */
package com.ngu.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ngu.Model.Like;
import com.ngu.Model.Post;
import com.ngu.Service.LikeService;
import com.ngu.Service.PostService;

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
	
	@RequestMapping(value = "like/{postId}/{userId}",method = RequestMethod.POST)
	public ResponseEntity<?> addLikeToPost(@PathVariable int postId,@PathVariable int userId)
	{
		Post post = postService.getPostById(postId);
		
		Like like = new Like();
		
		like.setPost(post);
		like.setAction("like");
		like.setUserId(userId);
		
		likeService.addPostLike(like);
		
		return (ResponseEntity<?>) likeService.findByUserIdAndPost(userId, postId);
	}
	
}
