/**
 * 
 */
package com.ngu.Service;

import java.util.List;

import com.ngu.Model.Blog;
import com.ngu.Model.Post;

/**
 * @author SURAJ
 *@Date Jan 26, 2020
 */
public interface BlogService
{

	

	List<Blog> findAllBlogs();
	
	Blog createBlog(Blog blog);
	
	 Blog findById(int id);
	 
	 void DeleteBlogById(int id);

	List<Blog> findAllBlogsOrderByDesc();
}
