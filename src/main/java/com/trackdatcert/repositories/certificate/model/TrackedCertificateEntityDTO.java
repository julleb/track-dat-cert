package com.trackdatcert.repositories.certificate.model;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class TrackedCertificateEntityDTO {

    @Setter
    private long id;
    private String name;
    private String description;
    private String url;
    private int certificateType;
    @Setter
    private List<CertificateEntityDTO> certificates;
}
