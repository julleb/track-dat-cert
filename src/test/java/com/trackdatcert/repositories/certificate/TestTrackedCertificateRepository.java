package com.trackdatcert.repositories.certificate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.trackdatcert.repositories.certificate.model.TrackedCertificateEntityDTO;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@SpringBootTest
class TestTrackedCertificateRepository {

    @Autowired
    private DataSource dataSource;
    private TrackedCertificateRepository trackedCertificateRepository;

    @BeforeEach
    void setup() {
        NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        trackedCertificateRepository = new TrackedCertificateRepository(jdbcTemplate);
    }

    @Test
    void testSaveTrackedCertificate() {
        var dto = TrackedCertificateEntityDTO.builder()
            .name("myName")
            .description("myDescription")
            .url("myUrl")
            .certificateType(1)
            .build();

        trackedCertificateRepository.saveTrackedCertificate(dto);
        var trackedCert = trackedCertificateRepository.getTrackedCertificate(dto.getName());
        assertEquals(1, trackedCert.getId());
        assertEquals(dto.getName(), trackedCert.getName());
        assertEquals(dto.getDescription(), trackedCert.getDescription());
        assertEquals(dto.getUrl(), trackedCert.getUrl());
        assertEquals(dto.getCertificateType(), trackedCert.getCertificateType());
    }

    @Test
    void testGetTrackedCertificate_whenNotExists() {
        assertThrows(EmptyResultDataAccessException.class, () -> trackedCertificateRepository.getTrackedCertificate("nonExisting"));
    }
}
