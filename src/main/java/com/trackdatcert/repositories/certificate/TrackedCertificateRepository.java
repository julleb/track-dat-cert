package com.trackdatcert.repositories.certificate;

import com.trackdatcert.repositories.certificate.model.TrackedCertificateEntityDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
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
            throw new IncorrectResultSizeDataAccessException("Failed to insert certificate", 1, updated);
        }
    }

    public TrackedCertificateEntityDTO getTrackedCertificate(String name) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", name);
        return jdbcTemplate.query("SELECT * FROM tracked_certificates WHERE name = :name", params,
            rs -> {
                if (rs.next()) {
                    return TrackedCertificateEntityDTO.builder()
                        .id(rs.getLong("id"))
                        .name(rs.getString("name"))
                        .description(rs.getString("description"))
                        .url(rs.getString("url"))
                        .certificateType(rs.getInt("cert_type"))
                        .build();
                }
                throw new EmptyResultDataAccessException("No certificate found with name: " + name, 1);
            });
    }

}
