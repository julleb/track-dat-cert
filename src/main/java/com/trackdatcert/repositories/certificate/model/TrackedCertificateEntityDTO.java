package com.trackdatcert.repositories.certificate.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TrackedCertificateEntityDTO {

    private long id;
    private String name;
    private String description;
    private String url;
    private int certificateType;
}
