package com.example.model;

import java.io.Serializable;

import java.time.LocalDate;
import java.util.Objects;

@SuppressWarnings("serial")
public class DoctorAvailabilityId implements Serializable{
	
	private Long doctorId;
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
	public void setAvailabledate(LocalDate availableDate) {
		this.availableDate = availableDate;
	}
	public DoctorAvailabilityId(Long doctorId, LocalDate availableDate) {
		super();
		this.doctorId = doctorId;
		this.availableDate = availableDate;
	}
	public DoctorAvailabilityId() {
		super();
	}
	@Override
	public int hashCode() {
		return Objects.hash(availableDate, doctorId);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DoctorAvailabilityId other = (DoctorAvailabilityId) obj;
		return Objects.equals(availableDate, other.availableDate) && Objects.equals(doctorId, other.doctorId);
	}
	
	
	
	

}
