package com.message.queue.filters;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Component
@Order(1)
public class RequestIdFilter implements Filter {
    private static final String REQUEST_ID = "request-id";

    private AtomicLong id = new AtomicLong(1);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        String requestId = httpServletRequest.getHeader(REQUEST_ID);
        if (requestId == null) {
            requestId = String.valueOf(id.getAndIncrement());
        }

        MDC.put(REQUEST_ID, requestId);

        try {
            chain.doFilter(request, response);
        } finally {
            MDC.remove(REQUEST_ID);
        }
    }
}
