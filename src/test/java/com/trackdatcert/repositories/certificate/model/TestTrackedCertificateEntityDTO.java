package com.trackdatcert.repositories.certificate.model;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestTrackedCertificateEntityDTO {

    private TrackedCertificateEntityDTO.TrackedCertificateEntityDTOBuilder builder;

    @BeforeEach
    void setup() {
        builder = TrackedCertificateEntityDTO.createBuilder()
            .id(1)
            .name("name")
            .description("")
            .url("url")
            .certificateType(1)
            .createdByUserId("userId")
            .certificates(List.of(new CertificateEntityDTO.CertificateEntityDTOBuilder().build()));
    }

    @Test
    void testCreateBuilder() {
        builder.build();
    }

    @Test
    void testCreateBuilder_whenNameIsEmpty() {
        builder.name("");
        assertThrows(IllegalArgumentException.class, () -> builder.build());
    }

    @Test
    void testCreateBuilder_whenDescriptionIsNull() {
        builder.description(null);
        assertThrows(IllegalArgumentException.class, () -> builder.build());
    }

    @Test
    void testCreateBuilder_whenUrlIsEmpty() {
        builder.url("");
        assertThrows(IllegalArgumentException.class, () -> builder.build());
    }

    @Test
    void testCreateBuilder_whenCertificatesIsEmpty() {
        builder.certificates(List.of());
        assertThrows(IllegalArgumentException.class, () -> builder.build());
    }

    @Test
    void testCreateBuilder_whenCreatedByUserIdIsEmpty() {
        builder.createdByUserId("");
        assertThrows(IllegalArgumentException.class, () -> builder.build());
    }
}
