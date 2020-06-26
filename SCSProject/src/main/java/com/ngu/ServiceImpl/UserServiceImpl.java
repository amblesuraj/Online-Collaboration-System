package com.ngu.ServiceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ngu.Model.CustomUserDetails;
import com.ngu.Model.Profile;
import com.ngu.Model.Role;
import com.ngu.Model.User;
import com.ngu.Model.UserModel;
import com.ngu.Repositories.RoleRepository;
import com.ngu.Repositories.UserRepository;
import com.ngu.Service.UserService;
@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	HttpSession session;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	User user = null;
	
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
			if(user==null)
			{
				throw new UsernameNotFoundException("Username not found");
			}
			
			Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
	        for (Role role : user.getRoles()){
	            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
	                   
	        }
	        
	        CustomUserDetails customUserDetails = new CustomUserDetails(user.getUsername(), user.getPassword(), grantedAuthorities);
	        customUserDetails.setFname(user.getFname());
	        customUserDetails.setLname(user.getLname());
	        customUserDetails.setEmail(user.getEmail());
	        customUserDetails.setUsername(user.getUsername());
	        
		return customUserDetails;
	}


	@Override
	public User save(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(true);	

        
        user.setRoles(new HashSet<>(Arrays.asList(new Role("USER"))));
        
        Profile profile = new Profile();
        
        profile.setUser(user);
        
        user.setProfile(profile);
        
        
    
       return   userRepository.save(user);
   	
	}
 

	@Override
	public User findByUsername(String username) {
		
		return userRepository.findByUsername(username);
	}



	@Override
	public User findByEmail(String email) {
		// TODO Auto-generated method stub
		return userRepository.findByEmail(email);
	}

	@Override
	public Optional<User> findByResetToken(String resetToken) {
		return userRepository.findByResetToken(resetToken);
	}


	public User findByUsernameAndPassword(String username, String password) {
		
		password=bCryptPasswordEncoder.encode(password);
		return userRepository.findByUsernameAndPassword(username,password);
	}


	@Override
	public User getId(int id) {
	
		return userRepository.getOne(id);
	}


	@Override
	public void deleteById(int id) {
		userRepository.deleteById(id);
		
	}


	@Override
	public List<User> findAllUsers() {
		
		return userRepository.findAll();
	}


	@Override
	public Optional<User> findById(int id) {
	
		return userRepository.findById(id);
	}


	@Override
	public User update(User user) {		
		
		
		user.setActive(true);
		return userRepository.save(user);
	}

	@Override
	public List<Map<String, Object>> userDetailsReport() {
		List<Map<String, Object>> result = new ArrayList<>();
		
		for(User user :userRepository.findAll())
		{
			Map<String, Object> item= new HashMap<String, Object>();
			
			
			item.put("id", user.getId());
			item.put("fname", user.getFname());
			item.put("lname",user.getLname());
			item.put("email", user.getEmail());
			item.put("username", user.getUsername());
			
			item.put("active", user.isActive());
			
			result.add(item);
		}
		
		return result;
	}


	@Override
	public Optional<User> findUserByEmail(String email) {
		
		return userRepository.findUserByEmail(email);
	}


	@Override
	public boolean findUserByUsername(String username) {
		userRepository.findUserByUsername(username);
		return true;
	}


	

		
	@Override
	public User getUserFromSession()
	{
		// TODO Auto-generated method stub
		return ((UserModel)session.getAttribute("userModel")).getUser();
	}


	@Override
	public Optional<User> findUByUsername(String username)
	{
		// TODO Auto-generated method stub
		return userRepository.findUByUsername(username);
	}


	@Override
	public List<User> findTop4ByOrderByIdDesc()
	{
		// TODO Auto-generated method stub
		return userRepository.findTop10ByOrderByIdDesc();
	}


	@Override
	public long count() {
		// TODO Auto-generated method stub
		return userRepository.count();
	}


//	@Override
//	public Profile getProfileFromSession()
//	{
//		// TODO Auto-generated method stub
//		return null;
//	}

	
}

