/**
 * 
 */
package com.ngu.Model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

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
@Table(name = "blog")
public class Blog extends Auditor<String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@NotEmpty(message = "Blog Title Should not be empty")
	private String blogtitle;

	private String blogImage;
	
//	private List<String> blogImages;
	
	@Lob
	@Column(columnDefinition = "LONGTEXT")
	@NotEmpty(message = "Content should not be empty")
	private String content;
	
	
}
