/**
 * 
 */
package com.ngu.Model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author SURAJ
 *@Date Jan 22, 2020
 */
@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Profile implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String coverPhoto;
	
	private String maritalStatus;
	private String status;
	private String hometown;
	private Date birthdate; 
	
	@OneToOne
	private User user;
}
