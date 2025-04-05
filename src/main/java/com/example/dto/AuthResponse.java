package com.example.dto;


import com.example.model.User;

public class AuthResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private User user;
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getTokenType() {
		return tokenType;
	}
	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public AuthResponse(String accessToken, User user) {
		super();
		this.accessToken = accessToken;
		this.user = user;
	}
	public AuthResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    
    
    
    
	
}
