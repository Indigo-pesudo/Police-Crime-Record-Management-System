package com.pcrms.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "evidence_history")
public class EvidenceHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long evidenceId;

    @Column(nullable = false)
    private String evidenceNumber;

    @Column(nullable = false)
    private String action;

    private String fromLocation;

    private String toLocation;

    private String previousCustodian;

    private String newCustodian;

    @Column(nullable = false)
    private String transferredBy;

    @Column(length = 1000)
    private String remarks;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    public EvidenceHistory() {
    }

    public Long getId() {
        return id;
    }

    public Long getEvidenceId() {
        return evidenceId;
    }

    public void setEvidenceId(Long evidenceId) {
        this.evidenceId = evidenceId;
    }

    public String getEvidenceNumber() {
        return evidenceNumber;
    }

    public void setEvidenceNumber(String evidenceNumber) {
        this.evidenceNumber = evidenceNumber;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getFromLocation() {
        return fromLocation;
    }

    public void setFromLocation(String fromLocation) {
        this.fromLocation = fromLocation;
    }

    public String getToLocation() {
        return toLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    public String getPreviousCustodian() {
        return previousCustodian;
    }

    public void setPreviousCustodian(String previousCustodian) {
        this.previousCustodian = previousCustodian;
    }

    public String getNewCustodian() {
        return newCustodian;
    }

    public void setNewCustodian(String newCustodian) {
        this.newCustodian = newCustodian;
    }

    public String getTransferredBy() {
        return transferredBy;
    }

    public void setTransferredBy(String transferredBy) {
        this.transferredBy = transferredBy;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @PrePersist
    public void onCreate() {
        timestamp = LocalDateTime.now();
    }
}