package com.trackdatcert.repositories.certificate;

import com.trackdatcert.utils.UrlUtils;
import java.io.IOException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
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
        } catch (IOException | NoSuchAlgorithmException | KeyManagementException e) {
            throw new RuntimeException(e);
        }
    }

    private X509Certificate getCertificateFromUrl(String url)
        throws IOException, NoSuchAlgorithmException, KeyManagementException {
        URL urlObj = UrlUtils.getUrl(url);

        HttpsURLConnection connection = (HttpsURLConnection) urlObj.openConnection();
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
