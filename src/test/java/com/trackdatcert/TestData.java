package com.trackdatcert;

import com.trackdatcert.services.certificate.model.CertificateDetails;
import com.trackdatcert.services.certificate.model.CertificateUsageType;
import com.trackdatcert.services.certificate.model.TrackedCertificate;
import java.util.List;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TestData {

    public static final String USER_ID = "abc";

    public TrackedCertificate.TrackedCertificateBuilder getTrackedCertificateBuilder() {
        return TrackedCertificate.builder()
            .name("test")
            .description("test")
            .url("https://saml.se")
            .usageType(CertificateUsageType.SAML)
            .certificateDetails(List.of(getCertificateDetailsBuilder().build()))
            .url("test");
    }

    public CertificateDetails.CertificateDetailsBuilder getCertificateDetailsBuilder() {
        return CertificateDetails.builder()
            .serialNumber("serialNumber")
            .issuer("issuerTest")
            .commonName("commonTest")
            .validFrom(System.currentTimeMillis())
            .validTo(System.currentTimeMillis());
    }
}
