package com.trackdatcert.services.certificate;

import com.trackdatcert.repositories.certificate.TrackedCertificateRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
@AllArgsConstructor
public class TrackedCertificateDeleter {

    private final TrackedCertificateRepository trackedCertificateRepository;

    @Transactional
    public void deleteTrackedCertificate(String name, String userId) {
        log.info("Deleting tracked certificate with name: {}", name);
        trackedCertificateRepository.deleteTrackedCertificate(name);
    }
}
