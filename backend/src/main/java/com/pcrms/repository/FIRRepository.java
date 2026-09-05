package com.pcrms.repository;

import com.pcrms.model.FIR;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FIRRepository extends JpaRepository<FIR, Long> {

    Optional<FIR> findByFirNumber(String firNumber);

}