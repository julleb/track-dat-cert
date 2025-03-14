package com.trackdatcert.repositories.certificate;

import com.trackdatcert.repositories.certificate.model.CertificateEntityDTO;
import com.trackdatcert.repositories.certificate.model.TrackedCertificateEntityDTO;
import com.trackdatcert.utils.ObjectUtils;
import com.trackdatcert.utils.SQLUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
@AllArgsConstructor
public class TrackedCertificateRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public void saveTrackedCertificate(TrackedCertificateEntityDTO trackedCertificateEntityDTO) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", trackedCertificateEntityDTO.getName())
            .addValue("description", trackedCertificateEntityDTO.getDescription())
            .addValue("url", trackedCertificateEntityDTO.getUrl())
            .addValue("cert_type", trackedCertificateEntityDTO.getCertificateType());
        int updated = jdbcTemplate.update(
            "INSERT INTO tracked_certificates (name, description, url, cert_type)" +
                " VALUES (:name, :description, :url, :cert_type)", params);
        if (updated != 1) {
            throw new IncorrectResultSizeDataAccessException("Failed to insert certificate", 1,
                updated);
        }

        params = new MapSqlParameterSource();
        params.addValue("name", trackedCertificateEntityDTO.getName());
        Long trackedCertId = jdbcTemplate.queryForObject(
            "SELECT tracked_certificates_id FROM tracked_certificates WHERE name = :name", params,
            Long.class);
        Objects.requireNonNull(trackedCertId, "Failed to get tracked certificate id");
        trackedCertificateEntityDTO.setId(trackedCertId);

        for (var cert : trackedCertificateEntityDTO.getCertificates()) {
            params = new MapSqlParameterSource();
            params.addValue("tracked_certificates_id", trackedCertificateEntityDTO.getId())
                .addValue("valid_from", SQLUtils.getSqlDate(cert.getValidFrom()))
                .addValue("valid_to", SQLUtils.getSqlDate(cert.getValidTo()))
                .addValue("commonName", cert.getCommonName())
                .addValue("issuer", cert.getIssuer());
            updated = jdbcTemplate.update(
                "INSERT INTO certificates (tracked_certificates_id, valid_from, " +
                    "valid_to, common_name, issuer) " +
                    "VALUES (:tracked_certificates_id, :valid_from, :valid_to, :commonName, :issuer)",
                params);
            if (updated != 1) {
                throw new IncorrectResultSizeDataAccessException("Failed to insert certificate", 1,
                    updated);
            }
        }
    }

    public TrackedCertificateEntityDTO getTrackedCertificate(String name) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", name);
        var trackedCertEntity =
            jdbcTemplate.query("SELECT * FROM tracked_certificates WHERE name = :name", params,
                rs -> {
                    if (rs.next()) {
                        return TrackedCertificateEntityDTO.builder()
                            .id(rs.getLong("tracked_certificates_id"))
                            .name(rs.getString("name"))
                            .description(rs.getString("description"))
                            .url(rs.getString("url"))
                            .certificateType(rs.getInt("cert_type"))
                            .build();
                    }
                    throw new EmptyResultDataAccessException(
                        "No certificate found with name: " + name, 1);
                });

        params = new MapSqlParameterSource();
        params.addValue("tracked_certificates_id", trackedCertEntity.getId());
        var certEntityList = jdbcTemplate.query(
            "SELECT * FROM certificates WHERE tracked_certificates_id = :tracked_certificates_id",
            params, rs -> {
                List<CertificateEntityDTO> certList = new ArrayList<>();
                if (rs.next()) {
                    var certEntity = CertificateEntityDTO.builder()
                        .trackedCertificateEntityId(rs.getLong("tracked_certificates_id"))
                        .id(rs.getLong("certificate_id"))
                        .validFrom(SQLUtils.getEpoch(rs.getDate("valid_from")))
                        .validTo(SQLUtils.getEpoch(rs.getDate("valid_to")))
                        .commonName(rs.getString("common_name"))
                        .issuer(rs.getString("issuer"))
                        .build();
                    certList.add(certEntity);
                }
                if (certList.isEmpty()) {
                    throw new EmptyResultDataAccessException(
                        "No certificates found for tracked certificate with id: " +
                            trackedCertEntity.getId(), 1);
                }
                return certList;
            });
        trackedCertEntity.setCertificates(certEntityList);
        return trackedCertEntity;
    }

}
