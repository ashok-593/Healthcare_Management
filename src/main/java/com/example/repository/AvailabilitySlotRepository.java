package com.example.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.model.AvailabilitySlot;

public interface AvailabilitySlotRepository extends JpaRepository<AvailabilitySlot, Long>{
	
	List<AvailabilitySlot> findByDoctorAvailability_DoctorIdAndDoctorAvailability_AvailableDate(Long doctorId, LocalDate availableDate);
	
	AvailabilitySlot findByDoctorAvailability_DoctorIdAndDoctorAvailability_AvailableDateAndTimeSlotAndIsBlockedFalse(Long doctorId, LocalDate newAvailableDate, LocalTime newTimeSlot);
	
	AvailabilitySlot findByDoctorAvailability_DoctorIdAndDoctorAvailability_AvailableDateAndTimeSlot(Long doctorId, LocalDate availableDate, LocalTime timeSlot);
	
	/*@Query("SELECT s FROM AvailabilitySlot s WHERE s.doctorId = :doctorId AND s.availableDate = :appointmentDate AND s.timeSlot = :timeSlot")
    AvailabilitySlot findByDoctorIdAndAvailableDateAndTimeSlot(@Param("doctorId") Long doctorId, 
                                                  @Param("appointmentDate") LocalDate appointmentDate, 
                                                  @Param("timeSlot") LocalTime timeSlot);*/
}
