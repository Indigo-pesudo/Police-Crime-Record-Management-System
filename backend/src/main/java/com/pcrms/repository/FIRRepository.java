package com.pcrms.repository;

import com.pcrms.model.FIR;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FIRRepository extends JpaRepository<FIR, Long> {

    Optional<FIR> findByFirNumber(String firNumber);

    List<FIR> findByFirNumberContainingIgnoreCase(String firNumber);

    List<FIR> findByComplainantNameContainingIgnoreCase(String complainantName);

    List<FIR> findByIncidentLocationContainingIgnoreCase(String incidentLocation);

    List<FIR> findByStatusIgnoreCase(String status);

    long countByStatusIgnoreCase(String status);
}