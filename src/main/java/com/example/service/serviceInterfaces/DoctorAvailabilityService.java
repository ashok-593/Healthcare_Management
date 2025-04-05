package com.example.service.serviceInterfaces;

import java.time.LocalDate;
import java.util.List;

import com.example.dao.DoctorAvailabilityDAO;
import com.example.model.DoctorAvailability;

public interface DoctorAvailabilityService {
	public List<DoctorAvailability> getDoctorAvailability(Long doctorId);
	public void setDoctorAvailability(Long doctorId);
	public List<DoctorAvailabilityDAO> getAvailability(Long doctorId);
	public void blockAvailability(Long doctorId, LocalDate date);
	public void unblockAvailability(Long doctorId, LocalDate date);
	

}
