package com.pcrms.repository;

import com.pcrms.model.EvidenceHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EvidenceHistoryRepository
        extends JpaRepository<EvidenceHistory, Long> {

    List<EvidenceHistory> findByEvidenceIdOrderByTimestampDesc(
            Long evidenceId);
}