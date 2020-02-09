package com.ngu.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ngu.Model.User;



@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Integer>
{
	
	public User findByUsername(String username);
	
	public boolean findUserByUsername(String username);
	
	public User findByEmail(String email);
	
	@Query("SELECT ua FROM User ua WHERE ua.username=:username and ua.password=:password")
	User findByUsernameAndPassword(@Param(value = "username")String username,@Param(value = "password")String password);

	public Optional<User> findUserByEmail(String email);
	public Optional<User> findByResetToken(String resetToken);

	/**
	 * @param username
	 * @return
	 */
	public Optional<User> findUByUsername(String username);
	
}
