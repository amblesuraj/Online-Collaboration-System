/**
 * 
 */
package com.ngu.Service;

import java.util.List;
import java.util.Optional;

import com.ngu.Enum.NotificationStatus;
import com.ngu.Model.Like;

/**
 * @author SURAJ
 *@Date May 22, 2020
 */
public interface LikeService
{

	public Like addPostLike(Like like);
	
	public void removePostLike(Like like);
	
	public void removeLike(int likeid);
	
	Optional<Like> findByUserId(int UserId);
	
	long countByPostIdAndaction(int PostId,String action);

	public List<Like> getLikes();
	
	Like findByPostIdAndUserId(int postId,int userId);
	
	List<Like> findByUserIdAndAction(int userId,NotificationStatus action);
}
