package com.pcrms.controller;

import com.pcrms.repository.CriminalRecordRepository;
import com.pcrms.repository.FIRRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final FIRRepository firRepository;
    private final CriminalRecordRepository criminalRecordRepository;

    public ReportController(
            FIRRepository firRepository,
            CriminalRecordRepository criminalRecordRepository) {

        this.firRepository = firRepository;
        this.criminalRecordRepository = criminalRecordRepository;
    }

    // FIR Summary
    @GetMapping("/fir-summary")
    public ResponseEntity<?> getFIRSummary() {

        long totalFIRs = firRepository.count();
        long openFIRs = firRepository.countByStatusIgnoreCase("OPEN");
        long closedFIRs = firRepository.countByStatusIgnoreCase("CLOSED");

        return ResponseEntity.ok(
                Map.of(
                        "totalFIRs", totalFIRs,
                        "openFIRs", openFIRs,
                        "closedFIRs", closedFIRs
                )
        );
    }

    // Criminal Records Summary
    @GetMapping("/criminal-summary")
    public ResponseEntity<?> getCriminalSummary() {

        long totalCriminals = criminalRecordRepository.count();
        long activeCriminals =
                criminalRecordRepository.countByStatusIgnoreCase("ACTIVE");
        long inactiveCriminals =
                criminalRecordRepository.countByStatusIgnoreCase("INACTIVE");

        return ResponseEntity.ok(
                Map.of(
                        "totalCriminals", totalCriminals,
                        "activeCriminals", activeCriminals,
                        "inactiveCriminals", inactiveCriminals
                )
        );
    }
}