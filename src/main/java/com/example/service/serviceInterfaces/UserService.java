package com.example.service.serviceInterfaces;

import com.example.dto.AuthResponse;
import com.example.dto.LoginRequest;
import com.example.dto.RegisterRequest;
import com.example.dto.UserUpdateRequest;
import com.example.model.User;

public interface UserService {
	public String registerUser(RegisterRequest request);
	public AuthResponse loginUser(LoginRequest request);
	public String updateUser(Long id ,UserUpdateRequest request);
	public User getUserDetails(String email);
}
