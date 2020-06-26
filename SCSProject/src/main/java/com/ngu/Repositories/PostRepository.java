/**
 * 
 */
package com.ngu.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ngu.Model.Post;

/**
 * @author SURAJ
 *@Date Nov 30, 2019
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Integer>{

	List<Post> findAllByOrderByIdDesc();

	List<Post> findTop4ByOrderByIdDesc();

//	List<Post> findByIdAndCreatedByOrderByIdDesc(); 
}
