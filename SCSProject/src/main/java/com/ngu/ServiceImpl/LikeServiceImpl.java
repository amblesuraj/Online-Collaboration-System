/**
 * 
 */
package com.ngu.ServiceImpl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ngu.Model.Like;
import com.ngu.Repositories.LikeRepository;
import com.ngu.Service.LikeService;

/**
 * @author SURAJ
 *@Date May 22, 2020
 */
@Service
@Transactional
public class LikeServiceImpl implements LikeService
{

	@Autowired
	private LikeRepository likeRepository;
	
	@Override
	public Like addPostLike(Like like)
	{
		
		return likeRepository.save(like);
	}

	@Override
	public void removePostLike(Like like)
	{
		likeRepository.delete(like);
	}

	@Override
	public List<Like> findByUserIdAndPost(int UserId,int PostId)
	{
		// TODO Auto-generated method stub
		return findByUserIdAndPost(UserId,PostId);
	}

}
