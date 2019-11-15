package com.ngu.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import com.ngu.Service.UserService;



@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	
	@Autowired
	private UserService userService;
		
		@Autowired
		private DataSource dataSource;
		
		

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.cors().and().csrf().disable();
		
			http.authorizeRequests()
			
			.antMatchers("/","/index","/","/home","/user/**").permitAll()
			.antMatchers("/dashboard/**").permitAll() 
			.antMatchers("/resources/**","/static/**").permitAll()	
			.antMatchers("/superadmin/**").hasAuthority("SUPERADMIN")
			.antMatchers("/admin/**").hasAnyAuthority("SUPERADMIN","ADMIN")
			.antMatchers("/employee/**").hasAnyAuthority("SUPERADMIN","ADMIN","EMPLOYEE")
			.antMatchers("/cart/**").hasAnyAuthority("SUPERADMIN","ADMIN","EMPLOYEE","USER")
			.antMatchers("/anonymous*","/user/**").permitAll()
			
			//.anyRequest().authenticated()
			.and()
			.formLogin().loginPage("/dashboard/login")
			.loginProcessingUrl("/dashboard/process-login")
			.defaultSuccessUrl("/index")
			.successHandler(new CustomLoginSuccessHandler())
			.failureUrl("/dashboard/login?error")
			.usernameParameter("username").passwordParameter("password")
			.and()
			.logout()
			//.logoutUrl("/dashboard/logout")
			.logoutRequestMatcher(new AntPathRequestMatcher("/dashboard/logout"))
			.logoutSuccessHandler(new CustomLogoutHandler())
			.clearAuthentication(true)
			.deleteCookies("JSESSIONID")
			.invalidateHttpSession(true)
			.and()			
			.rememberMe().rememberMeParameter("remember-me")
			.tokenRepository(persistentTokenRepository())
			.alwaysRemember(true).tokenValiditySeconds(2592000)
			.and()
			.exceptionHandling().accessDeniedPage("/dashboard/accessDenied");
		}
		
		@Bean
		public PersistentTokenRepository persistentTokenRepository()
		{
			JdbcTokenRepositoryImpl jdbcTokenRepositoryImpl = new JdbcTokenRepositoryImpl();
			jdbcTokenRepositoryImpl.setDataSource(dataSource);
			return jdbcTokenRepositoryImpl;
		}

		@Bean
	    public BCryptPasswordEncoder bCryptPasswordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
		
	    @Bean
	    public AuthenticationManager customAuthenticationManager() throws Exception {
	        return authenticationManager();
	    }

	    @Autowired
	    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder());
	    }


		@Bean
		public MultipartResolver multipartResolver()
		{
			return new StandardServletMultipartResolver();
		}
		

}
