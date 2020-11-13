/**
 * 
 */
package com.ngu.ServiceImpl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ngu.Model.Forum;
import com.ngu.Repositories.ForumRepository;
import com.ngu.Service.ForumService;

/**
 * @author SURAJ
 *@Date Mar 6, 2020
 */
@Service
@Transactional
public class ForumServiceImpl implements ForumService
{

	@Autowired
	private ForumRepository forumRepository; 
	
	@Override
	public Forum createForum(Forum forum)
	{
		// TODO Auto-generated method stub
		return forumRepository.save(forum);
	}

	@Override
	public Forum updateForum(Forum forum)
	{
		// TODO Auto-generated method stub
		return forumRepository.save(forum);
	}

	@Override
	public Forum FindById(int id)
	{
		// TODO Auto-generated method stub
		return forumRepository.getOne(id);
	}

	@Override
	public void deleteById(int id)
	{
			forumRepository.deleteById(id);
	}

	@Override
	public List<Forum> findAllForumsOrderByDesc() {
		// TODO Auto-generated method stub
		return forumRepository.findAllByOrderByIdDesc();
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return forumRepository.count();
	}

}
