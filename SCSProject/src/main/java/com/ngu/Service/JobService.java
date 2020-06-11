/**
 * 
 */
package com.ngu.Service;

import java.util.List;

import com.ngu.Model.Job;

/**
 * @author SURAJ
 *@Date Mar 6, 2020
 */
public interface JobService
{

	public Job createJob(Job job);
	
	public Job UpdateJob(Job job);
	
	public Job findById(int id);
	
	public void deleteJob(int id);

	public List<Job> findAllJobsOrderByDesc();
}
