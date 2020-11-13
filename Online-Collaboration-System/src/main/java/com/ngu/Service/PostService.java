/**
 * 
 */
package com.ngu.Service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ngu.Model.Post;

/**
 * @author SURAJ
 * @Date Nov 30, 2019
 */
public interface PostService {

	
	public void  updatePost(Post post);

	public List<Post> findAllPosts();

	public Post getPostById(int id);

	public void deletepostById(int id);

	/**
	 * @param post
	 */
	public void createPost(Post post);

	public List<Post> findAllPostsOrderByDesc();

	public long count();

	public List<Post> findTop4ByOrderByIdDesc();

	/**
	 * @param id
	 */
	

}
