package com.pcrms.controller;

import com.pcrms.model.FIR;
import com.pcrms.repository.FIRRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/firs")
public class FIRController {

    private final FIRRepository firRepository;

    public FIRController(FIRRepository firRepository) {
        this.firRepository = firRepository;
    }

    // Create FIR
    @PostMapping
    public ResponseEntity<?> createFIR(@RequestBody FIR fir) {

        // Validate required fields
        if (fir.getFirNumber() == null || fir.getFirNumber().isBlank()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "FIR number is required"));
        }

        if (fir.getDate() == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Date is required"));
        }

        if (fir.getComplainantName() == null ||
                fir.getComplainantName().isBlank()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Complainant name is required"));
        }

        if (fir.getComplainantContact() == null ||
                fir.getComplainantContact().isBlank()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Complainant contact is required"));
        }

        if (fir.getIncidentLocation() == null ||
                fir.getIncidentLocation().isBlank()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Incident location is required"));
        }

        if (fir.getDescription() == null ||
                fir.getDescription().isBlank()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Description is required"));
        }

        // Check duplicate FIR number
        if (firRepository.findByFirNumber(fir.getFirNumber()).isPresent()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "FIR number already exists"));
        }

        // New FIR always starts as OPEN
        fir.setStatus("OPEN");

        FIR savedFIR = firRepository.save(fir);

        return ResponseEntity.ok(savedFIR);
    }

    // Get all FIRs
    @GetMapping
    public ResponseEntity<List<FIR>> getAllFIRs() {
        return ResponseEntity.ok(firRepository.findAll());
    }

    // Get FIR by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getFIRById(@PathVariable Long id) {

        return firRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() ->
                        ResponseEntity.notFound().build());
    }

    // Search FIR
    @GetMapping("/search")
    public ResponseEntity<?> searchFirs(
            @RequestParam(required = false) String firNumber,
            @RequestParam(required = false) String complainantName,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String status) {

        if (firNumber != null && !firNumber.isBlank()) {
            return ResponseEntity.ok(
                    firRepository.findByFirNumberContainingIgnoreCase(firNumber)
            );
        }

        if (complainantName != null && !complainantName.isBlank()) {
            return ResponseEntity.ok(
                    firRepository.findByComplainantNameContainingIgnoreCase(
                            complainantName
                    )
            );
        }

        if (location != null && !location.isBlank()) {
            return ResponseEntity.ok(
                    firRepository.findByIncidentLocationContainingIgnoreCase(
                            location
                    )
            );
        }

        if (status != null && !status.isBlank()) {
            return ResponseEntity.ok(
                    firRepository.findByStatusIgnoreCase(status)
            );
        }

        return ResponseEntity.badRequest()
                .body(Map.of(
                        "message",
                        "Provide at least one search criterion"
                ));
    }

    // Update FIR
    @PutMapping("/{id}")
    public ResponseEntity<?> updateFIR(
            @PathVariable Long id,
            @RequestBody FIR updatedFIR) {

        // Validate required fields
        if (updatedFIR.getDate() == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Date is required"));
        }

        if (updatedFIR.getComplainantName() == null ||
                updatedFIR.getComplainantName().isBlank()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Complainant name is required"));
        }

        if (updatedFIR.getComplainantContact() == null ||
                updatedFIR.getComplainantContact().isBlank()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Complainant contact is required"));
        }

        if (updatedFIR.getIncidentLocation() == null ||
                updatedFIR.getIncidentLocation().isBlank()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Incident location is required"));
        }

        if (updatedFIR.getDescription() == null ||
                updatedFIR.getDescription().isBlank()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Description is required"));
        }

        return firRepository.findById(id)
                .map(existingFIR -> {

                    existingFIR.setDate(updatedFIR.getDate());

                    existingFIR.setComplainantName(
                            updatedFIR.getComplainantName()
                    );

                    existingFIR.setComplainantContact(
                            updatedFIR.getComplainantContact()
                    );

                    existingFIR.setIncidentLocation(
                            updatedFIR.getIncidentLocation()
                    );

                    existingFIR.setDescription(
                            updatedFIR.getDescription()
                    );

                    // Keep original FIR number and status
                    FIR savedFIR = firRepository.save(existingFIR);

                    return ResponseEntity.ok(savedFIR);
                })
                .orElseGet(() ->
                        ResponseEntity.notFound().build());
    }

    // Delete FIR
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFIR(@PathVariable Long id) {

        if (!firRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        firRepository.deleteById(id);

        return ResponseEntity.ok(
                Map.of("message", "FIR deleted successfully")
        );
    }
}