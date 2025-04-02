package com.example.model;

import java.time.LocalTime;

import jakarta.persistence.*;


@Entity
@Table(name="availability_slots")
public class AvailabilitySlot {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="doctor_id", referencedColumnName = "doctor_id"),
		@JoinColumn(name="available_date", referencedColumnName = "available_date")
	})
	private DoctorAvailability doctorAvailability;
	
	@Column(name = "time_slot")
	private LocalTime timeSlot;
	
	private boolean isBlocked;

	public DoctorAvailability getDoctorAvailability() {
		return doctorAvailability;
	}

	public void setDoctorAvailability(DoctorAvailability doctorAvailability) {
		this.doctorAvailability = doctorAvailability;
	}

	public LocalTime getTimeSlot() {
		return timeSlot;
	}

	public void setTimeSlot(LocalTime timeSlot) {
		this.timeSlot = timeSlot;
	}

	public boolean isBlocked() {
		return isBlocked;
	}

	public void setBlocked(boolean isBlocked) {
		this.isBlocked = isBlocked;
	}

	public AvailabilitySlot(Long id, DoctorAvailability doctorAvailability, LocalTime timeSlot, boolean isBlocked) {
		super();
		this.id = id;
		this.doctorAvailability = doctorAvailability;
		this.timeSlot = timeSlot;
		this.isBlocked = isBlocked;
	}

	public AvailabilitySlot() {
		super();
	}
	
	

}
