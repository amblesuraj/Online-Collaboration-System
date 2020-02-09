/**
 * 
 */
package com.ngu.RestController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ngu.Model.AppResponse;
import com.ngu.Model.Profile;
import com.ngu.Model.User;
import com.ngu.Service.FileStorageService;
import com.ngu.Service.UserService;

/**
 * @author SURAJ
 *@Date Nov 26, 2019
 */
@Controller
@RequestMapping("/rest/")
public class UserRestController {

	@Autowired
	UserService userService;
	@Autowired
	FileStorageService fileStorageService;
	
	
	@PostMapping(value = "saveProfilePic")
	public ResponseEntity<Object> saveProfilePicture(@RequestParam(required = true, value = "profilePicture") MultipartFile file)
	{
		
		try
		{
				User user = userService.getUserFromSession();
				
				Profile profile = userService.getProfileFromSession();
				
				String fileName = fileStorageService.storeFile(file);
				String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/")
						.path(fileName).toUriString();
				
				profile.setProfilePicture(fileDownloadUri);
				
				user.setProfile(profile);
				
				
				AppResponse<User> response = new AppResponse<User>("success", userService.update(user));
				return new ResponseEntity<Object>(response,HttpStatus.OK);
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		
		
		return new ResponseEntity<Object>("failed",HttpStatus.OK);
	}
	
	
	
}
