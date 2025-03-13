package com.trackdatcert.repositories.certificate;

import com.trackdatcert.utils.UrlUtils;
import java.io.IOException;
import java.net.URL;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import javax.net.ssl.HttpsURLConnection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class WebCertificateRepository {

    public X509Certificate getCertificate(String url) {
        try {
            return getCertificateFromUrl(url);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private X509Certificate getCertificateFromUrl(String url) throws IOException {
        URL urlObj = UrlUtils.getHttpsUrl(url);
        HttpsURLConnection connection = (HttpsURLConnection) urlObj.openConnection();
        try {
            return getCertificate(connection);
        } finally {
            connection.disconnect();
        }
    }

    private X509Certificate getCertificate(HttpsURLConnection connection) throws IOException {
        connection.connect();
        // TODO return the chain as well?
        Certificate[] certs = connection.getServerCertificates();
        if (certs != null && certs.length > 0) {
            return (X509Certificate) certs[0];
        } else {
            throw new RuntimeException("No certificate found");
        }
    }

}
