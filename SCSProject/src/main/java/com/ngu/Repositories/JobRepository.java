/**
 * 
 */
package com.ngu.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ngu.Model.Job;
import com.ngu.Model.Post;

/**
 * @author SURAJ
 *@Date Mar 6, 2020
 */

public interface JobRepository extends JpaRepository<Job, Integer>
{

	List<Job> findAllByOrderByIdDesc();

}
