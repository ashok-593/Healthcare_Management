package com.example.dto;



import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ConsultationRequest {
	
	@Min(value=1,message="Appointment ID cannot be null")
	private Long appointmentId;
	
	@NotBlank(message="Notes cannot be blank")
	@Size(min=5,message="Notes must be atleast 5 characters")
    private String notes;
	
	@NotBlank(message="Prescription cannot be blank")
	@Size(min=5,message="Prescription must be atleast 5 characters")
    private String prescription;
	public Long getAppointmentId() {
		return appointmentId;
	}
	public void setAppointmentId(Long appointmentId) {
		this.appointmentId = appointmentId;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getPrescription() {
		return prescription;
	}
	public void setPrescription(String prescription) {
		this.prescription = prescription;
	}
    
    

}
