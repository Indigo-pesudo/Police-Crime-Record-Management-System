package com.pcrms.repository;

import com.pcrms.model.CriminalFIRLink;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CriminalFIRLinkRepository
        extends JpaRepository<CriminalFIRLink, Long> {

    boolean existsByCriminalRecordIdAndFirId(
            Long criminalRecordId,
            Long firId
    );

    List<CriminalFIRLink> findByCriminalRecordId(Long criminalRecordId);

    List<CriminalFIRLink> findByFirId(Long firId);
}