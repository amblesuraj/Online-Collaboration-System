/**
 * 
 */
package com.ngu.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;

import com.ngu.Model.Profile;
import com.ngu.Repositories.ProfileRepository;
import com.ngu.Service.ProfileService;

/**
 * @author SURAJ
 *@Date Feb 16, 2020
 */
public class ProfileServiceImpl implements ProfileService
{

	@Autowired
	ProfileRepository profileRepository; 
	
	@Override
	public Profile UpdateProfile(Profile profile)
	{
		// TODO Auto-generated method stub
		return profileRepository.save(profile);
	}

}
