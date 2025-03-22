package com.trackdatcert.web.controllers;

import com.trackdatcert.services.certificate.model.CertificateDetails;
import com.trackdatcert.services.certificate.model.TrackedCertificate;
import com.trackdatcert.utils.TimeUtils;
import com.trackdatcert.web.controllers.model.CertificateDetailsResponse;
import com.trackdatcert.web.controllers.model.TrackedCertificateResponse;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.experimental.UtilityClass;

@UtilityClass
class Utils {

    public CertificateDetailsResponse convertToResponse(CertificateDetails details) {

        LocalDate validTo = TimeUtils.getDate(details.getValidTo());
        LocalDate validFrom = TimeUtils.getDate(details.getValidFrom());

        return CertificateDetailsResponse.builder()
            .serialNumber(details.getSerialNumber())
            .issuer(details.getIssuer())
            .commonName(details.getCommonName())
            .validFrom(validFrom)
            .validTo(validTo)
            .build();
    }

    public List<TrackedCertificateResponse> convertToResponse(List<TrackedCertificate> trackedCertificates) {
        return trackedCertificates.stream()
            .map(Utils::convertToResponse)
            .collect(Collectors.toList());
    }

    public TrackedCertificateResponse convertToResponse(TrackedCertificate trackedCertificate) {
        List<CertificateDetailsResponse> certificateDetails = trackedCertificate.getCertificateDetails()
            .stream()
            .map(Utils::convertToResponse)
            .collect(Collectors.toList());

        return TrackedCertificateResponse.builder()
            .certificateDetails(certificateDetails)
            .url(trackedCertificate.getUrl())
            .type(trackedCertificate.getUsageType().name())
            .description(trackedCertificate.getDescription())
            .name(trackedCertificate.getName())
            .build();
    }

}
