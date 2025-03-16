package com.trackdatcert.web.filters;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import java.io.IOException;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Order(1)
public class CorrelationIdFilter implements Filter {

    private static final String CORRELATION_ID_HEADER_NAME = "X-Correlation-Id";
    private static final String CORRELATION_ID_LOG_VAR_NAME = "correlationId";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
        FilterChain filterChain) throws IOException, ServletException {

        String correlationId = generateCorrelationId();
        MDC.put(CORRELATION_ID_LOG_VAR_NAME, correlationId);
        filterChain.doFilter(servletRequest, servletResponse);
        MDC.remove(CORRELATION_ID_LOG_VAR_NAME);
    }

    private String generateCorrelationId() {
        return UUID.randomUUID().toString();
    }
}
