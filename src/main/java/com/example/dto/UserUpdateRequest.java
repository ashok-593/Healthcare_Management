package com.example.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserUpdateRequest {
	
	@Pattern(regexp="^[A-Za-z ]+$", message="Only letters and spaces are allowed")
	private String name;
	
	@Pattern(regexp="^[6-9][0-9]{9}$",message="Phone number is invalid")
	private String phone;
	
	public UserUpdateRequest(String name, String phone) {
		super();
		this.name = name;
		this.phone = phone;
	}
	public UserUpdateRequest() {
		super();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	

}
