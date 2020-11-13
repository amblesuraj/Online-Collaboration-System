/**
 * 
 */
package com.ngu.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ngu.Model.Profile;

/**
 * @author SURAJ
 *@Date Feb 16, 2020
 */
public interface ProfileRepository extends JpaRepository<Profile, Integer>
{

}
