/**
 * 
 */
package com.ngu.ServiceImpl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ngu.Model.Job;
import com.ngu.Model.Post;
import com.ngu.Repositories.JobRepository;
import com.ngu.Service.JobService;

/**
 * @author SURAJ
 *@Date Mar 6, 2020
 */
@Service
@Transactional
public class JobServiceImpl implements JobService
{

	@Autowired
	private JobRepository jobRepository;
	
	@Override
	public Job createJob(Job job)
	{
		// TODO Auto-generated method stub
		return jobRepository.save(job);
	}

	@Override
	public Job UpdateJob(Job job)
	{
		// TODO Auto-generated method stub
		return jobRepository.save(job);
	}

	@Override
	public Job findById(int id)
	{
		// TODO Auto-generated method stub
		return jobRepository.getOne(id);
	}

	@Override
	public void deleteJob(int id)
	{
		jobRepository.deleteById(id);
		
	}

	@Override
	public List<Job> findAllJobsOrderByDesc() {
		// TODO Auto-generated method stub
		return jobRepository.findAllByOrderByIdDesc();
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return jobRepository.count();
	}

}
