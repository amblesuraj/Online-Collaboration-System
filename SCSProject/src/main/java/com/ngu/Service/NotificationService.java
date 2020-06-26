package com.ngu.Service;

import java.util.List;
import java.util.Optional;

import com.ngu.Enum.NotificationStatus;
import com.ngu.Model.Notification;

public interface NotificationService {

	public Notification save(Notification notification);
	
	List<Notification> findAll();
	
	public void DeleteById(int id);
	
	public void delete(Notification notification);
	
	public void DeleteAll();
	
	public long countByStatus(NotificationStatus unread);
	
	public Optional<Notification> findByUserId(int id);

	List<Notification> findTop5ByOrderByIdDesc();

	public Optional<Notification> findByPostIdAndUserId(int postId, int userId);
	
	List<Notification> findByStatus(NotificationStatus status);
	
	List<Notification> findByUserIdAndStatus(int userId,NotificationStatus status);

}
