package com.trackdatcert.services.certificate.model;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TrackedCertificate {

    private List<CertificateDetails> certificateDetails;
    private String name;
    private String description;
    private String url;
    private CertificateUsageType usageType;

}
