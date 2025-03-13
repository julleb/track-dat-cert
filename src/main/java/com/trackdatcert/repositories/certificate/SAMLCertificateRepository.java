package com.trackdatcert.repositories.certificate;

import com.trackdatcert.config.WebClientConfig;
import com.trackdatcert.utils.ObjectUtils;
import java.io.ByteArrayInputStream;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.parsers.ParserConfigurationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Repository
@Slf4j
public class SAMLCertificateRepository {

    private final CertificateFactory certificateFactory;

    public SAMLCertificateRepository() throws CertificateException {
        certificateFactory = CertificateFactory.getInstance("X.509");
    }

    public X509Certificate getCertificate(String url) {
        ObjectUtils.requireNonEmpty(url, "Url cannot be empty");
        var webClient = WebClientConfig.createWebClient(url);
        var responseEntity = webClient.get()
            .accept(MediaType.APPLICATION_XML)
            .retrieve()
            .toEntity(String.class)
            .map(respEntity -> {
                HttpHeaders headers = respEntity.getHeaders();
                String contentType = headers.getContentType() != null ? headers.getContentType()
                    .toString() : "";
                if (contentType.contains(MediaType.APPLICATION_XML_VALUE)) {
                    return respEntity;
                }
                throw new WebClientResponseException(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(),
                    "Expected XML content, but received: " + contentType, headers, null, null);
            })
            .block();
        if (responseEntity == null) {
            throw new RuntimeException("Failed to get response from url " + url);
        }
        try {
            return getCertificateFromMetadata(responseEntity);
        } catch(ParserConfigurationException | CertificateException e) {
            throw new RuntimeException(e);
        }

    }

    private X509Certificate getCertificateFromMetadata(ResponseEntity<String> responseEntity)
        throws ParserConfigurationException, CertificateException {
        String metadataString = responseEntity.getBody();
        ObjectUtils.requireNonEmpty(metadataString, "Metadata is empty");

        //This should probably be done with an XML parser, but for now we'll use regex
        String regex = "<md:KeyDescriptor use=\"signing\">(.*?)</md:KeyDescriptor>";
        Pattern pattern = Pattern.compile(regex, Pattern.DOTALL); // DOTALL to allow newline characters in base64 data
        Matcher matcher = pattern.matcher(metadataString);
        if(!matcher.find()) {
            throw new RuntimeException("No signing key descriptor found in metadata");
        }
        regex = "X509Certificate>(.*?)</";
        pattern = Pattern.compile(regex, Pattern.DOTALL);
        matcher = pattern.matcher(matcher.group());
        if(!matcher.find()) {
            throw new RuntimeException("No X509Certificate found in metadata");
        }
        String encodedCertificateString = matcher.group()
            .replace("X509Certificate>", "")
            .replace("</", "")
            .replace("\r\n", "")
            .replace("\n", "");

        return getCertificateFromBase64String(encodedCertificateString);
    }

    private X509Certificate getCertificateFromBase64String(String base64String)
        throws CertificateException {
        byte[] decodedCert = Base64.getDecoder().decode(base64String);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(decodedCert);
        return (X509Certificate) certificateFactory.generateCertificate(inputStream);
    }
}
