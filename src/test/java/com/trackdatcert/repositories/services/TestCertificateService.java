package com.trackdatcert.repositories.services;

import com.trackdatcert.repositories.certificate.SAMLCertificateRepository;
import com.trackdatcert.repositories.certificate.ServerCertificateRepository;
import com.trackdatcert.repositories.certificate.WebCertificateRepository;
import com.trackdatcert.services.CertificateService;
import com.trackdatcert.services.model.CertificateDetails;
import com.trackdatcert.services.model.CertificateUsageType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class TestCertificateService {

    private CertificateService certificateService;
    private WebCertificateRepository webCertificateRepository;
    private SAMLCertificateRepository samlCertificateRepository;
    private ServerCertificateRepository serverCertificateRepository;
    private String url = "https://saml.com";

    @BeforeEach
    void setup() {

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
            .thenReturn(null);
        CertificateDetails certDetails =
            certificateService.getCertificateDetails(url, CertificateUsageType.SAML);

    }

    @Test
    void testGetCertificateDetails_whenWeb() {
        Mockito.when(webCertificateRepository.getCertificate(url))
            .thenReturn(null);
        CertificateDetails certDetails =
            certificateService.getCertificateDetails(url, CertificateUsageType.WEB);

    }

    @Test
    void testGetCertificateDetails_whenServer() {
        String url = "myHost:443";
        Mockito.when(serverCertificateRepository.getCertificate("myHost", 443))
            .thenReturn(null);
        CertificateDetails certDetails =
            certificateService.getCertificateDetails(url, CertificateUsageType.SERVER);

    }

}
