/**
 * 
 */
package com.ngu.Model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.ngu.Enum.PostStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author SURAJ
 *@Date Nov 29, 2019
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "post")
public class Post extends Auditor<String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@ElementCollection
	@NotEmpty(message = "Post images Should not be empty")
	private List<String> postImages;
	
	@Column(name = "caption")
	private String caption;
	
	private PostStatus status;
	
}
