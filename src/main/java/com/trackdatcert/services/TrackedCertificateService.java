package com.trackdatcert.services;

import com.trackdatcert.repositories.certificate.TrackedCertificateRepository;
import com.trackdatcert.repositories.certificate.model.CertificateEntityDTO;
import com.trackdatcert.repositories.certificate.model.TrackedCertificateEntityDTO;
import com.trackdatcert.services.model.CertificateDetails;
import com.trackdatcert.services.model.TrackedCertificate;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
public class TrackedCertificateService {
    private final TrackedCertificateRepository trackedCertificateRepository;

    public TrackedCertificate getTrackedCertificate(String name) {
        var trackedCertDto = trackedCertificateRepository.getTrackedCertificate(name);
        return convertToTrackedCertificate(trackedCertDto);
    }

    public List<TrackedCertificate> getTrackedCertificatesForUser(String userId) {
        return List.of();
    }

    @Transactional
    public void addTrackedCertificate(TrackedCertificate trackedCertificate, String userId) {
        List<CertificateEntityDTO> certificateEntityDTOList = new ArrayList<>();
        for (var certDetails : trackedCertificate.getCertificateDetails()) {
            var certEntityDto = CertificateEntityDTO.builder()
                .commonName(certDetails.getCommonName())
                .validTo(certDetails.getValidTo())
                .issuer(certDetails.getIssuer())
                .validFrom(certDetails.getValidFrom())
                .build();
            certificateEntityDTOList.add(certEntityDto);
        }

        var trackedEntityDTO = TrackedCertificateEntityDTO.builder()
            .description(trackedCertificate.getDescription())
            .name(trackedCertificate.getName())
            .url(trackedCertificate.getUrl())
            .certificates(certificateEntityDTOList)
            .createdByUserId(userId)
            .build();
        trackedCertificateRepository.saveTrackedCertificate(trackedEntityDTO);
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
