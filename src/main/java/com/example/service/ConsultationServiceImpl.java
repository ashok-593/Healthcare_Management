package com.example.service;


import com.example.dao.ConsultationDAO;
import com.example.exception.HealthCareAPIException;
import com.example.exception.ResourceNotFoundException;
import com.example.model.Appointment;
import com.example.model.AppointmentStatus;
import java.util.stream.Collectors;
import com.example.model.Consultation;
import com.example.model.User;
import com.example.repository.AppointmentRepository;
import com.example.repository.ConsultationRepository;
import com.example.repository.UserRepository;
import com.example.service.serviceInterfaces.ConsultationService;

import jakarta.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
 
@Service
public class ConsultationServiceImpl implements ConsultationService {
    
    private ConsultationRepository consultationRepository;
    private AppointmentRepository appointmentRepository;
    private UserRepository userRepository;
    
    
 
    public ConsultationServiceImpl(ConsultationRepository consultationRepository,
			AppointmentRepository appointmentRepository, UserRepository userRepository) {
		super();
		this.consultationRepository = consultationRepository;
		this.appointmentRepository = appointmentRepository;
		this.userRepository = userRepository;
	}

	@SuppressWarnings("unused")
    @Override
    @Transactional
	public String addConsultation(Long appointmentId, String notes, String prescription) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment","id",appointmentId));
 
        Optional<Consultation> existingConsultation = Optional.ofNullable(
                consultationRepository.findByAppointmentAppointmentId(appointmentId));
 
        if (existingConsultation.isPresent()) {
            throw new HealthCareAPIException(HttpStatus.BAD_REQUEST,"Consultation already exists for this appointment");
        }
 
        
		Consultation consultation = new Consultation(appointment, notes, prescription);
		consultationRepository.save(consultation);
        appointment.setStatus(AppointmentStatus.COMPLETED);
        appointmentRepository.save(appointment);
        
        
        
        return "Consultation added successfully";
    }
    
    @Override
    public ConsultationDAO getConsultationByAppointmentId(Long appointmentId) {
        Consultation consultation = consultationRepository.findByAppointmentAppointmentId(appointmentId);
        
        return getConsultationDAO(consultation);
    }
    
    
    @SuppressWarnings("unused")
    @Override
	public List<ConsultationDAO> viewMedicalHistory(Long patientId) {
    	
    	
		User user = userRepository.findById(patientId).orElseThrow(()-> new ResourceNotFoundException("Patient","id",patientId));
    	//Appointment appointment = appointmentRepository.findByAppointmentPatientId(patientId).orElseThrow(()-> new ResourceNotFoundException("Patient","id",patientId));
    	boolean isAvailable = appointmentRepository.existsByPatientUserId(patientId);
    	if(!isAvailable) {
    		throw new ResourceNotFoundException("Appointment","Patient id",patientId);
    	}
    	
    	List<Consultation> consultations = consultationRepository.findByAppointmentPatientUserId(patientId);
    	 
    	 return  getConsultationDaoList(consultations);
    }
    
    public List<ConsultationDAO> viewDoctorConsultations(Long doctorId) {
    	
    	
		User user = userRepository.findById(doctorId).orElseThrow(()-> new ResourceNotFoundException("Doctor","id",doctorId));
    	//Appointment appointment = appointmentRepository.findByAppointmentPatientId(patientId).orElseThrow(()-> new ResourceNotFoundException("Patient","id",patientId));
    	boolean isAvailable = appointmentRepository.existsByDoctorUserId(doctorId);
    	if(!isAvailable) {
    		throw new ResourceNotFoundException("Appointment","Doctor id",doctorId);
    	}
    	
    	List<Consultation> consultations = consultationRepository.findByAppointmentDoctorUserId(doctorId);
    	 
    	 return  getConsultationDaoList(consultations);
    }
    
    public List<ConsultationDAO> getConsultationDaoList(List<Consultation> consultations){
    	
    	return consultations.stream()
			    .<ConsultationDAO>map(consultation -> {
			        ConsultationDAO dao = new ConsultationDAO();
			        dao.setConsultationId(consultation.getConsultationId());
			        dao.setAppointmentId(consultation.getAppointment().getAppointmentId());
			        dao.setPatientId(consultation.getAppointment().getPatient().getUserId());
			        dao.setPatientName(consultation.getAppointment().getPatient().getName());
			        dao.setDoctorId(consultation.getAppointment().getDoctor().getUserId());
			        dao.setDoctorName(consultation.getAppointment().getDoctor().getName());
			        dao.setAppointmentDate(consultation.getAppointment().getAppointmentDate());
			        dao.setTimeSlot(consultation.getAppointment().getTimeSlot());
			        dao.setNotes(consultation.getNotes());
			        dao.setPrescription(consultation.getPrescription());
			        return dao;
			    })
			    .collect(Collectors.toList());
    }
    
    public ConsultationDAO getConsultationDAO(Consultation consultation) {
    	
    	ConsultationDAO dao = new ConsultationDAO();
        dao.setConsultationId(consultation.getConsultationId());
        dao.setAppointmentId(consultation.getAppointment().getAppointmentId());
        dao.setPatientId(consultation.getAppointment().getPatient().getUserId());
        dao.setPatientName(consultation.getAppointment().getPatient().getName());
        dao.setDoctorId(consultation.getAppointment().getDoctor().getUserId());
        dao.setDoctorName(consultation.getAppointment().getDoctor().getName());
        dao.setAppointmentDate(consultation.getAppointment().getAppointmentDate());
        dao.setTimeSlot(consultation.getAppointment().getTimeSlot());
        dao.setNotes(consultation.getNotes());
        dao.setPrescription(consultation.getPrescription());
        return dao;
    	
    }
    
    
}
 
