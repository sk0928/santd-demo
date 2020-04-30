package com.santd.demo.api;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * This class is added purely for logging purposes
 */
@Component
public class CorrelationIdInterceptor extends HandlerInterceptorAdapter {
    public static final String CORRELATION_ID_LOGGER = "correlationId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        MDC.put(CORRELATION_ID_LOGGER, UUID.randomUUID().toString());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        MDC.remove(CORRELATION_ID_LOGGER);
    }
}
