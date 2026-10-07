package com.pcrms.repository;

import com.pcrms.model.CriminalRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CriminalRecordRepository
        extends JpaRepository<CriminalRecord, Long> {

    Optional<CriminalRecord> findByCriminalId(String criminalId);

    List<CriminalRecord> findByCriminalIdContainingIgnoreCase(String criminalId);

    List<CriminalRecord> findByFullNameContainingIgnoreCase(String fullName);

    List<CriminalRecord> findByPhoneContaining(String phone);

    List<CriminalRecord> findByStatusIgnoreCase(String status);
    long countByStatusIgnoreCase(String status);
}