/**
 * 
 */
package com.ngu.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author SURAJ
 *@Date Nov 7, 2019
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Mail {
	
	private String from;
	private String To;
	private String subject;
	private String message;

}
