package com.example.model;


import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "appointments")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long appointmentId;

    @ManyToOne
    @JoinColumn(name = "patient_Id",
    referencedColumnName="userId", 
    nullable=false)
    private User patient;

    @ManyToOne
    @JoinColumn(name = "doctor_Id" ,
    referencedColumnName="userId",
    nullable=false)
    private User doctor;

    private LocalDate appointmentDate;
    private LocalTime timeSlot;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status; // ENUM (Booked, Cancelled, Completed)

	public Long getAppointmentId() {
		return appointmentId;
	}

	public void setAppointmentId(Long appointmentId) {
		this.appointmentId = appointmentId;
	}

	public User getPatient() {
		return patient;
	}

	public void setPatient(User patient) {
		this.patient = patient;
	}

	public User getDoctor() {
		return doctor;
	}

	public void setDoctor(User doctor) {
		this.doctor = doctor;
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

	public AppointmentStatus getStatus() {
		return status;
	}

	public void setStatus(AppointmentStatus status) {
		this.status = status;
	}

	public Appointment(Long appointmentId, User patient, User doctor, LocalDate appointmentDate, LocalTime timeSlot,
			AppointmentStatus status) {
		super();
		this.appointmentId = appointmentId;
		this.patient = patient;
		this.doctor = doctor;
		this.appointmentDate = appointmentDate;
		this.timeSlot = timeSlot;
		this.status = status;
	}

	public Appointment() {
		super();
	}

    

    


    

    
}
