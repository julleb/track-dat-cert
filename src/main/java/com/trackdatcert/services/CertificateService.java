package com.trackdatcert.services;

import com.trackdatcert.repositories.certificate.SAMLCertificateRepository;
import com.trackdatcert.repositories.certificate.ServerCertificateRepository;
import com.trackdatcert.repositories.certificate.WebCertificateRepository;
import com.trackdatcert.services.model.CertificateDetails;
import com.trackdatcert.services.model.CertificateUsageType;
import com.trackdatcert.utils.ObjectUtils;
import com.trackdatcert.utils.UrlUtils;
import java.security.cert.X509Certificate;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class CertificateService {

    private WebCertificateRepository webCertificateRepository;
    private SAMLCertificateRepository samlCertificateRepository;
    private ServerCertificateRepository serverCertificateRepository;

    public CertificateDetails getCertificateDetails(String url, CertificateUsageType certificateUsageType) {
        log.info("Getting certificate details for url: {} and type {}", url, certificateUsageType);
        ObjectUtils.requireNonEmpty(url, "url cannot be empty");
        ObjectUtils.requireNonEmpty(certificateUsageType, "certificateUsageType cannot be empty");
        X509Certificate certificate = switch(certificateUsageType) {
            case SAML -> samlCertificateRepository.getCertificate(url);
            case WEB -> webCertificateRepository.getCertificate(url);
            case SERVER -> {
                var host = UrlUtils.getHostFromUrl(url);
                int port = UrlUtils.getPortFromUrl(url);
                yield serverCertificateRepository.getCertificate(host, port);
            }
        };
        return CertificateDetails.builder()
                .serialNumber(certificate.getSerialNumber().toString())
                .issuer(certificate.getIssuerX500Principal().getName())
                .commonName(certificate.getSubjectX500Principal().getName())
                .validFrom(certificate.getNotBefore().getTime())
                .validTo(certificate.getNotAfter().getTime())
                .build();
    }

}
