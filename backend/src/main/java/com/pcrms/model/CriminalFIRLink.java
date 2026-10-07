package com.pcrms.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "criminal_fir_links",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_criminal_fir",
            columnNames = {"criminal_id", "fir_id"}
        )
    }
)
public class CriminalFIRLink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "criminal_id", nullable = false)
    private CriminalRecord criminalRecord;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fir_id", nullable = false)
    private FIR fir;

    @Column(nullable = false)
    private LocalDateTime linkedAt;

    private String linkedBy;

    public CriminalFIRLink() {
    }

    public Long getId() {
        return id;
    }

    public CriminalRecord getCriminalRecord() {
        return criminalRecord;
    }

    public void setCriminalRecord(CriminalRecord criminalRecord) {
        this.criminalRecord = criminalRecord;
    }

    public FIR getFir() {
        return fir;
    }

    public void setFir(FIR fir) {
        this.fir = fir;
    }

    public LocalDateTime getLinkedAt() {
        return linkedAt;
    }

    public void setLinkedAt(LocalDateTime linkedAt) {
        this.linkedAt = linkedAt;
    }

    public String getLinkedBy() {
        return linkedBy;
    }

    public void setLinkedBy(String linkedBy) {
        this.linkedBy = linkedBy;
    }
}