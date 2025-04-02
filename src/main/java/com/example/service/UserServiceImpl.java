package com.example.service;


import com.example.dto.LoginRequest;


import com.example.dto.RegisterRequest;
import com.example.dto.UserUpdateRequest;
import com.example.exception.HealthCareAPIException;
import com.example.exception.ResourceNotFoundException;
import com.example.model.User;
import com.example.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.security.JwtUtil;
import com.example.service.serviceInterfaces.UserService;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {
	
	private  UserRepository userRepository;
    private  BCryptPasswordEncoder passwordEncoder;
    private  JwtUtil jwtUtil;
    private AuthenticationManager authenticationManager;
   
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager=authenticationManager;
    }

   
    //Register as User(Doctor or Patient)
    @Override
    @Transactional
    public String registerUser(RegisterRequest request) {
    	
    	if(userRepository.findByEmail(request.getEmail()).isPresent()) {
    		throw new HealthCareAPIException(HttpStatus.BAD_REQUEST,"Email alredy Exists");
    	}
    	
    	if(userRepository.findByPhone(request.getPhone()).isPresent()) {
    		throw new HealthCareAPIException(HttpStatus.BAD_REQUEST,"Phone number already exists");
    	}
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone()); 
        user.setPassword(passwordEncoder.encode(request.getPassword()));// Hash password
        user.setRole(request.getRole());
        userRepository.save(user);
        
        return "User Registered successfully!";
    }
    
    
    //Authenticate User Login
    @Override
    @Transactional
    public String loginUser(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(()-> new HealthCareAPIException(HttpStatus.NOT_FOUND,"User not found with the given email : "+request.getEmail()));
        
        if(!passwordEncoder.matches(request.getPassword(),user.getPassword())) {
        	throw new HealthCareAPIException(HttpStatus.BAD_REQUEST,"Invalid password");
        }
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
               request.getEmail(),request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        String token = jwtUtil.generateToken(authentication);
        
        return token;
    }
    
    @Override
    @Transactional
    public String updateUser(Long id ,UserUpdateRequest request) {
    	User user = userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User","id",id));
    	
    	if(request.getName()!=null) {
    		user.setName(request.getName());
    	}
    	if(request.getPhone()!=null) {
    		if(userRepository.findByPhone(request.getPhone()).isPresent()) {
        		throw new HealthCareAPIException(HttpStatus.BAD_REQUEST,"Phone number already exists");
        	}
    		user.setPhone(request.getPhone());
    	}
    	
    	userRepository.save(user);
    	
    	return "User details updated successfully";
    	
    }
    
    @Override
    public User getUserDetails(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new HealthCareAPIException(HttpStatus.NOT_FOUND,"User not found"));
    }


	
}



