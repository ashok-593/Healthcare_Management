package com.example.service;

import java.util.Collections;



import java.util.List;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.repository.UserRepository;
 
@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService {
 
    private final UserRepository userRepository;
    
    
    public CustomUserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
 
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        com.example.model.User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
 
        // Convert Role Enum to GrantedAuthority
        List<GrantedAuthority> authorities = Collections.singletonList(
        	    new SimpleGrantedAuthority("ROLE_" + user.getRole().name())  // Prefix with "ROLE_"
        	);

        	return new org.springframework.security.core.userdetails.User(
        	    user.getEmail(),
        	    user.getPassword(),
        	    authorities
        	);
    }
}
 