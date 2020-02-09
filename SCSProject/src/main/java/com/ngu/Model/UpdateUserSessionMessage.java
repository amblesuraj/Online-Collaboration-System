/**
 * 
 */
package com.ngu.Model;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author SURAJ
 *@Date Feb 9, 2020
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserSessionMessage
{

		private List<String> SessionKeys;
		private UUID Uuid;
		
		
	
}
