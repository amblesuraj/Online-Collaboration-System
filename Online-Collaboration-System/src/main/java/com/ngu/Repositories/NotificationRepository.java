package com.ngu.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ngu.Enum.NotificationStatus;
import com.ngu.Model.Notification;

@Repository("notificationRepository")
public interface NotificationRepository extends JpaRepository<Notification, Integer> {

	long countByStatus(NotificationStatus status);

	List<Notification> findTop5ByOrderByIdDesc();

	Optional<Notification> findByPostIdAndUserId(int postId, int userId);

	List<Notification> findByStatus(NotificationStatus status);

	List<Notification> findByUserIdAndStatus(int userId, NotificationStatus status);

	
	
//	SELECT u.username,u.fname,p.CreatedBy,p.caption,p.id,p.status,l.action,l.userId,l.post_id FROM
//	user u
//	INNER JOIN
//	post p
//	ON u.id=
//	p.user_id INNER
//	JOIN likes
//	l ON l.post_id=
//	p.id WHERE u.id=9
}
