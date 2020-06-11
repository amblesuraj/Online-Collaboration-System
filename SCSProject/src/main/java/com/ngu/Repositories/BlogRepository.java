/**
 * 
 */
package com.ngu.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ngu.Model.Blog;
import com.ngu.Model.Post;

/**
 * @author SURAJ
 *@Date Jan 26, 2020
 */
@Repository
public interface BlogRepository extends JpaRepository<Blog, Integer>
{

	List<Blog> findAllByOrderByIdDesc();

}
