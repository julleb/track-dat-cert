package com.trackdatcert.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trackdatcert.TestData;
import com.trackdatcert.services.certificate.TrackedCertificateService;
import com.trackdatcert.services.certificate.model.TrackedCertificate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TrackedCertificateController.class)
class TestTrackedCertificateController {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TrackedCertificateService trackedCertificateService;

    @BeforeEach
    void setup() {

    }

    @Test
    void testGetTrackedCertificatesForUser() throws Exception {
        var trackedCertificate = TestData.getTrackedCertificateBuilder().build();
        Mockito.when(trackedCertificateService.getTrackedCertificatesForCurrentUser())
            .thenReturn(List.of(trackedCertificate));

        mockMvc.perform(get(TrackedCertificateController.RESOURCE_NAME))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].name").value(trackedCertificate.getName()))
            .andExpect(jsonPath("$[0].description").value(trackedCertificate.getDescription()))
            .andExpect(jsonPath("$[0].url").value(trackedCertificate.getUrl()))
            .andExpect(jsonPath("$[0].usageType").value(trackedCertificate.getUsageType().name()))
            .andExpect(jsonPath("$[0].certificateDetails[0].serialNumber").value(trackedCertificate.getCertificateDetails().getFirst().getSerialNumber()))
            .andExpect(jsonPath("$[0].certificateDetails[0].issuer").value(trackedCertificate.getCertificateDetails().getFirst().getIssuer()))
            .andExpect(jsonPath("$[0].certificateDetails[0].commonName").value(trackedCertificate.getCertificateDetails().getFirst().getCommonName()))
            .andExpect(jsonPath("$[0].certificateDetails[0].validFrom").value(trackedCertificate.getCertificateDetails().getFirst().getValidFrom()))
            .andExpect(jsonPath("$[0].certificateDetails[0].validTo").value(trackedCertificate.getCertificateDetails().getFirst().getValidTo()));
    }


    @Test
    void testGetTrackedCertificatesForUser_whenError() throws Exception {
        Mockito.when(trackedCertificateService.getTrackedCertificatesForCurrentUser())
            .thenThrow(new RuntimeException("Error"));

        mockMvc.perform(get(TrackedCertificateController.RESOURCE_NAME))
            .andExpect(status().is5xxServerError())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.message").exists());
    }


    @Test
    void testCreateTrackedCertificate() throws Exception {
        var trackedCertificate = TestData.getTrackedCertificateBuilder().build();
        String json = new ObjectMapper().writeValueAsString(trackedCertificate);

        mockMvc.perform(post(TrackedCertificateController.RESOURCE_NAME)
                .content(json))
            .andExpect(status().isNoContent());

        Mockito.verify(trackedCertificateService, Mockito.times(1))
            .addTrackedCertificate(Mockito.any(TrackedCertificate.class));
    }
}
