package com.pcrms.repository;

import com.pcrms.model.Case;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CaseRepository extends JpaRepository<Case, Long> {

    Optional<Case> findByCaseNumber(String caseNumber);

    long countByStatus(String status);
}