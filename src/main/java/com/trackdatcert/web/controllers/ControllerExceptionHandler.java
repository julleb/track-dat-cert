package com.trackdatcert.web.controllers;

import com.trackdatcert.web.controllers.model.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(assignableTypes = {CertificateController.class, TrackedCertificateController.class})
@Slf4j
class ControllerExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleException(Exception e) {
        log.error("Exception caught: {}", e.getMessage(), e);
        var errorMsg = ErrorMessage.builder().message(e.getMessage()).build();
        return switch(e) {
            case IllegalArgumentException ignored:
                yield ResponseEntity.badRequest().body(errorMsg);
            default:
                yield ResponseEntity.internalServerError().body(errorMsg);
        };
    }

}
