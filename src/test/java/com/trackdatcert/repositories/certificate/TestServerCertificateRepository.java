package com.trackdatcert.repositories.certificate;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.trackdatcert.config.SSLVerificationConfig;
import com.trackdatcert.utils.UrlUtils;
import java.io.IOException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestServerCertificateRepository {

    private MockHttpsServer mockWebServer;
    private String url;
    private ServerCertificateRepository serverCertificateRepository;

    @BeforeEach
    void setup() throws Exception {
        mockWebServer = new MockHttpsServer();
        mockWebServer.start();
        url = mockWebServer.getUrl();
        serverCertificateRepository = new ServerCertificateRepository();
    }

    @AfterEach
    void after() throws IOException {
        mockWebServer.stop();
    }

    @Test
    void testGetCertificate() {
        String host = UrlUtils.getHostFromUrl(url);
        int port = UrlUtils.getPortFromUrl(url);
        var cert = serverCertificateRepository.getCertificate(host, port);
        assertNotNull(cert);
    }

}
