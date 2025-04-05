package com.example.service;

import java.time.LocalDate;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.dao.AvailableDoctorDAO;
import com.example.dao.DoctorAvailabilityDAO;
import com.example.exception.ResourceNotFoundException;
import com.example.model.AvailabilitySlot;
import com.example.model.DoctorAvailability;
import com.example.model.User;
import com.example.repository.AvailabilitySlotRepository;
import com.example.repository.DoctorAvailabilityRepository;
import com.example.repository.UserRepository;
import com.example.service.serviceInterfaces.DoctorAvailabilityService;

import jakarta.transaction.Transactional;

@Service
public class DoctorAvailabilityServiceImpl implements DoctorAvailabilityService{
	
	
	private  UserRepository userRepository;
	private  DoctorAvailabilityRepository doctorAvailabilityRepository ;
	private AvailabilitySlotRepository slotRepository;
	
	
	
	
	public DoctorAvailabilityServiceImpl(UserRepository userRepository,
			DoctorAvailabilityRepository doctorAvailabilityRepository, AvailabilitySlotRepository slotRepository) {
		super();
		this.userRepository = userRepository;
		this.doctorAvailabilityRepository = doctorAvailabilityRepository;
		this.slotRepository = slotRepository;
	}



	public List<DoctorAvailability> getDoctorAvailability(Long doctorId){
		
		return doctorAvailabilityRepository.findByDoctorIdOrderByAvailableDateAsc(doctorId);
	}
	
	
	
	private List<AvailabilitySlot> generateTimeSlots(DoctorAvailability availability){
		List<AvailabilitySlot> slots = new ArrayList<>();
		
		LocalTime[] timeArray = {LocalTime.of(9,0), LocalTime.of(10,0), LocalTime.of(11,0), LocalTime.of(13,0), LocalTime.of(14,0), LocalTime.of(15,0)};
		
		for(LocalTime time : timeArray) {
			AvailabilitySlot slot = new AvailabilitySlot(); 
			slot.setDoctorAvailability(availability);
			slot.setTimeSlot(time);
			slot.setBlocked(false);
			slots.add(slot);
		}
		
		return slots;
	}
	
	@Override
	public void setDoctorAvailability(Long doctorId) {
		User doctor = userRepository.findById(doctorId).orElseThrow(()-> new ResourceNotFoundException(" Doctor","id",doctorId));
		
		LocalDate today = LocalDate.now();
		IntStream.range(0, 7).forEach(i->{
			LocalDate date = today.plusDays(i);
			
				DoctorAvailability availability = new DoctorAvailability();
				availability.setDoctorId(doctorId);
				availability.setAvailableDate(date);
				availability.setBlocked(false);
				List<AvailabilitySlot> slots=generateTimeSlots(availability);
				availability.setTimeSlots(slots);
				doctorAvailabilityRepository.save(availability);
				
			
			
		});
	}
	
//	@Override
//	public List<DoctorAvailability> getAvailability(Long doctorId){
//		LocalDate today = LocalDate.now();
//		LocalDate futureDate = today.plusDays(7);
//		
//		return doctorAvailabilityRepository.findByDoctorIdAndAvailableDateBetween(doctorId,today,futureDate);
//		
//	}
	@Override
	public List<DoctorAvailabilityDAO> getAvailability(Long doctorId) {
	    LocalDate today = LocalDate.now();
	    LocalDate futureDate = today.plusDays(7);

	    List<DoctorAvailability> doctorAvailabilities = doctorAvailabilityRepository.findByDoctorIdAndAvailableDateBetween(doctorId,today,futureDate);

        return doctorAvailabilities.stream()
            .map(availability -> {
                // Extract only non-blocked start times
                List<LocalTime> timeSlots = availability.getTimeSlots().stream()
                    .filter(slot -> !slot.isBlocked())
                    .map(slot->slot.getTimeSlot())
                    .collect(Collectors.toList());

                return new DoctorAvailabilityDAO(availability.getDoctorId(), availability.getAvailableDate(), timeSlots);
            })
            .collect(Collectors.toList());
	}
	
