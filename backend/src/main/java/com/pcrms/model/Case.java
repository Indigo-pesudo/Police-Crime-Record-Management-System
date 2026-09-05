package com.pcrms.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "cases")
public class Case {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String caseNumber;

    @Column(nullable = false)
    private String firNumber;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String investigatingOfficer;

    @Column(nullable = false)
    private LocalDate openedDate;

    @Column(nullable = false)
    private String status;

    @Column(length = 2000)
    private String description;

    public Case() {
    }

    public Long getId() {
        return id;
    }

    public String getCaseNumber() {
        return caseNumber;
    }

    public void setCaseNumber(String caseNumber) {
        this.caseNumber = caseNumber;
    }

    public String getFirNumber() {
        return firNumber;
    }

    public void setFirNumber(String firNumber) {
        this.firNumber = firNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInvestigatingOfficer() {
        return investigatingOfficer;
    }

    public void setInvestigatingOfficer(String investigatingOfficer) {
        this.investigatingOfficer = investigatingOfficer;
    }

    public LocalDate getOpenedDate() {
        return openedDate;
    }

    public void setOpenedDate(LocalDate openedDate) {
        this.openedDate = openedDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}