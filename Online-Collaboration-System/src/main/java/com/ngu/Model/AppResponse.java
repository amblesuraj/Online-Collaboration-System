/**
 * 
 */
package com.ngu.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author SURAJ
 *@Date Feb 8, 2020
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppResponse<T>
{
		private String response;
		private T data;
}
