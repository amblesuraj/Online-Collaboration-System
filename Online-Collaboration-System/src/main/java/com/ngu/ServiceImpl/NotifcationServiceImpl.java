package com.ngu.ServiceImpl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ngu.Enum.NotificationStatus;
import com.ngu.Model.Notification;
import com.ngu.Repositories.NotificationRepository;
import com.ngu.Service.NotificationService;

@Service
@Transactional
public class NotifcationServiceImpl implements NotificationService {

	@Autowired
	NotificationRepository notificationRepository;
	@Override
	public Notification save(Notification notification) {
		// TODO Auto-generated method stub
		return notificationRepository.save(notification);
	}

	@Override
	public List<Notification> findAll() {
		// TODO Auto-generated method stub
		return notificationRepository.findAll();
	}

	@Override
	public void DeleteById(int id) {
		notificationRepository.deleteById(id);

	}

	@Override
	public void DeleteAll() {
		notificationRepository.deleteAll();
		
	}

	@Override
	public void delete(Notification notification) {
		notificationRepository.delete(notification);
	}

	@Override
	public long countByStatus(NotificationStatus status) {
		// TODO Auto-generated method stub
		return notificationRepository.countByStatus(status);
	}

	@Override
	public List<Notification> findTop5ByOrderByIdDesc() {
		// TODO Auto-generated method stub
		return notificationRepository.findTop5ByOrderByIdDesc();
	}

	@Override
	public Optional<Notification> findByUserId(int id) {
		// TODO Auto-generated method stub
		return notificationRepository.findById(id);
	}

	@Override
	public Optional<Notification> findByPostIdAndUserId(int postId, int userId) {
		// TODO Auto-generated method stub
		return notificationRepository.findByPostIdAndUserId(postId,userId);
	}

	@Override
	public List<Notification> findByStatus(NotificationStatus status) {
		// TODO Auto-generated method stub
		return notificationRepository.findByStatus(status);
	}

	@Override
	public List<Notification> findByUserIdAndStatus(int userId, NotificationStatus status) {
		// TODO Auto-generated method stub
		return notificationRepository.findByUserIdAndStatus(userId, status);
	}

}
