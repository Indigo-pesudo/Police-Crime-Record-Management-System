package com.pcrms.controller;

import com.pcrms.model.FIR;
import com.pcrms.repository.FIRRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/firs")
public class FIRController {

    private final FIRRepository firRepository;

    public FIRController(FIRRepository firRepository) {
        this.firRepository = firRepository;
    }

    // Register a new FIR
    @PostMapping
    public ResponseEntity<FIR> createFIR(@RequestBody FIR fir) {

        if (firRepository.findByFirNumber(fir.getFirNumber()).isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        fir.setStatus("OPEN");

        FIR savedFIR = firRepository.save(fir);

        return ResponseEntity.ok(savedFIR);
    }

    // Get all FIRs
    @GetMapping
    public List<FIR> getAllFIRs() {
        return firRepository.findAll();
    }

    // Get FIR by ID
    @GetMapping("/{id}")
    public ResponseEntity<FIR> getFIRById(@PathVariable Long id) {

        return firRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}