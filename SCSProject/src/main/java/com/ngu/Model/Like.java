/**
 * 
 */
package com.ngu.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * @author SURAJ
 *@Date May 22, 2020
 */
@Entity
@Table(name = "likes")
@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class Like
{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	
	private int postId;
	
	private String action;
	
	private int userId;
}
