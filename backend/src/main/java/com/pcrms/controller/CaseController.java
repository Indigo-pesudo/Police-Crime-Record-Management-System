package com.pcrms.controller;

import com.pcrms.model.Case;
import com.pcrms.repository.CaseRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cases")
public class CaseController {

    private final CaseRepository caseRepository;

    public CaseController(CaseRepository caseRepository) {
        this.caseRepository = caseRepository;
    }

    @PostMapping
    public ResponseEntity<Case> createCase(
            @RequestBody Case caseData) {

        if (caseRepository
                .findByCaseNumber(caseData.getCaseNumber())
                .isPresent()) {

            return ResponseEntity.badRequest().build();
        }

        caseData.setStatus("OPEN");

        Case savedCase =
                caseRepository.save(caseData);

        return ResponseEntity.ok(savedCase);
    }

    @GetMapping
    public List<Case> getAllCases() {

        return caseRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Case> getCaseById(
            @PathVariable Long id) {

        return caseRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/fir/{firNumber}")
public ResponseEntity<List<Case>> getCasesByFirNumber(
        @PathVariable String firNumber) {

    List<Case> cases =
            caseRepository.findByFirNumber(firNumber);

    return ResponseEntity.ok(cases);
}
    @GetMapping("/stats")
public long getActiveCases() {
    return caseRepository.countByStatus("OPEN");
}
}