package com.trackdatcert.web.controllers;

import com.trackdatcert.services.CertificateService;
import com.trackdatcert.services.model.CertificateDetails;
import com.trackdatcert.services.model.CertificateUsageType;
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
    public ResponseEntity<CertificateDetails> getWebServerCertificate(
        @PathVariable("certUsageType") String certUsageType,
        @RequestParam("url") String url) {

        CertificateUsageType certType = CertificateUsageType.valueOf(certUsageType.toUpperCase());
        var details = certificateService.getCertificateDetails(url, certType);
        return ResponseEntity.ok().body(details);
    }


}
