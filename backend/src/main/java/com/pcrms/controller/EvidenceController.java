package com.pcrms.controller;

import com.pcrms.model.Evidence;
import com.pcrms.repository.EvidenceRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/evidence")
public class EvidenceController {

    private final EvidenceRepository evidenceRepository;

    public EvidenceController(EvidenceRepository evidenceRepository) {
        this.evidenceRepository = evidenceRepository;
    }

    @PostMapping
    public ResponseEntity<?> createEvidence(
            @RequestBody Evidence evidence) {

        if (evidence.getEvidenceNumber() == null ||
                evidence.getEvidenceNumber().isBlank()) {

            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Evidence number is required"));
        }

        if (evidence.getFirNumber() == null ||
                evidence.getFirNumber().isBlank()) {

            return ResponseEntity.badRequest()
                    .body(Map.of("message", "FIR number is required"));
        }

        if (evidenceRepository
                .findByEvidenceNumber(evidence.getEvidenceNumber())
                .isPresent()) {

            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Evidence number already exists"));
        }

        if (evidence.getCollectedDate() == null) {
            evidence.setCollectedDate(LocalDate.now());
        }

        if (evidence.getStatus() == null ||
                evidence.getStatus().isBlank()) {

            evidence.setStatus("IN_STORAGE");
        }

        // Set the initial custodian
        if (evidence.getCurrentCustodian() == null ||
                evidence.getCurrentCustodian().isBlank()) {

            evidence.setCurrentCustodian(evidence.getCollectedBy());
        }

        Evidence saved = evidenceRepository.save(evidence);

        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public ResponseEntity<List<Evidence>> getAllEvidence() {
        return ResponseEntity.ok(evidenceRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEvidenceById(
            @PathVariable Long id) {

        return evidenceRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchEvidence(
            @RequestParam(required = false) String evidenceNumber,
            @RequestParam(required = false) String firNumber,
            @RequestParam(required = false) String caseNumber,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String evidenceType) {

        if (evidenceNumber != null && !evidenceNumber.isBlank()) {
            return ResponseEntity.ok(
                    evidenceRepository.findByEvidenceNumber(
                            evidenceNumber)
                            .map(List::of)
                            .orElse(List.of())
            );
        }

        if (firNumber != null && !firNumber.isBlank()) {
            return ResponseEntity.ok(
                    evidenceRepository.findByFirNumberIgnoreCase(firNumber)
            );
        }

        if (caseNumber != null && !caseNumber.isBlank()) {
            return ResponseEntity.ok(
                    evidenceRepository.findByCaseNumberIgnoreCase(caseNumber)
            );
        }

        if (status != null && !status.isBlank()) {
            return ResponseEntity.ok(
                    evidenceRepository.findByStatusIgnoreCase(status)
            );
        }

        if (evidenceType != null && !evidenceType.isBlank()) {
            return ResponseEntity.ok(
                    evidenceRepository.findByEvidenceTypeContainingIgnoreCase(
                            evidenceType)
            );
        }

        return ResponseEntity.badRequest()
                .body(Map.of(
                        "message",
                        "Provide at least one search criterion"
                ));
    }
}