package com.trackdatcert.repositories.certificate;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import okhttp3.Headers;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClientResponseException;

class TestSAMLCertificateRepository {

    private SAMLCertificateRepository samlCertificateRepository;
    private MockWebServer mockWebServer;
    private String url;
    private static final String METADATA_FILE_NAME = "saml-metadata.xml";

    @BeforeEach
    void setup() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        url = mockWebServer.url("/")
            .toString();
        samlCertificateRepository = new SAMLCertificateRepository();
    }

    @Test
    void testGetCertificate() throws IOException {
        var is = TestSAMLCertificateRepository.class.getClassLoader().getResourceAsStream(METADATA_FILE_NAME);
        var metadata = new String(is.readAllBytes());
        mockWebServer.enqueue(new MockResponse()
            .setBody(metadata)
            .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_XML_VALUE)
            .setResponseCode(200));
        var cert = samlCertificateRepository.getCertificate(url);
        assertNotNull(cert);
    }

    @Test
    void testGetCertificate_whenBadRequest() {
        String response = "abc";
        mockWebServer.enqueue(new MockResponse()
            .setBody(response)
            .setResponseCode(400));
        assertThrows(WebClientResponseException.class, () -> samlCertificateRepository.getCertificate(url));
    }

    @Test
    void testGetCertificate_whenNoApplicationXmlContentHeader() {
        String response = "abc";
        mockWebServer.enqueue(new MockResponse()
            .setBody(response)
            .setResponseCode(200));
        assertThrows(WebClientResponseException.class, () -> samlCertificateRepository.getCertificate(url));
    }

    @Test
    void testGetCertificate_whenHttp500() {
        String response = "error";
        mockWebServer.enqueue(new MockResponse()
            .setBody(response)
            .setResponseCode(500));
        assertThrows(WebClientResponseException.class, () -> samlCertificateRepository.getCertificate(url));
    }
}
