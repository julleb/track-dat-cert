package com.trackdatcert.repositories.certificate.model;

import com.trackdatcert.utils.ObjectUtils;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CertificateEntityDTO {
    private long trackedCertificateEntityId;
    private long id;
    private long validFrom;
    private long validTo;
    private String commonName;
    private String issuer;

    @Builder(builderMethodName = "createBuilder")
    public CertificateEntityDTO(long validFrom, long validTo, String commonName, String issuer) {
        if(validFrom < 1) throw new IllegalArgumentException("validFrom must be larger than 0 ");
        if(validTo < 1) throw new IllegalArgumentException("validTo must be larger than 0");
        ObjectUtils.requireNonEmpty(commonName, "commonName cannot be empty");
        ObjectUtils.requireNonEmpty(issuer, "issuer cannot be empty");
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.commonName = commonName;
        this.issuer = issuer;
    }

    @Builder(builderMethodName = "readBuilder")
    public CertificateEntityDTO(long trackedCertificateEntityId, long id, long validFrom, long validTo, String commonName, String issuer) {
        if(validFrom < 1) throw new IllegalArgumentException("validFrom must be larger than 0");
        if(validTo < 1) throw new IllegalArgumentException("validTo must be larger than 0");
        ObjectUtils.requireNonEmpty(commonName, "commonName cannot be empty");
        ObjectUtils.requireNonEmpty(issuer, "issuer cannot be empty");
        if(trackedCertificateEntityId < 1) throw new IllegalArgumentException("trackedCertificateEntityId must be larger than 0");
        if(id < 1) throw new IllegalArgumentException("id must be larger than 0");

        this.trackedCertificateEntityId = trackedCertificateEntityId;
        this.id = id;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.commonName = commonName;
        this.issuer = issuer;
    }
}
