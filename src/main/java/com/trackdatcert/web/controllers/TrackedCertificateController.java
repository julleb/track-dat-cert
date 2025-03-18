package com.trackdatcert.web.controllers;

import com.trackdatcert.services.certificate.TrackedCertificateService;
import com.trackdatcert.services.certificate.model.TrackedCertificate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/trackedCertificates")
@Slf4j
@AllArgsConstructor
public class TrackedCertificateController {

    private final TrackedCertificateService trackedCertificateService;

    @GetMapping
    public ResponseEntity<List<TrackedCertificate>> getTrackedCertificatesForUser() {
        var trackedCerts = trackedCertificateService.getTrackedCertificatesForCurrentUser();
        return ResponseEntity.ok().body(trackedCerts);
    }

    @PostMapping
    public ResponseEntity<String> createTrackedCertificate() {
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{name}")
    public ResponseEntity<String> updateTrackedCertificate() {
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<String> deleteTrackedCertificate() {
        return ResponseEntity.noContent().build();
    }
}
