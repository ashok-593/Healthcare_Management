package com.example.controller;

import java.time.LocalDate;


import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.dao.DoctorAvailabilityDAO;
import com.example.dto.DoctorAvailabilityRequest;
import com.example.model.DoctorAvailability;
import com.example.service.DoctorAvailabilityServiceImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/availability")
@RequiredArgsConstructor
@Validated
public class DoctorAvailabilityController {
	@Autowired
	private DoctorAvailabilityServiceImpl doctorAvailabilityService;
	
	
	
	
	@PostMapping("/set/{doctorId}")
	@PreAuthorize("hasRole('DOCTOR')")
	public ResponseEntity<String> setDoctorAvailability(@PathVariable("doctorId") Long id){
		
		doctorAvailabilityService.setDoctorAvailability(id);
		
		return new ResponseEntity<>("Successfully added the Availability for the next 7 days",HttpStatus.CREATED);
		
	}
	
	@DeleteMapping("/block")
	@PreAuthorize("hasRole('DOCTOR')")
	public ResponseEntity<String> blockAvailability( @Valid @RequestBody DoctorAvailabilityRequest request ){
	
		doctorAvailabilityService.blockAvailability(request.getDoctorId(), request.getAvailableDate());
		
		return new ResponseEntity<>("Successfully blocked the date "+request.getAvailableDate()+".",HttpStatus.OK);
		
	}
	
	@PutMapping("/unblock")
	@PreAuthorize("hasRole('DOCTOR')")
	public ResponseEntity<String> unblockAvailability( @Valid @RequestBody DoctorAvailabilityRequest request){
		
		doctorAvailabilityService.unblockAvailability(request.getDoctorId(), request.getAvailableDate());
		
		return new ResponseEntity<>("Successfully unBlocked the date :"+request.getAvailableDate()+".",HttpStatus.OK);
	}
	
	@GetMapping("/{doctorId}/{date}")
	public ResponseEntity<DoctorAvailabilityDAO> getDoctorAvailability(@PathVariable Long doctorId,@PathVariable String date){
		
		LocalDate queryDate = LocalDate.parse(date);
		
		return new ResponseEntity<>(doctorAvailabilityService.getDoctorAvailability(doctorId, queryDate),HttpStatus.OK);
	}
	
	@GetMapping("/doctor/{doctorId}")
	public ResponseEntity<List<DoctorAvailabilityDAO>> getDoctorAvailabilities(@PathVariable Long doctorId){
		
		
		return new ResponseEntity<>(doctorAvailabilityService.getDoctorAvailabilities(doctorId),HttpStatus.OK);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@GetMapping("/{doctorId}")
	public ResponseEntity<List<DoctorAvailability>>getAvailability(@PathVariable Long doctorId){
		
		return new ResponseEntity<>(doctorAvailabilityService.getDoctorAvailability(doctorId),HttpStatus.OK);
		
	}
	

		
	

}
