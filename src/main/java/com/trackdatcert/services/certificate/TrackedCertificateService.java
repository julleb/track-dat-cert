package com.trackdatcert.services.certificate;

import com.trackdatcert.services.certificate.model.TrackedCertificate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class TrackedCertificateService {
    private final TrackedCertificateFetcher trackedCertificateFetcher;
    private final TrackedCertificateCreator trackedCertificateCreator;

    public TrackedCertificate getTrackedCertificate(String name) {
        return trackedCertificateFetcher.getTrackedCertificate(name);
    }

    public List<TrackedCertificate> getTrackedCertificatesForUser(String userId) {
        return trackedCertificateFetcher.getTrackedCertificatesForUser(userId);
    }

    public void addTrackedCertificate(TrackedCertificate trackedCertificate, String userId) {
        trackedCertificateCreator.addTrackedCertificate(trackedCertificate, userId);
    }



}
