package com.trackdatcert.services.certificate.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class CertificateDetails {
    private String serialNumber;
    private String issuer;
    private String commonName;
    private Long validFrom;
    private Long validTo;

}
