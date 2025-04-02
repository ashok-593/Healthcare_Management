package com.example.model;


import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "doctor_availability",
		indexes = {@Index(name="idx_doctor_availability",columnList="doctor_id,available_date")})
@IdClass(DoctorAvailabilityId.class)
public class DoctorAvailability implements Serializable {
    
	@Id
	@Column(name="doctor_id")
    private Long doctorId;  // Doctor ID as Primary Key
    
    @Id
    @Column(name="available_date")
    private LocalDate availableDate;
    
    
    @OneToMany(mappedBy="doctorAvailability", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AvailabilitySlot> timeSlots;

     // Reference to User table

	private boolean isBlocked;

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

	public List<AvailabilitySlot> getTimeSlots() {
		return timeSlots;
	}

	public void setTimeSlots(List<AvailabilitySlot> timeSlots) {
		this.timeSlots = timeSlots;
	}

	public boolean isBlocked() {
		return isBlocked;
	}

	public void setBlocked(boolean isBlocked) {
		this.isBlocked = isBlocked;
	}

	public DoctorAvailability(Long doctorId, LocalDate availableDate, List<AvailabilitySlot> timeSlots,
			boolean isBlocked) {
		super();
		this.doctorId = doctorId;
		this.availableDate = availableDate;
		this.timeSlots = timeSlots;
		this.isBlocked = isBlocked;
	}

	public DoctorAvailability() {
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
		DoctorAvailability other = (DoctorAvailability) obj;
		return Objects.equals(availableDate, other.availableDate) && Objects.equals(doctorId, other.doctorId);
	}
	
	
    
    
	
	
   
}