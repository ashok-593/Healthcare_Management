package com.example.dto;

import com.example.model.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


public class RegisterRequest {
	
	@Size(min=3,max=30,message="Name should be between 3 and 30 characters")
	@Pattern(regexp="^[A-Za-z ]+$", message="Only letters and spaces are allowed")

	private String name;
	
	@Email(message = "Email should be valid")
    @NotBlank(message = "Email is mandatory")
	private String email;
	
	@NotBlank(message = "Password is mandatory")
    @Size(min = 8, message = "Password should be at least 8 characters long")
	private String password;
	
	@NotBlank(message = "Phone number is mandatory")
	@Pattern(regexp="^[6-9][0-9]{9}$",message="Phone number is invalid")
	private String phone;
	
	@NotNull(message = "Role is mandatory")
	private Role role;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public RegisterRequest(String name, String email, String password, String phone, Role role) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.phone = phone;
		this.role = role;
	}
	public RegisterRequest() {
		super();
	}
	
	

}
