package com.ngu;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.ngu.Model.Like;
import com.ngu.Model.Notify;
import com.ngu.Repositories.LikeRepository;

@EnableJpaRepositories(basePackages = "com.ngu.Repositories")
@EntityScan("com.ngu.Model")
@SpringBootApplication(scanBasePackages = { "com.ngu" })
@EnableAutoConfiguration

public class SpringBootThymeleafProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootThymeleafProjectApplication.class, args);
	}
	
	
	@Autowired
	private LikeRepository likeRepository;
	
	
	@PostConstruct	
	public void getLikesList()
	{
		List<Notify> likes =  likeRepository.getLikesAsNorifications();
		if(!likes.isEmpty()) {
			
			for (Notify notify : likes) {
				System.out.println("User Id == >"+ notify.getUserid());
				System.out.println("User Id From Like == >"+ notify.getLike_userId());
				System.out.println("Username == >"+ notify.getUsername());
				
				System.out.println("Post Id == >"+ notify.getPostid());
//				System.out.println("Post Id From Like== >"+ notify.getLike_postid());
				System.out.println("Caption == >"+ notify.getCaption());
				
				
			}
				
		
		}
		else
		{
			System.out.println("No Likes Found");
		}
	}
}
