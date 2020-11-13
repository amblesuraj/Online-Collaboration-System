package com.ngu.config;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
	public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {
	 
	 
	    @Override
	    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
	 
	        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
	 
	        if (roles.contains("SUPERADMIN")) {
	            httpServletResponse.sendRedirect("/superadmin");
	        }
	        else if (roles.contains("ADMIN")) {
	            httpServletResponse.sendRedirect("/admin");
	        }
	        else if (roles.contains("EMPLOYEE") || roles.contains("USER")) {
	            httpServletResponse.sendRedirect("/index");
	        }
	        else
	        {
	            httpServletResponse.sendRedirect("/index");
	        }
	    }
	}