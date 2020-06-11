package com.ngu;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.ngu.Model.Like;
import com.ngu.Repositories.LikeRepository;
import com.ngu.Repositories.UserRepository;

@EnableJpaRepositories(basePackages = "com.ngu.Repositories")
@EntityScan("com.ngu.Model")
@SpringBootApplication(scanBasePackages = { "com.ngu" })
@EnableAutoConfiguration

public class SpringBootThymeleafProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootThymeleafProjectApplication.class, args);
	}
	
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	LikeRepository likeRepository; 
	
	@PostConstruct
	public void commandLineRunner() {
		
		
		
		
	}

}
