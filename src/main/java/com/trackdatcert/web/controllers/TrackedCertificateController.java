package com.trackdatcert.web.controllers;

import static com.trackdatcert.web.controllers.TrackedCertificateController.RESOURCE_NAME;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(RESOURCE_NAME)
@Slf4j
@AllArgsConstructor
public class TrackedCertificateController {

    private final TrackedCertificateService trackedCertificateService;
    static final String RESOURCE_NAME = "/trackedCertificates";

    @GetMapping
    public ResponseEntity<List<TrackedCertificate>> getTrackedCertificatesForUser() {
        var trackedCerts = trackedCertificateService.getTrackedCertificatesForCurrentUser();
        return ResponseEntity.ok().body(trackedCerts);
    }

    @PostMapping
    public ResponseEntity<String> createTrackedCertificate(TrackedCertificate trackedCertificate) {
        trackedCertificateService.addTrackedCertificate(trackedCertificate);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{name}")
    public ResponseEntity<String> updateTrackedCertificate() {
        throw new UnsupportedOperationException("Not implemented");
        //return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<String> deleteTrackedCertificate(@PathVariable(value = "name") String name) {
        trackedCertificateService.deleteTrackedCertificate(name);
        return ResponseEntity.noContent().build();
    }
}
