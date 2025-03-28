package com.trackdatcert.services.certificate;

import com.trackdatcert.repositories.certificate.SAMLCertificateRepository;
import com.trackdatcert.repositories.certificate.ServerCertificateRepository;
import com.trackdatcert.repositories.certificate.WebCertificateRepository;
import com.trackdatcert.services.certificate.model.CertificateDetails;
import com.trackdatcert.services.certificate.model.CertificateUsageType;
import com.trackdatcert.utils.ObjectUtils;
import com.trackdatcert.utils.ServerPortUtils;
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
                var host = ServerPortUtils.getHostFromString(url);
                int port = ServerPortUtils.getPortFromString(url);
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
