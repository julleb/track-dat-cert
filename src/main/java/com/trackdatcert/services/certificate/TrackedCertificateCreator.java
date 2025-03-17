package com.trackdatcert.services.certificate;

import com.trackdatcert.repositories.certificate.TrackedCertificateRepository;
import com.trackdatcert.repositories.certificate.model.CertificateEntityDTO;
import com.trackdatcert.repositories.certificate.model.TrackedCertificateEntityDTO;
import com.trackdatcert.services.certificate.model.TrackedCertificate;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
@AllArgsConstructor
class TrackedCertificateCreator {

    private final TrackedCertificateRepository trackedCertificateRepository;

    @Transactional(rollbackFor = Exception.class)
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

        var trackedEntityDTO = TrackedCertificateEntityDTO.createBuilder()
            .description(trackedCertificate.getDescription())
            .name(trackedCertificate.getName())
            .url(trackedCertificate.getUrl())
            .certificates(certificateEntityDTOList)
            .createdByUserId(userId)
            .build();
        trackedCertificateRepository.saveTrackedCertificate(trackedEntityDTO);
    }
}
