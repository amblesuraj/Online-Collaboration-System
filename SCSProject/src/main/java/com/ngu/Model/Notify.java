package com.ngu.Model;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Notify implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	int userid;
	String username;
//	String profilePicture;

	int postid;
	String caption;

	int like_postid;
	int like_userId;
	
	
}
