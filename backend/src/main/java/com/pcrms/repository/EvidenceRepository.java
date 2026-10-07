package com.pcrms.repository;

import com.pcrms.model.Evidence;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EvidenceRepository extends JpaRepository<Evidence, Long> {

    Optional<Evidence> findByEvidenceNumber(String evidenceNumber);

    List<Evidence> findByFirNumberIgnoreCase(String firNumber);

    List<Evidence> findByCaseNumberIgnoreCase(String caseNumber);

    List<Evidence> findByStatusIgnoreCase(String status);

    List<Evidence> findByEvidenceTypeContainingIgnoreCase(String evidenceType);
}