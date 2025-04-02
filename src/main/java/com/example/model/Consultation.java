package com.example.model;


import jakarta.persistence.*;


@Entity
@Table(name = "consultations")
public class Consultation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long consultationId;

    @OneToOne
    @JoinColumn(name = "appointmentId", referencedColumnName="appointmentId",nullable=false,unique=true)
    private Appointment appointment;
    
    @Column(name="notes",length=1000)
    private String notes;
    
    @Column(name="prescription",length=1000)
    private String prescription;
      
    public Consultation() {
		super();
	}

	public Consultation(Appointment appointment, String notes, String prescription) {
		super();
		this.appointment = appointment;
		this.notes = notes;
		this.prescription = prescription;
		
	}

    // Getters and Setters
    public Long getConsultationId() {
        return consultationId;
    }

    public void setConsultationId(Long consultationId) {
        this.consultationId = consultationId;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
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