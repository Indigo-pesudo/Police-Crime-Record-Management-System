package com.pcrms.controller;

import com.pcrms.model.CriminalRecord;
import com.pcrms.repository.CriminalRecordRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/criminal-records")
public class CriminalRecordController {

    private final CriminalRecordRepository repository;

    public CriminalRecordController(
            CriminalRecordRepository repository) {
        this.repository = repository;
    }

    // Create criminal record
    @PostMapping
    public ResponseEntity<?> createCriminalRecord(
            @RequestBody CriminalRecord criminalRecord) {

        if (repository.findByCriminalId(
                criminalRecord.getCriminalId()).isPresent()) {

            return ResponseEntity.badRequest()
                    .body(Map.of(
                            "message",
                            "Criminal ID already exists"
                    ));
        }

        if (criminalRecord.getStatus() == null ||
                criminalRecord.getStatus().isBlank()) {

            criminalRecord.setStatus("ACTIVE");
        }

        CriminalRecord saved =
                repository.save(criminalRecord);

        return ResponseEntity.ok(saved);
    }

    // Get all criminal records
    @GetMapping
    public ResponseEntity<List<CriminalRecord>> getAllRecords() {
        return ResponseEntity.ok(repository.findAll());
    }

    // Get by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getRecordById(
            @PathVariable Long id) {

        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() ->
                        ResponseEntity.notFound().build());
    }

    // Search
    @GetMapping("/search")
    public ResponseEntity<?> searchCriminalRecords(

            @RequestParam(required = false)
            String criminalId,

            @RequestParam(required = false)
            String fullName,

            @RequestParam(required = false)
            String phone,

            @RequestParam(required = false)
            String status) {

        if (criminalId != null &&
                !criminalId.isBlank()) {

            return ResponseEntity.ok(
                    repository
                            .findByCriminalIdContainingIgnoreCase(
                                    criminalId
                            )
            );
        }

        if (fullName != null &&
                !fullName.isBlank()) {

            return ResponseEntity.ok(
                    repository
                            .findByFullNameContainingIgnoreCase(
                                    fullName
                            )
            );
        }

        if (phone != null &&
                !phone.isBlank()) {

            return ResponseEntity.ok(
                    repository.findByPhoneContaining(phone)
            );
        }

        if (status != null &&
                !status.isBlank()) {

            return ResponseEntity.ok(
                    repository.findByStatusIgnoreCase(status)
            );
        }

        return ResponseEntity.badRequest()
                .body(Map.of(
                        "message",
                        "Provide a search criterion"
                ));
    }
}