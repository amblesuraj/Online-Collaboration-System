/**
 * 
 */
package com.ngu.ServiceImpl;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ngu.Model.CustomUserDetails;

/**
 * @author SURAJ
 * @Date Nov 26, 2019
 */
public class AuditorAwareImpl implements AuditorAware<String> {

	@Override
	public Optional<String> getCurrentAuditor() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
			if(authentication != null) {
		
			CustomUserDetails user= (CustomUserDetails) authentication.getPrincipal();

			return Optional.of(user.getFname() + " " + user.getLname());
			}
			else
			{
				return Optional.of("Suraj Amble"); 
			}
		

	}

}
