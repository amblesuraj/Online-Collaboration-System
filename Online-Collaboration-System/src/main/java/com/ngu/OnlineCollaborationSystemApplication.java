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
import com.ngu.Model.User;
import com.ngu.Repositories.LikeRepository;
import com.ngu.Repositories.UserRepository;
import com.ngu.Service.UserService;

@EnableJpaRepositories(basePackages = "com.ngu.Repositories")
@EntityScan("com.ngu.Model")
@SpringBootApplication(scanBasePackages = { "com.ngu" })
@EnableAutoConfiguration

public class OnlineCollaborationSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlineCollaborationSystemApplication.class, args);
	}
	
	
	@Autowired
	private LikeRepository likeRepository;
	
	@Autowired
	private UserService userService; 
	
	@PostConstruct	
	public void getLikesList()
	{
		
		
	}
}
