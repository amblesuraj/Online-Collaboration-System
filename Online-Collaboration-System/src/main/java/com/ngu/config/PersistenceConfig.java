/**
 * 
 */
package com.ngu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.ngu.ServiceImpl.AuditorAwareImpl;

/**
 * @author SURAJ
 *@Date Nov 26, 2019
 */
@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class PersistenceConfig {
	
	@Bean
	AuditorAware<String> auditorAware(){
		return new AuditorAwareImpl();
	}

}
