/**
 * 
 */
package com.ngu.Service;

import java.util.List;

import com.ngu.Model.Like;

/**
 * @author SURAJ
 *@Date May 22, 2020
 */
public interface LikeService
{

	public Like addPostLike(Like like);
	
	public void removePostLike(Like like);
	
	List<Like> findByUserIdAndPost(int UserId,int PostId);
}
