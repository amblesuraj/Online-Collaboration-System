/**
 * 
 */
package com.ngu.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ngu.Model.Like;

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
}
