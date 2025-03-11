package com.trackdatcert.config;

import jakarta.annotation.PostConstruct;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import lombok.experimental.UtilityClass;

@UtilityClass
public class SSLVerificationConfig {

    public TrustManager[] getTrustManagerThatAcceptsAllCertificates() {
        return new TrustManager[] { new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }

            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }
        } };
    }

    @PostConstruct
    public void disableSSLVerification() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sc = SSLContext.getInstance("TLS");
        sc.init(null, getTrustManagerThatAcceptsAllCertificates(),
            new java.security.SecureRandom());
        // Set the default SSLSocketFactory to use our custom SSLContext
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        // Set the default HostnameVerifier to always return true (bypassing hostname verification)
        HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);
    }

}
