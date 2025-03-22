package com.trackdatcert.web.controllers.model;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CertificateDetailsResponse {
    private String serialNumber;
    private String issuer;
    private String commonName;
    private LocalDate validFrom;
    private LocalDate validTo;
}
