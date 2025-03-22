package com.trackdatcert.web.controllers;

import com.trackdatcert.services.certificate.CertificateService;
import com.trackdatcert.services.certificate.model.CertificateDetails;
import com.trackdatcert.services.certificate.model.CertificateUsageType;
import com.trackdatcert.web.controllers.model.CertificateDetailsResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/certificates")
@Slf4j
@AllArgsConstructor
public class CertificateController {

    private final CertificateService certificateService;

    @GetMapping(value="/{certUsageType}")
    public ResponseEntity<CertificateDetailsResponse> getWebServerCertificate(
        @PathVariable("certUsageType") String certUsageType,
        @RequestParam("url") String url) {

        CertificateUsageType certType = CertificateUsageType.valueOf(certUsageType.toUpperCase());
        var details = certificateService.getCertificateDetails(url, certType);
        var response = Utils.convertToResponse(details);
        return ResponseEntity.ok().body(response);
    }


}
