package com.example.model;
 
import jakarta.persistence.*;

 
@Entity
@Table(name = "notifications")
public class Notification {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;
 
    @ManyToOne
    @JoinColumn(name = "user_id" , nullable=false)  // ✅ Matches database column name
    private User user;
 
    private String message;
 
    @Enumerated(EnumType.STRING)
    private Status status; // ENUM (PENDING, SENT)
 
    public enum Status { PENDING, SENT }
    
    
 
    public Notification() {
		super();
	}

	public Notification(User user, String message, Status status) {
		super();
		this.user = user;
		this.message = message;
		this.status = status;
		
	}

	// Getters and Setters
    public Long getNotificationId() {
        return notificationId;
    }
 
    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }
 
    public User getUser() {
        return user;
    }
 
    public void setUser(User user) {
        this.user = user;
    }
 
    public String getMessage() {
        return message;
    }
 
    public void setMessage(String message) {
        this.message = message;
    }
 
    public Status getStatus() {
        return status;
    }
 
    public void setStatus(Status status) {
        this.status = status;
    }
 
}
 