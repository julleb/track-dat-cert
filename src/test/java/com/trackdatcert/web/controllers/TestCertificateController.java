package com.trackdatcert.web.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.trackdatcert.TestData;
import com.trackdatcert.services.certificate.CertificateService;
import com.trackdatcert.services.certificate.model.CertificateDetails;
import com.trackdatcert.services.certificate.model.CertificateUsageType;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CertificateController.class)
class TestCertificateController {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CertificateService certificateService;

    private CertificateDetails.CertificateDetailsBuilder certDetailsBuilder = TestData.getCertificateDetailsBuilder();

    @Test
    void testGetCertificates_whenSaml() throws Exception {
        Mockito.when(
                certificateService.getCertificateDetails("www.example.com", CertificateUsageType.SAML))
            .thenReturn(certDetailsBuilder.build());
        mockMvc.perform(get("/certificates/saml?url=www.example.com"))
            .andExpect(status().isOk());
    }

    @Test
    void testGetCertificates_whenWeb() throws Exception {
        Mockito.when(
                certificateService.getCertificateDetails("www.example.com", CertificateUsageType.WEB))
            .thenReturn(certDetailsBuilder.build());
        mockMvc.perform(get("/certificates/web?url=www.example.com"))
            .andExpect(status().isOk());
    }

    @Test
    void testGetCertificates_whenServer() throws Exception {
        Mockito.when(
                certificateService.getCertificateDetails("www.example.com", CertificateUsageType.SERVER))
            .thenReturn(certDetailsBuilder.build());
        mockMvc.perform(get("/certificates/server?url=www.example.com"))
            .andExpect(status().isOk());
    }

    @Test
    void testGetCertificates_whenBadType() throws Exception {
        mockMvc.perform(get("/certificates/abc?url=www.example.com"))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.message").exists());
    }

}
