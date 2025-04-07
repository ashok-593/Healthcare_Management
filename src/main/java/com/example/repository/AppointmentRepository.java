package com.example.repository;

import com.example.model.Appointment;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
	
	List<Appointment>findByDoctorUserId(Long doctorId);
	List<Appointment>findByPatientUserId(Long patientId);
	boolean existsByDoctorUserIdAndTimeSlot(Long doctorId, LocalDateTime timeSlot);
	Appointment findByDoctorUserIdAndTimeSlot(Long doctorId, LocalDateTime timeSlot);
	boolean existsByPatientUserId(Long patientId);
	boolean existsByDoctorUserId(Long doctorId);
	Optional<Appointment> findByAppointmentId(Long appointmentId);
	

	
}
