package com.example.service;


import com.example.exception.ResourceNotFoundException;


import com.example.model.Notification;
import com.example.model.User;

import com.example.repository.NotificationRepository;
import com.example.repository.UserRepository;
import com.example.service.serviceInterfaces.NotificationService;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;


 
@Service
public class NotificationServiceImpl  implements NotificationService{
 
    private NotificationRepository notificationRepository;
    private UserRepository userRepository;
    
    
   
   
    
    public NotificationServiceImpl(NotificationRepository notificationRepository, UserRepository userRepository) {
		super();
		this.notificationRepository = notificationRepository;
		this.userRepository = userRepository;
	}





	@Override
    @Transactional
    public void addnotification(Long userId, String message) {
    	User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User","id",userId));
        Notification notification = new Notification(user, message, Notification.Status.SENT);
        notificationRepository.save(notification);

    	
    }

	
}
 