package com.message.queue.filters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
@Order(2)
public class LoggingFilter implements Filter {
    private static final String HEALTH_CHECK_URI = "/health";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        if (validURIForLogging(httpServletRequest.getRequestURI())) {
            logRequestTrace(httpServletRequest);

            long startTime = System.currentTimeMillis();

            chain.doFilter(httpServletRequest, httpServletResponse);

            long endTime = System.currentTimeMillis();
            log.info("{} Took {} millis. ", httpServletRequest.getRequestURI(), (endTime - startTime));

            logResponseTrace(httpServletResponse);
        } else {
            chain.doFilter(httpServletRequest, response);
        }
    }

    private boolean validURIForLogging(String requestURI) {
        return !requestURI.equals(HEALTH_CHECK_URI) && !requestURI.contains("swagger") && !requestURI.contains("api-docs") && !requestURI.contains("/favicon");
    }

    private void logRequestTrace(HttpServletRequest request) {
        log.info("REQUEST: {}", request.getMethod() + " " + request.getRequestURI());
        Map<String, String> headers = Collections.list(request.getHeaderNames())
                .stream()
                .collect(Collectors.toMap(Function.identity(), request::getHeader));

        if (!headers.isEmpty()) log.info("REQUEST HEADERS: {}", headers);
        if (request.getQueryString() != null) log.info("REQUEST QUERY: {}", request.getQueryString());
    }

    private void logResponseTrace(HttpServletResponse response) {
        log.info("RESPONSE STATUS: {}", response.getStatus());
        Collection<String> responseHeaderNames = response.getHeaderNames();
        Set<String> responseHeaders = new HashSet<>();
        for (String headerName : responseHeaderNames) {
            responseHeaders.add(headerName + ": " + response.getHeader(headerName));
        }
        if (!responseHeaders.isEmpty()) log.info("RESPONSE HEADERS: {}", responseHeaders);
    }
}
