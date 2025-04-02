package com.example.dto;

import java.time.LocalDate;



import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DoctorAvailabilityRequest {
	
	@NotNull(message = "Doctor ID is mandatory")
	private Long doctorId;
	
	@NotNull(message = "Available date is mandatory")
    @Future(message = "Available date must be in the future")
	private LocalDate availableDate;
	
	public Long getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(Long doctorId) {
		this.doctorId = doctorId;
	}
	public LocalDate getAvailableDate() {
		return availableDate;
	}
	public void setAvailableDate(LocalDate availableDate) {
		this.availableDate = availableDate;
	}
	
	
	
	
	

}
