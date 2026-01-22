package com.hen.phishingscanner.controller;

import com.hen.phishingscanner.model.EmailScanRequest;
import com.hen.phishingscanner.model.ScanResult;
import com.hen.phishingscanner.service.ScanService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ScanController {

    private final ScanService scanService;

    public ScanController(ScanService scanService) {
        this.scanService = scanService;
    }

    @PostMapping("/scan")
    public ScanResult scan(@Valid @RequestBody EmailScanRequest req) {
        return scanService.scan(req);
    }
}
