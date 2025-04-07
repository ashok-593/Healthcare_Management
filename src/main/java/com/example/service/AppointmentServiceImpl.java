package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.example.dao.AppointmentDAO;
import com.example.dto.AppointmentRequest;
import com.example.exception.HealthCareAPIException;
import com.example.exception.ResourceNotFoundException;
import com.example.model.Appointment;
import com.example.model.AppointmentStatus;
import com.example.model.AvailabilitySlot;
import com.example.model.DoctorAvailability;
import com.example.model.User;
import com.example.repository.AppointmentRepository;
import com.example.repository.AvailabilitySlotRepository;
import com.example.repository.DoctorAvailabilityRepository;
import com.example.repository.UserRepository;
import com.example.service.serviceInterfaces.AppointmentService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
 
@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService{
	

	@Autowired
	private AppointmentRepository appointmentRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private NotificationServiceImpl notificationService;
	@Autowired
	private AvailabilitySlotRepository slotRepository;
	@Autowired
	private DoctorAvailabilityRepository availabilityRepository;
	
	
	//@Autowired
	//private EmailService emailService;
	
	//@Autowired
	//private SmsService smsService;
	
	
	
	@Override
	@Transactional
	 public String bookAppointment(AppointmentRequest request) {
		
		 User patient = userRepository.findById(request.getPatientId()).orElseThrow(() -> new ResourceNotFoundException("Patient","id",request.getPatientId()));
	     User doctor = userRepository.findById(request.getDoctorId()).orElseThrow(() -> new ResourceNotFoundException("Doctor","id",request.getDoctorId()));
		 
		
		 boolean isAvailable = availabilityRepository.findByDoctorIdAndNonBlockedSlots(request.getDoctorId()).stream().anyMatch(availability -> availability.getAvailableDate().equals(request.getAppointmentDate()));
		 
		 if(!isAvailable) {
			 throw new HealthCareAPIException(HttpStatus.BAD_REQUEST,"Doctor is not available at this timeSlot");
		 }
		 
		 
		 AvailabilitySlot slot = slotRepository.findByDoctorAvailability_DoctorIdAndDoctorAvailability_AvailableDateAndTimeSlot(request.getDoctorId(), request.getAppointmentDate(), request.getTimeSlot());
	     
		 if(slot==null || slot.isBlocked()) {
	    	  throw new HealthCareAPIException(HttpStatus.BAD_REQUEST,"Selected timeSlot is already booked and Not Available ");
	      }
	 
	        Appointment appointment = new Appointment();
	        appointment.setPatient(patient);
	        appointment.setDoctor(doctor);
	        appointment.setAppointmentDate(request.getAppointmentDate());
	        appointment.setTimeSlot(request.getTimeSlot());
	        appointment.setStatus(AppointmentStatus.BOOKED);
	        appointmentRepository.save(appointment);
	        
	        slot.setBlocked(true);
	        slotRepository.save(slot);
	 
	        
	        
	        
	        
	        String patientMessage = " Dear Patient , your Appointment with Doctor Id :"+appointment.getDoctor().getUserId()+" "+appointment.getDoctor().getName()+" is confirmed for "+appointment.getTimeSlot()+" .";
	        String doctorMessage = " Dear Doctor , your Appointment with PAtient Id :"+appointment.getPatient().getUserId()+" "+appointment.getPatient().getName()+" is confirmed for "+appointment.getTimeSlot()+" .";
	        
	  
	        notificationService.addnotification(appointment.getDoctor().getUserId(), doctorMessage);
	        notificationService.addnotification(appointment.getPatient().getUserId(), patientMessage);
	        
	        
	        
	       
	        
	        return "Successfully Booked an appointment";
	    
	 }
	 
	@Override
	@Transactional
	 public String updateAppointment(Long id, LocalDate appointmentDate , LocalTime timeSlot) {
		 Appointment appointment = appointmentRepository.findByAppointmentId(id).orElseThrow(()->new ResourceNotFoundException("Appointment"," id",id));
		 
		 
		 
		 DoctorAvailability slotDate = availabilityRepository.findByDoctorIdAndAvailableDate(appointment.getDoctor().getUserId(), appointmentDate);
		 
		 if(slotDate == null || slotDate.isBlocked()) {
			 throw new HealthCareAPIException(HttpStatus.BAD_REQUEST,"Selected  new Appointment Date is Not Available");
			 
		 }
		 
		 AvailabilitySlot slot = slotRepository.findByDoctorAvailability_DoctorIdAndDoctorAvailability_AvailableDateAndTimeSlot(appointment.getDoctor().getUserId(), appointmentDate, timeSlot);
		 
		 if(slot==null  || slot.isBlocked()) {
			 throw new HealthCareAPIException(HttpStatus.BAD_REQUEST,"Selected  new timeSlot is already booked and Not Available");
			 
		 }
		 if(!appointment.getStatus().equals(AppointmentStatus.CANCELLED) && !appointment.getStatus().equals(AppointmentStatus.COMPLETED) ) {
			 appointment.setAppointmentDate(appointmentDate);
			 appointment.setTimeSlot(timeSlot);
			 appointment.setStatus(AppointmentStatus.MODIFIED);
			 appointmentRepository.save(appointment);
			 
			 slot.setBlocked(true);
			 slotRepository.save(slot);
			 
			 String pMessage = " Dear Patient , your Appointment with Doctor Id :"+appointment.getDoctor().getUserId()+" "+appointment.getDoctor().getName()+" is updated and conformed for "+appointment.getTimeSlot()+" .";
			 String doctorMessage = " Dear Doctor , your Appointment with PAtient Id :"+appointment.getPatient().getUserId()+" "+appointment.getPatient().getName()+" is Updated and confirmed for "+appointment.getTimeSlot()+" .";
			notificationService.addnotification(appointment.getPatient().getUserId(), pMessage);
			notificationService.addnotification(appointment.getDoctor().getUserId(), doctorMessage);
			 return "Appointment Updated Successfully";
			 
		 } else {
			 throw new HealthCareAPIException(HttpStatus.BAD_REQUEST,"your appointment is  cancelled and cannot be modified ");
		 }
		 
	 }
	 
	@Override
	 public String cancelAppointment(Long id) {
		 Appointment appointment = appointmentRepository.findByAppointmentId(id).orElseThrow(()-> new ResourceNotFoundException("Appointment"," id",id));
		 
		 AvailabilitySlot slot = slotRepository.findByDoctorAvailability_DoctorIdAndDoctorAvailability_AvailableDateAndTimeSlot(appointment.getDoctor().getUserId(), appointment.getAppointmentDate(), appointment.getTimeSlot());
		 
		 appointment.setStatus(AppointmentStatus.CANCELLED);
		 appointmentRepository.save(appointment);
		 slot.setBlocked(false);
		 slotRepository.save(slot);
		 
		 
		 String pMessage = " Dear Patient , your Appointment with Doctor Id :"+appointment.getDoctor().getUserId()+" "+appointment.getDoctor().getName()+" is Cancelled .";
		  
			notificationService.addnotification(appointment.getPatient().getUserId(), pMessage);
			
			return "Appointment Cancelled Successfully";
		 
	 } 
	 
	@Override
	 public List<AppointmentDAO> getAppointmentsByPatient(Long patientId){
		
			 List<Appointment> patientAppointments =  appointmentRepository.findByPatientUserId(patientId);
			 
			 return getAppointmentDAOList(patientAppointments);
		
		 
		 
		 
		 
	 }
	
	public List<AppointmentDAO> getUpcomingPatientAppointments(Long patientId){
		
	
		     LocalDate today = LocalDate.now();
		     LocalTime time = LocalTime.now();
		     
		     List<Appointment> allAppointments = appointmentRepository.findByPatientUserId(patientId);
		     
		     List<Appointment> upcomingAppointments = allAppointments.stream()
		         .filter(appointment -> 
		             appointment.getAppointmentDate().isAfter(today) ||
		             (appointment.getAppointmentDate().isEqual(today) && appointment.getTimeSlot().isAfter(time))
		         )
		         .collect(Collectors.toList());
		     
		     return getAppointmentDAOList(upcomingAppointments);
		 
		 
		
	}
	
	
	
public List<AppointmentDAO> getPastPatientAppointments(Long patientId){
		
	 LocalDate today = LocalDate.now();
     LocalTime time = LocalTime.now();
     
     List<Appointment> allAppointments = appointmentRepository.findByPatientUserId(patientId);
     
     List<Appointment> pastAppointments = allAppointments.stream()
         .filter(appointment -> 
             appointment.getAppointmentDate().isBefore(today) ||
             (appointment.getAppointmentDate().isEqual(today) && appointment.getTimeSlot().isBefore(time))
         )
         .collect(Collectors.toList());
     
     return getAppointmentDAOList(pastAppointments);
		
	}
	
	@Override
	 public List<AppointmentDAO> getAppointmentsByDoctor(Long doctorId){
		
			 List<Appointment> doctorAppointments= appointmentRepository.findByDoctorUserId(doctorId);
			 
			 return getAppointmentDAOList(doctorAppointments);
		 
		 
		 
	 }	
	
	public AppointmentDAO getAppointmentByAppointmentId(Long appointmentId) {
		
		Appointment appointment = appointmentRepository.findByAppointmentId(appointmentId).orElseThrow(()-> new ResourceNotFoundException("Appointment","id",appointmentId));
		return getAppointmentDAO(appointment);
	}
	 
	
	 public List<AppointmentDAO> getAppointmentDAOList(List<Appointment> appointments){
		 return appointments.stream()
				    .<AppointmentDAO>map(appointment -> {
				        AppointmentDAO dao = new AppointmentDAO();
				        dao.setAppointmentId(appointment.getAppointmentId());			        
				        dao.setPatientId(appointment.getPatient().getUserId());
				        dao.setPatientName(appointment.getPatient().getName());
				        dao.setDoctorId(appointment.getDoctor().getUserId());
				        dao.setDoctorName(appointment.getDoctor().getName());
				        dao.setAppointmentStatus(appointment.getStatus());
				        dao.setAppointmentDate(appointment.getAppointmentDate());
				        dao.setTimeSlot(appointment.getTimeSlot());
				        return dao;
				    })
				    .collect(Collectors.toList());
	 }
	 
	 public  AppointmentDAO getAppointmentDAO(Appointment appointment) {
		 
		 AppointmentDAO dao = new AppointmentDAO();
		 dao.setAppointmentId(appointment.getAppointmentId());
		 dao.setPatientId(appointment.getPatient().getUserId());
		 dao.setPatientName(appointment.getPatient().getName());
		 dao.setDoctorId(appointment.getDoctor().getUserId());
		 dao.setDoctorName(appointment.getDoctor().getName());
		 dao.setAppointmentDate(appointment.getAppointmentDate());
		 dao.setTimeSlot(appointment.getTimeSlot());
		 dao.setAppointmentStatus(appointment.getStatus());
		 
		 return dao;
	 }

	

	
}
