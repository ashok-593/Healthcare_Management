package com.example.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
//import lombok.Getter;
//import lombok.Setter;

@Data

public class AppointmentRequest {
	
	@Min(value=1,message="Patient ID cannot be null")
	private Long patientId;
	
	@Min(value=1,message="Doctor ID cannot be null")
	private Long doctorId;
	
	@NotNull(message = " appointmentDate is Mandatory to book an appointment")
	@Future(message="Time slot must be in the future")
	private LocalDate appointmentDate;
	
	@NotNull(message = " appointment timeSlot is Madatory to book an appointment")
	private LocalTime timeSlot;

	public Long getPatientId() {
		return patientId;
	}

	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}

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


	

}
