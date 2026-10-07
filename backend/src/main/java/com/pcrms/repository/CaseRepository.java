package com.pcrms.repository;

import com.pcrms.model.Case;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CaseRepository extends JpaRepository<Case, Long> {

    Optional<Case> findByCaseNumber(String caseNumber);

    List<Case> findByFirNumber(String firNumber);

    long countByStatus(String status);
}