package com.trackdatcert.web.controllers.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ErrorMessage {
    private String msg;
}
