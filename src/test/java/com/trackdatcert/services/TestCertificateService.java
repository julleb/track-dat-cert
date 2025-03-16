package com.trackdatcert.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.trackdatcert.KeystoreTestData;
import com.trackdatcert.repositories.certificate.SAMLCertificateRepository;
import com.trackdatcert.repositories.certificate.ServerCertificateRepository;
import com.trackdatcert.repositories.certificate.WebCertificateRepository;
import com.trackdatcert.services.model.CertificateDetails;
import com.trackdatcert.services.model.CertificateUsageType;
import java.security.cert.X509Certificate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class TestCertificateService {

    private CertificateService certificateService;
    private WebCertificateRepository webCertificateRepository;
    private SAMLCertificateRepository samlCertificateRepository;
    private ServerCertificateRepository serverCertificateRepository;
    private String url = "https://saml.com";
    private X509Certificate certificate;

    @BeforeEach
    void setup() throws Exception {
        certificate = KeystoreTestData.getAsX509Certificate();
        webCertificateRepository = Mockito.mock(WebCertificateRepository.class);
        samlCertificateRepository = Mockito.mock(SAMLCertificateRepository.class);
        serverCertificateRepository = Mockito.mock(ServerCertificateRepository.class);

        certificateService =
            new CertificateService(webCertificateRepository, samlCertificateRepository,
                serverCertificateRepository);
    }

    @Test
    void testGetCertificateDetails_whenSAML() {
        Mockito.when(samlCertificateRepository.getCertificate(url))
            .thenReturn(certificate);
        CertificateDetails certDetails =
            certificateService.getCertificateDetails(url, CertificateUsageType.SAML);

        assertEquals(KeystoreTestData.SERIAL_NUMBER, certDetails.getSerialNumber());
        assertEquals(KeystoreTestData.ISSUER, certDetails.getIssuer());
        assertEquals(KeystoreTestData.COMMON_NAME, certDetails.getCommonName());
        assertEquals(KeystoreTestData.VALID_FROM, certDetails.getValidFrom());
        assertEquals(KeystoreTestData.VALID_TO, certDetails.getValidTo());

    }

    @Test
    void testGetCertificateDetails_whenWeb() {
        Mockito.when(webCertificateRepository.getCertificate(url))
            .thenReturn(certificate);
        CertificateDetails certDetails =
            certificateService.getCertificateDetails(url, CertificateUsageType.WEB);

        assertEquals(KeystoreTestData.SERIAL_NUMBER, certDetails.getSerialNumber());
        assertEquals(KeystoreTestData.ISSUER, certDetails.getIssuer());
        assertEquals(KeystoreTestData.COMMON_NAME, certDetails.getCommonName());
        assertEquals(KeystoreTestData.VALID_FROM, certDetails.getValidFrom());
        assertEquals(KeystoreTestData.VALID_TO, certDetails.getValidTo());

    }

    @Test
    void testGetCertificateDetails_whenServer() {
        String url = "myHost:443";
        Mockito.when(serverCertificateRepository.getCertificate("myHost", 443))
            .thenReturn(certificate);
        CertificateDetails certDetails =
            certificateService.getCertificateDetails(url, CertificateUsageType.SERVER);

        assertEquals(KeystoreTestData.SERIAL_NUMBER, certDetails.getSerialNumber());
        assertEquals(KeystoreTestData.ISSUER, certDetails.getIssuer());
        assertEquals(KeystoreTestData.COMMON_NAME, certDetails.getCommonName());
        assertEquals(KeystoreTestData.VALID_FROM, certDetails.getValidFrom());
        assertEquals(KeystoreTestData.VALID_TO, certDetails.getValidTo());
    }

}
