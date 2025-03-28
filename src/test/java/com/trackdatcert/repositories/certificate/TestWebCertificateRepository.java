package com.trackdatcert.repositories.certificate;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.trackdatcert.config.SSLVerificationConfig;
import java.io.IOException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestWebCertificateRepository {

    private WebCertificateRepository webCertificateRepository;
    private MockHttpsServer mockWebServer;
    private String url;

    @BeforeEach
    void setup() throws Exception {
        SSLVerificationConfig.disableSSLVerification();
        mockWebServer = new MockHttpsServer();
        mockWebServer.start();
        url = mockWebServer.getUrl();
        webCertificateRepository = new WebCertificateRepository();
    }

    @AfterEach
    void after() throws IOException {
        mockWebServer.stop();
    }

    @Test
    void testGetCertificate() {
        var cert = webCertificateRepository.getCertificate(url);
        assertNotNull(cert);
    }

    @Test
    void testGetCertificate_whenUrlIsNotHttps() {
        assertThrows(IllegalArgumentException.class,
            () -> webCertificateRepository.getCertificate("http://localhost.se"));
    }
}
