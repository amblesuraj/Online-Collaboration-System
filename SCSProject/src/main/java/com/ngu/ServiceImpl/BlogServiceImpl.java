/**
 * 
 */
package com.ngu.ServiceImpl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ngu.Model.Blog;
import com.ngu.Repositories.BlogRepository;
import com.ngu.Service.BlogService;

/**
 * @author SURAJ
 *@Date Jan 26, 2020
 */
@Service
@Transactional
public class BlogServiceImpl implements BlogService
{

	@Autowired
	private BlogRepository blogRepository; 
	
	@Override
	public List<Blog> findAllBlogs()
	{
		// TODO Auto-generated method stub
		return blogRepository.findAll();
	}

	@Override
	public Blog createBlog(Blog blog)
	{
		// TODO Auto-generated method stub
		return blogRepository.save(blog);
	}

	@Override
	public Blog findById(int id)
	{
		// TODO Auto-generated method stub
		return blogRepository.getOne(id);
	}

	@Override
	public void DeleteBlogById(int id)
	{
	
		blogRepository.deleteById(id);
		
	}

}
