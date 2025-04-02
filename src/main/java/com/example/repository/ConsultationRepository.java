package com.example.repository;

import com.example.model.Consultation;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsultationRepository extends JpaRepository<Consultation, Long> {
	
	Consultation findByAppointmentAppointmentId(Long appointmentId);
	Optional<Consultation>findByConsultationId(Long consultationId);
	List<Consultation>findByAppointmentPatientUserId(Long patientId);
}
