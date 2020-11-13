/**
 * 
 */
package com.ngu.ServiceImpl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ngu.Enum.NotificationStatus;
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
	public long countByPostIdAndaction(int PostId,String action) {
		// TODO Auto-generated method stub
		return likeRepository.countByPostIdAndAction(PostId,action);
	}

	@Override
	public Optional<Like> findByUserId(int UserId) {
		// TODO Auto-generated method stub
		return likeRepository.findById(UserId);
	}

	@Override
	public List<Like> getLikes() {
		// TODO Auto-generated method stub
		return likeRepository.findAll();
	}

	@Override
	public Like findByPostIdAndUserId(int postId, int userId) {
		// TODO Auto-generated method stub
		return likeRepository.findByPostIdAndUserId(postId, userId);
	}

	@Override
	public void removeLike(int likeid) {
		// TODO Auto-generated method stub
		likeRepository.deleteById(likeid);
	}

	@Override
	public List<Like> findByUserIdAndAction(int userId, NotificationStatus action) {
		// TODO Auto-generated method stub
		return likeRepository.findByUserIdAndAction(userId, action);
	}

}
