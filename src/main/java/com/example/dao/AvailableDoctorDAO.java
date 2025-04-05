package com.example.dao;

import java.time.LocalDate;

public class AvailableDoctorDAO {
	private Long doctorId;
	private String name;
	
	public Long getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(Long doctorId) {
		this.doctorId = doctorId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	

	
	public AvailableDoctorDAO(Long doctorId, String name) {
		super();
		this.doctorId = doctorId;
		this.name = name;
	
	}
	public AvailableDoctorDAO() {
		super();
	}
	
	
	
	
	
	

}
