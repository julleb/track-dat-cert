package com.trackdatcert.repositories.certificate;

import com.trackdatcert.repositories.KeystoreTestData;
import java.io.IOException;
import java.security.KeyStore;
import java.security.SecureRandom;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import lombok.Getter;
import okhttp3.mockwebserver.MockWebServer;

class MockHttpsServer {

    private final MockWebServer mockWebServer;

    @Getter
    private String url;

    public MockHttpsServer() {
        mockWebServer = new MockWebServer();
    }

    public void start() throws Exception {
        mockWebServer.useHttps(getSSLContext().getSocketFactory(), false);
        mockWebServer.start();
        url = mockWebServer.url("/")
            .toString();
    }

    public void stop() throws IOException {
        mockWebServer.shutdown();
    }

    private SSLContext getSSLContext() throws Exception {
        var ks = KeystoreTestData.getKeyStore();
        return createSSLContext(ks, "");
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

}