	public List<DoctorAvailability> getAvailableDoctors(){
		LocalDate today = LocalDate.now();
		LocalDate futureDate = today.plusDays(7);
		
		return doctorAvailabilityRepository.findByAvailableDateBetween(today,futureDate);
		
	}
	
	@Override
	@Transactional
	public void blockAvailability(Long doctorId, LocalDate date) {
		
		DoctorAvailability availability = doctorAvailabilityRepository.findByDoctorIdAndAvailableDate(doctorId, date);
		availability.setBlocked(true);
		availability.getTimeSlots().forEach(slot-> slot.setBlocked(true));
		
		doctorAvailabilityRepository.save(availability);
		
	} 
	
	@Override
	@Transactional
	public void unblockAvailability(Long doctorId, LocalDate date) {
		DoctorAvailability availability = doctorAvailabilityRepository.findByDoctorIdAndAvailableDate(doctorId, date);
		
		availability.setBlocked(false);
		availability.getTimeSlots().forEach(slot-> slot.setBlocked(false));
		
		doctorAvailabilityRepository.save(availability);
	}
	
	
	
	
	@Scheduled(cron = "0 0 0 * * ?")
	public void scheduledCleanup() {
		LocalDate cutoffDate = LocalDate.now().minusDays(60);
		
		doctorAvailabilityRepository.deleteByAvailableDateBefore(cutoffDate);
		
	}
	
	public DoctorAvailabilityDAO getDoctorAvailability(Long doctorId, LocalDate availableDate) {
        DoctorAvailability doctorAvailability = doctorAvailabilityRepository.findByDoctorIdAndAvailableDate(doctorId, availableDate);

        if (doctorAvailability == null) {
            throw new ResourceNotFoundException("Availability with doctor","id",doctorId);
        }

        // Extract only start times from time slots
        List<LocalTime> startTimes = doctorAvailability.getTimeSlots().stream()
            .map(slot -> slot.getTimeSlot()) // Extract sartTime only
            .collect(Collectors.toList());

        return new DoctorAvailabilityDAO(doctorAvailability.getDoctorId(), doctorAvailability.getAvailableDate(), startTimes);
    }
	
	public List<DoctorAvailabilityDAO> getDoctorAvailabilities(Long doctorId) {
        List<DoctorAvailability> doctorAvailabilities = doctorAvailabilityRepository.findByDoctorIdAndNonBlockedSlots(doctorId);

        return doctorAvailabilities.stream()
            .map(availability -> {
                // Extract only non-blocked start times
                List<LocalTime> timeSlots = availability.getTimeSlots().stream()
                    .filter(slot -> !slot.isBlocked())
                    .map(slot->slot.getTimeSlot())
                    .collect(Collectors.toList());

                return new DoctorAvailabilityDAO(availability.getDoctorId(), availability.getAvailableDate(), timeSlots);
            })
            .collect(Collectors.toList());
    }
	
	
	public List<AvailableDoctorDAO> getAvailableDoctorList() {
	    LocalDate today = LocalDate.now();
	    LocalDate futureDate = today.plusDays(7);
	    List<DoctorAvailability> doctorAvailabilities = doctorAvailabilityRepository.findByAvailableDateBetween(today, futureDate);

	    Set<Long> uniqueDoctorIds = new HashSet<>();
	    return doctorAvailabilities.stream()
	        .filter(availability -> uniqueDoctorIds.add(availability.getDoctorId())) // Add to set and filter duplicates
	        .map(availability -> {
	            Optional<User> doctor = userRepository.findById(availability.getDoctorId());
	            return new AvailableDoctorDAO(availability.getDoctorId(), doctor.get().getName());
	        })
	        .collect(Collectors.toList());
	}

	
	
}
