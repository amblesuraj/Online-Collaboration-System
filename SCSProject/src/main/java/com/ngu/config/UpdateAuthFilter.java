/**
 * 
 */
package com.ngu.config;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ngu.Model.CustomUserDetails;
import com.ngu.Model.Role;
import com.ngu.Model.UpdateUserSessionMessage;
import com.ngu.Model.User;
import com.ngu.Service.UserService;

/**
 * @author SURAJ
 *@Date Feb 9, 2020
 */
@Component
@Order(UpdateAuthFilter.ORDER_AFTER_SPRING_SESSION)
public class UpdateAuthFilter extends OncePerRequestFilter
{
public static final int ORDER_AFTER_SPRING_SESSION = -2147483597;

private Logger log = LoggerFactory.getLogger(this.getClass());

private Set<String> permissionsToUpdate = new HashSet<>();

@Autowired
private UserService userService;

private void modifySessionSet(String sessionKey, boolean add)
{
    if (add) {
        permissionsToUpdate.add(sessionKey);
    } else {
        permissionsToUpdate.remove(sessionKey);
    }
}

public void addUserSessionsToSet(UpdateUserSessionMessage updateUserSessionMessage)
{
    log.info("UPDATE_USER_SESSION - {} - received", updateUserSessionMessage.getUuid().toString());
    updateUserSessionMessage.getSessionKeys().forEach(sessionKey -> modifySessionSet(sessionKey, true));
    //clear keys for sessions not in redis
    log.info("UPDATE_USER_SESSION - {} - success", updateUserSessionMessage.getUuid().toString());
}

@Override
public void destroy()
{

}

@Override
protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException
{
    HttpSession session = httpServletRequest.getSession();

    
    
if (session != null)
{
    String sessionId = session.getId();
    if (permissionsToUpdate.contains(sessionId))
    {
        try
        {
            SecurityContextImpl securityContextImpl = (SecurityContextImpl) session.getAttribute("SPRING_SECURITY_CONTEXT");
            if (securityContextImpl != null)
            {
                Authentication auth = securityContextImpl.getAuthentication();

                 User user  = userService.findByUsername(auth.getName());
                 			
                if (user != null )
                {
                  
                	Set<GrantedAuthority> authorities = new HashSet<>();
                    for (Role role : user.getRoles())
					{
						authorities.add(new SimpleGrantedAuthority(role.getName()));
					}
                    CustomUserDetails myCustomUser = new CustomUserDetails(auth.getName(),
                                                                 null,auth.getAuthorities()
                                                                 );
                    

                    final Authentication newAuth = new UsernamePasswordAuthenticationToken(myCustomUser,authorities);
                    SecurityContextHolder.getContext().setAuthentication(newAuth);
                    
                  
                	RequestContextHolder.currentRequestAttributes().setAttribute("SPRING_SECURITY_CONTEXT",newAuth,RequestAttributes.SCOPE_SESSION);
                	RequestContextHolder.currentRequestAttributes().setAttribute("userModel",newAuth,RequestAttributes.SCOPE_SESSION);
                    httpServletRequest.getSession().setAttribute("userModel", newAuth);
                    httpServletRequest.getSession().setAttribute("SPRING_SECURITY_CONTEXT", newAuth);
                
                			
                }
                else
                {
                    //invalidate the session if the user could not be found
                    session.invalidate();
                }
                
            }
            else
            {
                //invalidate the session if the user could not be found
                session.invalidate();
            }
        }
        finally
        {
            modifySessionSet(sessionId, false);
        }
    }
}

filterChain.doFilter(httpServletRequest, httpServletResponse);
}
}
