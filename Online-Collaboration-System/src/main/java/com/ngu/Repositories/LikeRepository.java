/**
 * 
 */
package com.ngu.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ngu.Enum.NotificationStatus;
import com.ngu.Model.Like;
import com.ngu.Model.Notify;

/**
 * @author SURAJ
 *@Date May 22, 2020
 */
@Repository("likeRepository")
public interface LikeRepository extends JpaRepository<Like, Integer>
{

	
		long countByPostIdAndAction(int postId,String action);
		
		Optional<Like> findByUserId(int userId);
		
		Like findByPostIdAndUserId(int postId,int userId);
		
//		@Query("SELECT l.user.username,l.user.id, l.user.profilePicture,l.post.id,l.post.caption, l.id FROM likes AS l WHERE l.user.id=?1 AND l.status=?2")
//		@Query("SELECT new com.ngu.Model.Notify(p.user.id,p.user.username,p.id,p.caption, l.post.id, l.user.id) FROM Post p ,Like l WHERE p.like= l")
//		List<Notify> getLikesAsNorifications();
		
		List<Like> findByUserIdAndAction(int userId,NotificationStatus action);
		
}
