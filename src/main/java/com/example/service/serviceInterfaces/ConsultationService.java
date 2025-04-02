package com.example.service.serviceInterfaces;

import java.util.List;

import com.example.dao.ConsultationDAO;

public interface ConsultationService {
	public String addConsultation(Long appointmentId, String notes, String prescription);
	public ConsultationDAO getConsultationByAppointmentId(Long appointmentId);
	public List<ConsultationDAO> viewMedicalHistory(Long patientId);
	

}
