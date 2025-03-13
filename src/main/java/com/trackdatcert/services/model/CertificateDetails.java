package com.trackdatcert.services.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CertificateDetails {
    private String serialNumber;
    private String issuer;
    private String commonName;
    private Long validFrom;
    private Long validTo;

}
