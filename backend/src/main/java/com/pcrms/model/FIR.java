package com.pcrms.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "firs")
public class FIR {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fir_number", nullable = false, unique = true)
    private String firNumber;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private String complainantName;

    @Column(nullable = false)
    private String complainantContact;

    @Column(nullable = false)
    private String incidentLocation;

    @Column(nullable = false, length = 2000)
    private String description;

    @Column(nullable = false)
    private String status;

    public FIR() {
    }

    public Long getId() {
        return id;
    }

    public String getFirNumber() {
        return firNumber;
    }

    public void setFirNumber(String firNumber) {
        this.firNumber = firNumber;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getComplainantName() {
        return complainantName;
    }

    public void setComplainantName(String complainantName) {
        this.complainantName = complainantName;
    }

    public String getComplainantContact() {
        return complainantContact;
    }

    public void setComplainantContact(String complainantContact) {
        this.complainantContact = complainantContact;
    }

    public String getIncidentLocation() {
        return incidentLocation;
    }

    public void setIncidentLocation(String incidentLocation) {
        this.incidentLocation = incidentLocation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}