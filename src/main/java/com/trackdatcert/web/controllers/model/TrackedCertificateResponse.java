package com.trackdatcert.web.controllers.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class TrackedCertificateResponse {
    private List<CertificateDetailsResponse> certificateDetails;
    private String url;
    private String type;
    private String description;
    private String name;
}
