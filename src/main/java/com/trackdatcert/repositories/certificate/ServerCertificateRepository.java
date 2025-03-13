package com.trackdatcert.repositories.certificate;

import com.trackdatcert.config.SSLVerificationConfig;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Objects;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class ServerCertificateRepository {

    private final SSLContext sslContext;

    public ServerCertificateRepository() throws NoSuchAlgorithmException, KeyManagementException {
        sslContext = SSLVerificationConfig.getAcceptAllCertificatesSslContext();
    }

    public X509Certificate getCertificate(String host, int port) {
        Objects.requireNonNull(host, "Host cannot be null or empty");
        if (port < 1) {
            throw new IllegalArgumentException("Port cannot be less than 1");
        }
        try {
            return getCertificateFromServer(host, port);
        } catch (NoSuchAlgorithmException | KeyManagementException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private X509Certificate getCertificateFromServer(String host, int port)
        throws NoSuchAlgorithmException, KeyManagementException, IOException {
        SSLSocketFactory factory = sslContext.getSocketFactory();
        SSLSession session;
        try (SSLSocket socket = (SSLSocket) factory.createSocket(host, port)) {
            socket.startHandshake();
            session = socket.getSession();
            // Get the server certificate chain
            Certificate[] certificates = session.getPeerCertificates();
            if (certificates.length < 1) {
                throw new RuntimeException("No certificates found");
            }
            return (X509Certificate) certificates[0];
        }
    }
}
