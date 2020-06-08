/**
 * 
 */
package com.ngu.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ngu.Model.Like;
import java.lang.Integer;
import java.util.List;

/**
 * @author SURAJ
 *@Date May 22, 2020
 */
@Repository("likeRepository")
public interface LikeRepository extends JpaRepository<Like, Integer>
{

	List<Like> findByUserIdAndPost(int UserId,int PostId);
}
