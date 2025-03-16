package com.trackdatcert.repositories.certificate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        var certDto = CertificateEntityDTO.builder()
            .trackedCertificateEntityId(1)
            .id(1)
            .validFrom(1)
            .validTo(2)
            .commonName("myCommonName")
            .issuer("myIssuer")
            .build();
        var dto = TrackedCertificateEntityDTO.createBuilder()
            .name("myName")
            .description("myDescription")
            .url("myUrl")
            .certificateType(1)
            .certificates(List.of(certDto))
            .createdByUserId("userAbc")
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
        var storedCertDto = trackedCert.getCertificates().get(0);
        assertEquals(certDto.getCommonName(), storedCertDto.getCommonName());
        assertEquals(certDto.getIssuer(), storedCertDto.getIssuer());
    }

    @Test
    void testGetTrackedCertificate_whenNotExists() {
        assertThrows(EmptyResultDataAccessException.class, () -> trackedCertificateRepository.getTrackedCertificate("nonExisting"));
    }
}
