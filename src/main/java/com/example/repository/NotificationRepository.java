package com.example.repository;

import com.example.model.Notification;
import com.example.model.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
	
	List<Notification> findByUser(User user);
}
