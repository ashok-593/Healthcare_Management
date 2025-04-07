package com.example.dto;

import java.time.LocalDate;

import java.time.LocalTime;

import jakarta.validation.constraints.Future;


public class AppointmentUpdateRequest {
	
	private Long appointmentId;
	private Long doctorId;
	
	
	private LocalDate appointmentDate;
	
	private LocalTime timeSlot;
	public Long getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(Long doctorId) {
		this.doctorId = doctorId;
	}
	public LocalDate getAppointmentDate() {
		return appointmentDate;
	}
	public void setAppointmentDate(LocalDate appointmentDate) {
		this.appointmentDate = appointmentDate;
	}
	public LocalTime getTimeSlot() {
		return timeSlot;
	}
	public void setTimeSlot(LocalTime timeSlot) {
		this.timeSlot = timeSlot;
	}
	public Long getAppointmentId() {
		return appointmentId;
	}
	public void setAppointmentId(Long appointmentId) {
		this.appointmentId = appointmentId;
	}
	
	
	
	

}
