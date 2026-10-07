package com.pcrms.repository;

import com.pcrms.model.EvidenceTransfer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EvidenceTransferRepository
        extends JpaRepository<EvidenceTransfer, Long> {

    List<EvidenceTransfer> findByEvidenceIdOrderByTransferredAtDesc(
            Long evidenceId
    );
}