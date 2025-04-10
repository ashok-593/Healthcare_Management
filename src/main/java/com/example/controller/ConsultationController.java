package com.example.controller;


import com.example.dao.ConsultationDAO;


import com.example.dto.ConsultationRequest;
import com.example.service.ConsultationServiceImpl;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
 
@RestController
@RequestMapping("/api/consultations")
@Validated
public class ConsultationController {
    
    @Autowired
    private ConsultationServiceImpl consultationService;
 
    @PostMapping("/add")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<String> addConsultation(@Valid @RequestBody ConsultationRequest request) {
    	
    	String consultation = consultationService.addConsultation(request.getAppointmentId(),request.getNotes(),request.getPrescription());
    	
        return new ResponseEntity<>(consultation,HttpStatus.CREATED);
    }
    
   
 
    @GetMapping("/{appointmentId}")
    public ResponseEntity<ConsultationDAO> getConsultation(@PathVariable Long appointmentId) {
        return new ResponseEntity<>(consultationService.getConsultationByAppointmentId(appointmentId),HttpStatus.OK);
    }
    
    @GetMapping("/history/{patientId}")
    public ResponseEntity<List<ConsultationDAO>> viewMedicalHistory(@PathVariable Long patientId){
    	
    	return new ResponseEntity<>(consultationService.viewMedicalHistory(patientId),HttpStatus.OK);
    }
    
    @GetMapping("/doctor/history/{doctorId}")
    public ResponseEntity<List<ConsultationDAO>> viewDoctorConsultations(@PathVariable Long doctorId){
    	
    	return new ResponseEntity<>(consultationService.viewDoctorConsultations(doctorId),HttpStatus.OK);
    }
}
 
