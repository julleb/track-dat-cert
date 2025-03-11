package com.trackdatcert.repositories.certificate;

import com.trackdatcert.config.WebClientConfig;
import java.security.cert.X509Certificate;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Repository
@Slf4j
@AllArgsConstructor
public class SAMLCertificateRepository {

    public X509Certificate getCertificate(String url) {
        Objects.requireNonNull(url, "url cannot be null");
        var webClient = WebClientConfig.createWebClient(url);
        var responseEntity = webClient.get()
            .accept(MediaType.APPLICATION_XML)
            .retrieve()
            .toEntity(String.class)
            .map(respEntity -> {
                HttpHeaders headers = respEntity.getHeaders();
                String contentType = headers.getContentType() != null ? headers.getContentType()
                    .toString() : "";
                if (contentType.contains(MediaType.APPLICATION_XML_VALUE)) {
                    return respEntity;
                }
                throw new WebClientResponseException(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(),
                    "Expected XML content, but received: " + contentType, headers, null, null);
            })
            .block();
        if (responseEntity == null) {
            throw new RuntimeException("Failed to get response from url " + url);
        }
        return getCertificateFromMetadata(responseEntity);
    }

    private X509Certificate getCertificateFromMetadata(ResponseEntity<String> responseEntity) {
        System.out.println("Metadata " + responseEntity.getBody());
        return null;
    }
}
