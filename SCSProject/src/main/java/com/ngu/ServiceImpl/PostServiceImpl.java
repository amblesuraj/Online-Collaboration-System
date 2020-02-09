/**
 * 
 */
package com.ngu.ServiceImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ngu.Model.Post;
import com.ngu.Repositories.PostRepository;
import com.ngu.Service.PostService;

/**
 * @author SURAJ
 *@Date Nov 29, 2019
 */
@Service
@Transactional
public class PostServiceImpl implements PostService{

	@Autowired
	PostRepository postRepository;
	
//	@Override
//	public void createPost(Post post,MultipartFile postImage) {
//		// TODO Auto-generated method stub
//		Path absolutePath = Paths.get(".").toAbsolutePath();
//		String imagePath = absolutePath + "/src/main/resources/static/postImages/";
//		byte[] bytes;
//		try {
//			bytes = postImage.getBytes();
//			Path path = Paths.get(imagePath + postImage.getOriginalFilename());
//			Files.write(path, bytes);
//			post.setPostImage(postImage.getOriginalFilename());
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		postRepository.save(post);
//	}

	@Override
	public void  updatePost(Post post) {
		// TODO Auto-generated method stub
		postRepository.save(post);
	}

	@Override
	public List<Post> findAllPosts() {
		// TODO Auto-generated method stub
		return postRepository.findAll();
	}

	@Override
	public Post getPostById(int id) {
		// TODO Auto-generated method stub
		return postRepository.getOne(id);
	}

	@Override
	public void deletepostById(int id) {
		postRepository.deleteById(id);
	}

	@Override
	public void createPost(Post post)
	{
		postRepository.save(post);
		
	}

}
