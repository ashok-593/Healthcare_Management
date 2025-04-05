package com.example.controller;


import com.example.dto.AuthResponse;

import com.example.dto.LoginRequest;
import com.example.dto.RegisterRequest;
import com.example.dto.UserUpdateRequest;
import com.example.model.User;
import com.example.service.UserServiceImpl;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/users")  // ✅ This should be correct
@Validated
public class UserController {
    @Autowired
    private UserServiceImpl userService;
    
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping("/register")  
    public ResponseEntity<String> registerUser(@Valid @RequestBody RegisterRequest request) {
    	String response=userService.registerUser(request);
    	
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest request) {
        
        AuthResponse response = userService.loginUser(request);
       

        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateUser(@Valid @PathVariable Long id, @Valid @RequestBody UserUpdateRequest request){
    	
    	String response = userService.updateUser(id, request);
    	
    	return new ResponseEntity<>(response,HttpStatus.OK);
    }
    
    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile(Authentication authentication) {
        String email = authentication.getName(); // Extract email from authenticated user
        User user = userService.getUserDetails(email);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }
}