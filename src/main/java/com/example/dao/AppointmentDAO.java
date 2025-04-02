package com.example.dao;

import java.time.LocalDate;
import java.time.LocalTime;

import com.example.model.AppointmentStatus;

import lombok.Data;

@Data
public class AppointmentDAO {
	private Long appointmentId;
	private Long patientId;
	private String patientName;
	private Long doctorId;
	private String doctorName;
	private LocalDate appointmentDate;
	private LocalTime timeSlot;
	private AppointmentStatus appointmentStatus;
	public Long getAppointmentId() {
		return appointmentId;
	}
	public void setAppointmentId(Long appointmentId) {
		this.appointmentId = appointmentId;
	}
	public Long getPatientId() {
		return patientId;
	}
	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	public Long getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(Long doctorId) {
		this.doctorId = doctorId;
	}
	public String getDoctorName() {
		return doctorName;
	}
	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
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
	public AppointmentStatus getAppointmentStatus() {
		return appointmentStatus;
	}
	public void setAppointmentStatus(AppointmentStatus appointmentStatus) {
		this.appointmentStatus = appointmentStatus;
	}
	
	
}
