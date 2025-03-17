package com.trackdatcert.services.certificate;

import com.trackdatcert.repositories.certificate.TrackedCertificateRepository;
import com.trackdatcert.repositories.certificate.model.TrackedCertificateEntityDTO;
import com.trackdatcert.services.certificate.model.CertificateDetails;
import com.trackdatcert.services.certificate.model.TrackedCertificate;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
class TrackedCertificateFetcher {

    private final TrackedCertificateRepository trackedCertificateRepository;

    public TrackedCertificate getTrackedCertificate(String name) {
        var trackedCertDto = trackedCertificateRepository.getTrackedCertificate(name);
        return convertToTrackedCertificate(trackedCertDto);
    }

    public List<TrackedCertificate> getTrackedCertificatesForUser(String userId) {
        List<TrackedCertificateEntityDTO> list = trackedCertificateRepository.getTrackedCertificatesCreatedByUserId(userId);
        List<TrackedCertificate> trackedCertificateList = new ArrayList<>();
        for(var trackedCertDto : list) {
            trackedCertificateList.add(convertToTrackedCertificate(trackedCertDto));
        }
        return trackedCertificateList;
    }

    private TrackedCertificate convertToTrackedCertificate(
        TrackedCertificateEntityDTO trackedCertDto) {
        List<CertificateDetails> certificateDetailsList = new ArrayList<>();
        for (var certDto : trackedCertDto.getCertificates()) {
            certificateDetailsList.add(CertificateDetails.builder()
                .issuer(certDto.getIssuer())
                .validTo(certDto.getValidTo())
                .validFrom(certDto.getValidFrom())
                .build());
        }
        return TrackedCertificate.builder()
            .url(trackedCertDto.getUrl())
            .description(trackedCertDto.getDescription())
            .name(trackedCertDto.getName())
            .certificateDetails(certificateDetailsList)
            .build();
    }

}
