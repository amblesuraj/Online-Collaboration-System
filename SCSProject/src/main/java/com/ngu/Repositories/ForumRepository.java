/**
 * 
 */
package com.ngu.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ngu.Model.Forum;

/**
 * @author SURAJ
 *@Date Mar 6, 2020
 */
public interface ForumRepository extends JpaRepository<Forum, Integer>
{

}
