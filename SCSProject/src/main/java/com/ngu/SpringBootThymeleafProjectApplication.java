package com.ngu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.ngu.Repositories")
@EntityScan("com.ngu.Model")
@SpringBootApplication(scanBasePackages = { "com.ngu" })
@EnableAutoConfiguration

public class SpringBootThymeleafProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootThymeleafProjectApplication.class, args);
	}

}
