package com.trackdatcert.repositories.certificate;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.trackdatcert.config.SSLVerificationConfig;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestWebCertificateRepository {

    private WebCertificateRepository webCertificateRepository;
    private MockWebServer mockWebServer;
    private static final String KEY_STORE_NAME = "test.jks";
    private String url;

    @BeforeEach
    void setup() throws Exception {
        SSLVerificationConfig.disableSSLVerification();
        mockWebServer = new MockWebServer();
        mockWebServer.useHttps(getSSLContext().getSocketFactory(), false);
        mockWebServer.start();
        url = mockWebServer.url("/")
            .toString();
        webCertificateRepository = new WebCertificateRepository();
    }

    private SSLContext getSSLContext() throws Exception {
        var inputStream = TestWebCertificateRepository.class.getClassLoader()
            .getResourceAsStream(KEY_STORE_NAME);
        KeyStore ks = loadKeyStore(inputStream, "");
        return createSSLContext(ks, "");
    }

    public KeyStore loadKeyStore(InputStream is, String keystorePassword) throws Exception {
        KeyStore keyStore = KeyStore.getInstance("JKS");
        keyStore.load(is, keystorePassword.toCharArray());
        return keyStore;
    }

    public SSLContext createSSLContext(KeyStore keyStore, String keystorePassword)
        throws Exception {
        KeyManagerFactory keyManagerFactory =
            KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStore, keystorePassword.toCharArray());

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(keyManagerFactory.getKeyManagers(), null, new SecureRandom());

        return sslContext;
    }

    @AfterEach
    void after() throws IOException {
        mockWebServer.shutdown();
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
