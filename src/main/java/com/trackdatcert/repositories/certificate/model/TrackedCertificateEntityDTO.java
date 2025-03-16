package com.trackdatcert.repositories.certificate.model;

import com.trackdatcert.utils.ObjectUtils;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
public class TrackedCertificateEntityDTO {

    @Setter
    private long id;
    private String name;
    private String description;
    private String url;
    private int certificateType;
    private List<CertificateEntityDTO> certificates;
    private String createdByUserId;

    @Builder(builderMethodName = "createBuilder")
    public TrackedCertificateEntityDTO(long id, String name, String description, String url,
        int certificateType, List<CertificateEntityDTO> certificates, String createdByUserId) {

        ObjectUtils.requireNonEmpty(name, "name cannot be empty");
        ObjectUtils.requireNonNull(description, "description cannot be null");
        ObjectUtils.requireNonEmpty(url, "url cannot be empty");
        ObjectUtils.requireNonEmpty(certificateType, "certificateType cannot be empty");
        ObjectUtils.requireNonEmpty(certificates, "certificates cannot be empty");
        ObjectUtils.requireNonEmpty(createdByUserId, "createdByUserId cannot be empty");

        this.id = id;
        this.name = name;
        this.description = description;
        this.url = url;
        this.certificateType = certificateType;
        this.certificates = certificates;
        this.createdByUserId = createdByUserId;
    }

}
