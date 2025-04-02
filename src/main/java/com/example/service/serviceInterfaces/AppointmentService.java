package com.example.service.serviceInterfaces;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import com.example.dao.AppointmentDAO;
import com.example.dto.AppointmentRequest;


public interface AppointmentService {
	public String bookAppointment(AppointmentRequest request);
	public String cancelAppointment(Long id);
	public List<AppointmentDAO> getAppointmentsByPatient(Long patientId);
	public List<AppointmentDAO> getAppointmentsByDoctor(Long doctorId);
	String updateAppointment(Long id, LocalDate appointmentDate, LocalTime localTime);
	
}
