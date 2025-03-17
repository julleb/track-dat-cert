package com.trackdatcert.services.certificate;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.trackdatcert.TestData;
import com.trackdatcert.services.certificate.model.TrackedCertificate;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TestTrackedCertificateCreator {

    @Autowired
    private DataSource dataSource;


    @Autowired
    private TrackedCertificateCreator trackedCertificateCreator;

    @Autowired
    private TrackedCertificateFetcher trackedCertificateFetcher;

    private TrackedCertificate.TrackedCertificateBuilder trackedCertificateBuilder;

    @BeforeEach
    void setup() {
        trackedCertificateBuilder = TestData.getTrackedCertificateBuilder();
    }

    @Test
    void testAddTrackedCertificate() {
        var trackedCert = trackedCertificateBuilder.build();
        trackedCertificateCreator.addTrackedCertificate(trackedCert, TestData.USER_ID);
        var result = trackedCertificateFetcher.getTrackedCertificate(trackedCert.getName());
        assertEquals(trackedCert.getName(), result.getName());
    }


    @Test
    void testAddTrackedCertificate_whenTransactionFails() {
        //TODO: Implement this test
    }

}
