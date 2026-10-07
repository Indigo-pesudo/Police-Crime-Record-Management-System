package com.pcrms.controller;

import com.pcrms.model.CriminalFIRLink;
import com.pcrms.model.CriminalRecord;
import com.pcrms.model.FIR;
import com.pcrms.repository.CriminalFIRLinkRepository;
import com.pcrms.repository.CriminalRecordRepository;
import com.pcrms.repository.FIRRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/criminal-fir-links")
public class CriminalFIRLinkController {

    private final CriminalFIRLinkRepository linkRepository;
    private final CriminalRecordRepository criminalRepository;
    private final FIRRepository firRepository;

    public CriminalFIRLinkController(
            CriminalFIRLinkRepository linkRepository,
            CriminalRecordRepository criminalRepository,
            FIRRepository firRepository) {

        this.linkRepository = linkRepository;
        this.criminalRepository = criminalRepository;
        this.firRepository = firRepository;
    }

    // Link criminal record to FIR
    @PostMapping
    public ResponseEntity<?> linkCriminalToFIR(
            @RequestParam Long criminalId,
            @RequestParam Long firId,
            Authentication authentication) {

        CriminalRecord criminal =
                criminalRepository.findById(criminalId).orElse(null);

        if (criminal == null) {
            return ResponseEntity.notFound().build();
        }

        FIR fir =
                firRepository.findById(firId).orElse(null);

        if (fir == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of(
                            "message",
                            "FIR not found"
                    ));
        }

        if (linkRepository.existsByCriminalRecordIdAndFirId(
                criminalId, firId)) {

            return ResponseEntity.badRequest()
                    .body(Map.of(
                            "message",
                            "Criminal is already linked to this FIR"
                    ));
        }

        CriminalFIRLink link = new CriminalFIRLink();

        link.setCriminalRecord(criminal);
        link.setFir(fir);
        link.setLinkedAt(LocalDateTime.now());
        link.setLinkedBy(authentication.getName());

        CriminalFIRLink saved =
                linkRepository.save(link);

        return ResponseEntity.ok(
                Map.of(
                        "message", "Criminal linked to FIR successfully",
                        "linkId", saved.getId()
                )
        );
    }

    // Get FIRs associated with a criminal
    // Get FIRs associated with a criminal
@GetMapping("/criminal/{criminalId}")
public ResponseEntity<?> getCriminalFIRs(
        @PathVariable Long criminalId) {

    if (!criminalRepository.existsById(criminalId)) {
        return ResponseEntity.notFound().build();
    }

    List<CriminalFIRLink> links =
            linkRepository.findByCriminalRecordId(criminalId);

    List<Map<String, Object>> firs = links.stream()
            .map(link -> {
                FIR fir = link.getFir();

                Map<String, Object> firData = new HashMap<>();

                firData.put("id", fir.getId());
                firData.put("firNumber", fir.getFirNumber());
                firData.put("date", fir.getDate());
                firData.put("complainantName", fir.getComplainantName());
                firData.put("complainantContact", fir.getComplainantContact());
                firData.put("incidentLocation", fir.getIncidentLocation());
                firData.put("description", fir.getDescription());
                firData.put("status", fir.getStatus());
                firData.put("linkedAt", link.getLinkedAt());
                firData.put("linkedBy", link.getLinkedBy());

                return firData;
            })
            .toList();

    return ResponseEntity.ok(firs);
}

    // Get criminals associated with an FIR
   // Get criminals associated with an FIR
@GetMapping("/fir/{firId}")
public ResponseEntity<?> getFIRCriminals(
        @PathVariable Long firId) {

    if (!firRepository.existsById(firId)) {
        return ResponseEntity.notFound().build();
    }

    List<CriminalFIRLink> links =
            linkRepository.findByFirId(firId);

    List<Map<String, Object>> criminals = links.stream()
            .map(link -> {
                CriminalRecord criminal = link.getCriminalRecord();

                Map<String, Object> criminalData = new HashMap<>();

                criminalData.put("id", criminal.getId());
                criminalData.put("criminalId", criminal.getCriminalId());
                criminalData.put("fullName", criminal.getFullName());
                criminalData.put("dateOfBirth", criminal.getDateOfBirth());
                criminalData.put("gender", criminal.getGender());
                criminalData.put("phone", criminal.getPhone());
                criminalData.put("address", criminal.getAddress());
                criminalData.put("identificationMark",
                        criminal.getIdentificationMark());
                criminalData.put("status", criminal.getStatus());
                criminalData.put("linkedAt", link.getLinkedAt());
                criminalData.put("linkedBy", link.getLinkedBy());

                return criminalData;
            })
            .toList();

    return ResponseEntity.ok(criminals);
}
}