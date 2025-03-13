package com.trackdatcert.repositories;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.X509Certificate;
import lombok.experimental.UtilityClass;

@UtilityClass
public class KeystoreTestData {

    public final String KEY_STORE_NAME = "test.jks";
    public final String KEY_STORE_PASSWORD = "";
    public final String KEY_STORE_ALIAS = "trackdatcert";

    public final String SERIAL_NUMBER = "645741599602804665051047058672448505493179914694";
    public final String ISSUER = "CN=TrackDatCert,OU=TrackDatCertOrgUnit,O=TrackDatCertOrgName,L=SwedenLocal,ST=Luleå,C=SE";
    public final String COMMON_NAME = ISSUER;
    public final Long VALID_FROM = 1741719132000L;
    public final Long VALID_TO = 1773255132000L;

    public X509Certificate getAsX509Certificate() throws Exception {
        var ks = getKeyStore();
        return (X509Certificate) ks.getCertificate(KEY_STORE_ALIAS);
    }

    public KeyStore getKeyStore() throws Exception {
        var inputStream = KeystoreTestData.class.getClassLoader()
            .getResourceAsStream(KEY_STORE_NAME);
        return loadKeyStore(inputStream, KEY_STORE_PASSWORD);
    }

    public KeyStore loadKeyStore(InputStream is, String keystorePassword) throws Exception {
        KeyStore keyStore = KeyStore.getInstance("JKS");
        keyStore.load(is, keystorePassword.toCharArray());
        return keyStore;
    }
}
