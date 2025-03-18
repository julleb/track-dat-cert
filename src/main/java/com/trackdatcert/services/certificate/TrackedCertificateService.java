package com.trackdatcert.services.certificate;

import com.trackdatcert.services.authentication.AuthenticationService;
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
    private final AuthenticationService authenticationService;


    public List<TrackedCertificate> getTrackedCertificatesForCurrentUser() {
        String userId = authenticationService.getCurrentUser();
        return trackedCertificateFetcher.getTrackedCertificatesForUser(userId);
    }

    public void addTrackedCertificate(TrackedCertificate trackedCertificate) {
        String userId = authenticationService.getCurrentUser();
        trackedCertificateCreator.addTrackedCertificate(trackedCertificate, userId);
    }



}
