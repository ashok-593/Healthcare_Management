package com.example.repository;

import com.example.model.DoctorAvailability;
import com.example.model.DoctorAvailabilityId;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorAvailabilityRepository extends JpaRepository<DoctorAvailability, DoctorAvailabilityId> {
	
	@Query("SELECT d FROM DoctorAvailability d WHERE d.doctorId = :doctorId AND EXISTS "
	         + "(SELECT s FROM AvailabilitySlot s WHERE s.doctorAvailability = d AND s.isBlocked = FALSE)")
	List<DoctorAvailability>findByDoctorIdAndNonBlockedSlots(Long doctorId);
	List<DoctorAvailability>findByDoctorIdOrderByAvailableDateAsc(Long doctorId);
	List<DoctorAvailability>findByDoctorIdAndIsBlockedFalse(Long doctorId);
	List<DoctorAvailability>findByDoctorIdAndAvailableDateBetween(Long doctorId,LocalDate startDate, LocalDate end);
	void deleteByAvailableDateBefore(LocalDate availabledate);
	DoctorAvailability findByDoctorIdAndAvailableDate(Long doctorId, LocalDate availableDate);
	
	
	
}
