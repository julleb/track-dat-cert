package com.trackdatcert.repositories.certificate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.trackdatcert.repositories.certificate.model.CertificateEntityDTO;
import com.trackdatcert.repositories.certificate.model.TrackedCertificateEntityDTO;
import java.util.List;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional //rollback after each test
class TestTrackedCertificateRepository {

    @Autowired
    private DataSource dataSource;
    private TrackedCertificateRepository trackedCertificateRepository;

    private final String userId = "userAbc";

    private CertificateEntityDTO.CertificateEntityDTOBuilder certificateEntityDTOBuilder =
        CertificateEntityDTO.builder()
            .trackedCertificateEntityId(1)
            .id(1)
            .validFrom(1)
            .validTo(2)
            .commonName("myCommonName")
            .issuer("myIssuer");

    private TrackedCertificateEntityDTO.TrackedCertificateEntityDTOBuilder trackedCertificateEntityDTOBuilder =
        TrackedCertificateEntityDTO.createBuilder()
            .name("myName")
            .description("myDescription")
            .url("myUrl")
            .certificateType(1)
            .createdByUserId(userId);

    @BeforeEach
    void setup() {
        NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        trackedCertificateRepository = new TrackedCertificateRepository(jdbcTemplate);
    }

    @Test
    void testSaveTrackedCertificate() {
        var certDto = certificateEntityDTOBuilder
            .build();
        var dto = trackedCertificateEntityDTOBuilder
            .certificates(List.of(certDto))
            .build();

        trackedCertificateRepository.saveTrackedCertificate(dto);
        var trackedCert = trackedCertificateRepository.getTrackedCertificate(dto.getName());
        assertEquals(1, trackedCert.getId());
        assertEquals(dto.getName(), trackedCert.getName());
        assertEquals(dto.getDescription(), trackedCert.getDescription());
        assertEquals(dto.getUrl(), trackedCert.getUrl());
        assertEquals(dto.getCertificateType(), trackedCert.getCertificateType());
        assertEquals(dto.getCreatedByUserId(), trackedCert.getCreatedByUserId());

        assertEquals(1, trackedCert.getCertificates().size());
        var storedCertDto = trackedCert.getCertificates().getFirst();
        assertEquals(certDto.getCommonName(), storedCertDto.getCommonName());
        assertEquals(certDto.getIssuer(), storedCertDto.getIssuer());
    }

    @Test
    void testGetTrackedCertificate_whenNotExists() {
        assertThrows(EmptyResultDataAccessException.class, () -> trackedCertificateRepository.getTrackedCertificate("nonExisting"));
    }

    @Test
    void testGetTrackedCertificateCreatedByUserId() {
        var certDto = certificateEntityDTOBuilder
            .build();
        var dto = trackedCertificateEntityDTOBuilder
            .certificates(List.of(certDto))
            .build();

        trackedCertificateRepository.saveTrackedCertificate(dto);

        certDto = certificateEntityDTOBuilder
            .build();
        dto = trackedCertificateEntityDTOBuilder
            .certificates(List.of(certDto))
            .name("abc")
            .build();
        trackedCertificateRepository.saveTrackedCertificate(dto);

        var result = trackedCertificateRepository.getTrackedCertificatesCreatedByUserId(userId);
        assertEquals(2, result.size());
    }

    @Test
    void testGetTrackedCertificateCreatedByUserId_whenNoCerts() {
        var result = trackedCertificateRepository.getTrackedCertificatesCreatedByUserId(userId);
        assertTrue(result.isEmpty());
    }
}
