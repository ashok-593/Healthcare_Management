package com.example.dao;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

//import com.example.dao.TimeSlotDAO;

import lombok.Data;

@Data
public class DoctorAvailabilityDAO {
	private Long doctorId;
	private LocalDate availableDate;
	private List<LocalTime> timeSlots;
	public Long getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(Long doctorId) {
		this.doctorId = doctorId;
	}
	public LocalDate getAvailableDate() {
		return availableDate;
	}
	public void setAvailableDate(LocalDate availableDate) {
		this.availableDate = availableDate;
	}
	public List<LocalTime> getTimeSlots() {
		return timeSlots;
	}
	public void setTimeSlots(List<LocalTime> timeSlots) {
		this.timeSlots = timeSlots;
	}
	public DoctorAvailabilityDAO(Long doctorId, LocalDate availableDate, List<LocalTime> startTimes) {
		super();
		this.doctorId = doctorId;
		this.availableDate = availableDate;
		this.timeSlots = startTimes;
	}
	public DoctorAvailabilityDAO() {
		super();
	}
	
	
	

}
