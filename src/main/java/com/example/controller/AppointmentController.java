package com.example.controller;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
//import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.example.dao.AppointmentDAO;
import com.example.dto.AppointmentRequest;
import com.example.dto.AppointmentUpdateRequest;
import com.example.service.AppointmentServiceImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

//import java.time.LocalDateTime;
import java.util.List;
 
@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
@Validated
public class AppointmentController {
	
	@Autowired
    private  AppointmentServiceImpl appointmentService;
 
	@PostMapping("/book")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<String> bookAppointment(@Valid @RequestBody AppointmentRequest request){
		
		String appointment = appointmentService.bookAppointment(request);
		
		return new ResponseEntity<>(appointment,HttpStatus.CREATED);
		
	}
	
	@PutMapping("/update")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<String> updateAppointment( @Valid @RequestBody AppointmentUpdateRequest request){
		return new ResponseEntity<>(appointmentService.updateAppointment(request.getAppointmentId(),request.getAppointmentDate(), request.getTimeSlot()),HttpStatus.OK);
	}
	
	@DeleteMapping("cancel/{id}")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<String> cancelAppointment(@PathVariable Long id){
		
		return new ResponseEntity<>(appointmentService.cancelAppointment(id),HttpStatus.OK);
	
	}
	
	@GetMapping("/patient/{patientId}")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<List<AppointmentDAO>> getAppointmentsByPatient(@PathVariable Long patientId){
		
		return new ResponseEntity<>(appointmentService.getAppointmentsByPatient(patientId),HttpStatus.OK);
		
	}
	
	
	@GetMapping("/{userId}/upcoming")
	public ResponseEntity<List<AppointmentDAO>> getUpcomingAppointments(@PathVariable Long userId){
		
		return new ResponseEntity<>(appointmentService.getUpcomingAppointments(userId),HttpStatus.OK);
		
	}
	
	
	@GetMapping("/{userId}/past")
	public ResponseEntity<List<AppointmentDAO>> getPastAppointments(@PathVariable Long userId){
		
		return new ResponseEntity<>(appointmentService.getPastAppointments(userId),HttpStatus.OK);
		
	}
	
	@GetMapping("/{userId}/today")
	public ResponseEntity<List<AppointmentDAO>> getTodaysAppointments(@PathVariable Long userId){
		
		return new ResponseEntity<>(appointmentService.getTodaysAppointments(userId),HttpStatus.OK);
		
	}
	
	
	
	@GetMapping("/doctor/{doctorId}")
	@PreAuthorize("hasRole('DOCTOR')")
	public ResponseEntity<List<AppointmentDAO>> getAppointmentsByDoctor(@PathVariable Long doctorId){
		
		return new ResponseEntity<>(appointmentService.getAppointmentsByDoctor(doctorId),HttpStatus.OK);
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<AppointmentDAO> getAppointmentByAppointmentId(@PathVariable Long id){
		
		return new ResponseEntity<>(appointmentService.getAppointmentByAppointmentId(id),HttpStatus.OK);
		}
	
	
	
	
}