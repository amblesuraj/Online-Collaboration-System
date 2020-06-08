/**
 * 
 */
package com.ngu.Service;

import com.ngu.Model.Forum;

/**
 * @author SURAJ
 *@Date Mar 6, 2020
 */
public interface ForumService
{
			
		public Forum createForum(Forum forum);
		public Forum updateForum(Forum forum);
		
		public Forum FindById(int id);
		public void deleteById(int id);
		
}
