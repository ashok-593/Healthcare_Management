package com.example.service;



import com.example.exception.ResourceNotFoundException;
import com.example.model.Notification;
import com.example.model.User;
import com.example.model.Notification.Status;
import com.example.repository.NotificationRepository;
import com.example.repository.UserRepository;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
 
@Service
public class SmsService {
	
	
	@Autowired
	private NotificationRepository notificationRepository;
	
	@Autowired
	private UserRepository userRepository;
 
    @Value("${twilio.account_sid}")
    private String accountSid;
 
    @Value("${twilio.auth_token}")
    private String authToken;
 
    @Value("${twilio.phone_number}")
    private String twilioNumber;
 
    public void sendSms(long id,String to, String message) {
    	User user = userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Doctor","id",id));
    	Notification notification = new Notification();
    	notification.setMessage(message);
        notification.setUser(user);
    	try {
    		Twilio.init(accountSid, authToken);
            Message.creator(
                    new com.twilio.type.PhoneNumber("+91"+to),
                    new com.twilio.type.PhoneNumber(twilioNumber),
                    message
            ).create();
            
            notification.setStatus(Status.SENT);
    		
    	} catch (RuntimeException e) {
    		System.out.printf("SMS not sent due to some error  :"+e);
    		notification.setStatus(Status.PENDING);
    		
    	}
        
        notificationRepository.save(notification);
    }
}

 
