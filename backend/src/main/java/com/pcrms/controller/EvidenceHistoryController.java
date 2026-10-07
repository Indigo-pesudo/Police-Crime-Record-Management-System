package com.pcrms.controller;

import com.pcrms.model.Evidence;
import com.pcrms.model.EvidenceHistory;
import com.pcrms.repository.EvidenceHistoryRepository;
import com.pcrms.repository.EvidenceRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/evidence")
public class EvidenceHistoryController {

    private final EvidenceRepository evidenceRepository;
    private final EvidenceHistoryRepository historyRepository;

    public EvidenceHistoryController(
            EvidenceRepository evidenceRepository,
            EvidenceHistoryRepository historyRepository) {

        this.evidenceRepository = evidenceRepository;
        this.historyRepository = historyRepository;
    }

    @PostMapping("/{id}/transfer")
    public ResponseEntity<?> transferEvidence(
            @PathVariable Long id,
            @RequestBody Map<String, String> request,
            Authentication authentication) {

        Evidence evidence = evidenceRepository.findById(id).orElse(null);

        if (evidence == null) {
            return ResponseEntity.notFound().build();
        }

        String toLocation = request.get("toLocation");
        String newCustodian = request.get("newCustodian");
        String remarks = request.get("remarks");

        boolean locationChanged =
                toLocation != null && !toLocation.isBlank();

        boolean custodianChanged =
                newCustodian != null && !newCustodian.isBlank();

        if (!locationChanged && !custodianChanged) {
            return ResponseEntity.badRequest()
                    .body("Provide a new location or new custodian");
        }

        String fromLocation = evidence.getStorageLocation();
        String previousCustodian = evidence.getCurrentCustodian();
        String transferredBy = authentication.getName();

        // Update location only if a new location was provided
        if (locationChanged) {
            evidence.setStorageLocation(toLocation.trim());
        }

        // Update custodian only if a new custodian was provided
        if (custodianChanged) {
            evidence.setCurrentCustodian(newCustodian.trim());
        }

        evidenceRepository.save(evidence);

        // Create history record
        EvidenceHistory history = new EvidenceHistory();

        history.setEvidenceId(evidence.getId());
        history.setEvidenceNumber(evidence.getEvidenceNumber());

        if (locationChanged && custodianChanged) {
            history.setAction("CUSTODY_AND_LOCATION_TRANSFER");
        } else if (locationChanged) {
            history.setAction("LOCATION_TRANSFER");
        } else {
            history.setAction("CUSTODY_TRANSFER");
        }

        history.setFromLocation(fromLocation);
        history.setToLocation(
                locationChanged ? toLocation.trim() : fromLocation
        );

        history.setPreviousCustodian(previousCustodian);
        history.setNewCustodian(
                custodianChanged ? newCustodian.trim() : previousCustodian
        );

        history.setTransferredBy(transferredBy);
        history.setRemarks(remarks);

        historyRepository.save(history);

        return ResponseEntity.ok(history);
    }

    @GetMapping("/{id}/history")
    public ResponseEntity<?> getEvidenceHistory(@PathVariable Long id) {

        if (!evidenceRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        List<EvidenceHistory> history =
                historyRepository.findByEvidenceIdOrderByTimestampDesc(id);

        return ResponseEntity.ok(history);
    }
}