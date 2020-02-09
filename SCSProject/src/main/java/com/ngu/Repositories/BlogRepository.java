/**
 * 
 */
package com.ngu.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ngu.Model.Blog;

/**
 * @author SURAJ
 *@Date Jan 26, 2020
 */
@Repository
public interface BlogRepository extends JpaRepository<Blog, Integer>
{

}
