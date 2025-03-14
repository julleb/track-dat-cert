package com.trackdatcert.repositories.certificate.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class CertificateEntityDTO {
    private long trackedCertificateEntityId;
    private long id;
    private long validFrom;
    private long validTo;
    private String commonName;
    private String issuer;
}
