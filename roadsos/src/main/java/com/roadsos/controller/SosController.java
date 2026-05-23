package com.roadsos.controller;

import com.roadsos.model.SeverityLevel;
import com.roadsos.model.SosRequest;
import com.roadsos.service.SosService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/sos")
public class SosController {

    @Autowired
    private SosService sosService;

    // Trigger a new SOS
    @PostMapping
    public ResponseEntity<Map<String, Object>> triggerSos(
            @RequestParam @NotNull Double lat,
            @RequestParam @NotNull Double lng,
            @RequestParam @NotNull SeverityLevel severity,
            @RequestParam(required = false) String description) {

        Map<String, Object> response = 
                sosService.handleSos(lat, lng, severity, description);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // Get a specific SOS by id
    @GetMapping("/{id}")
    public ResponseEntity<SosRequest> getSosById(@PathVariable Long id) {
        return ResponseEntity.ok(sosService.getSosById(id));
    }

    // Get all pending SOS requests (admin use)
    @GetMapping("/pending")
    public ResponseEntity<List<SosRequest>> getPendingSos() {
        return ResponseEntity.ok(sosService.getPendingSos());
    }
}
