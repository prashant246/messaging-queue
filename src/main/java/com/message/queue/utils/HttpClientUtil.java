package com.message.queue.utils;

import com.message.queue.entity.SubscriptionMessage;
import com.message.queue.enums.MessageStatus;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * created by prashant246 on 2021-08-01
 */

@Slf4j
@Component
public class HttpClientUtil {

    private final RestTemplate restTemplate;

    @Autowired
    public HttpClientUtil(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public SubscriptionMessage publish(String message, HttpHeaders headers, String url, HttpMethod httpMethod, SubscriptionMessage subscriptionMessage) {
        log.info(String.format("Publishing messageId: %s to subscriber: %s", subscriptionMessage.getMessageId(), subscriptionMessage.getSubscriptionId()));
        HttpEntity<?> httpEntity = new HttpEntity<Object>(message, headers);
        ResponseEntity<String> response = null;

        try {
            response = restTemplate.exchange(url, httpMethod, httpEntity, String.class);
        }
        catch (Exception e){
            log.error(String.format("Unknown Error from Subscriber: %s ", e.getMessage()));
            subscriptionMessage.setResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            subscriptionMessage.setResponse(e.getMessage());
            subscriptionMessage.setStatus(MessageStatus.FAILED);
            subscriptionMessage.setUpdatedAt(DateTime.now().getMillis());
            return subscriptionMessage;
        }

        if (response.getStatusCode() != HttpStatus.OK) {
            log.error("Error in getting otps from OMS");
            subscriptionMessage.setResponseStatus(response.getStatusCode());
            subscriptionMessage.setResponse(response.getBody());
            subscriptionMessage.setStatus(MessageStatus.FAILED);
            subscriptionMessage.setUpdatedAt(DateTime.now().getMillis());
            return subscriptionMessage;
        }

        subscriptionMessage.setResponseStatus(HttpStatus.OK);
        subscriptionMessage.setResponse(response.getBody());
        subscriptionMessage.setStatus(MessageStatus.PROCESSED);
        subscriptionMessage.setUpdatedAt(DateTime.now().getMillis());
        return subscriptionMessage;
    }

    public void publish(String message, HttpHeaders headers, String url, HttpMethod httpMethod) {
        log.info(String.format("Publishing callback to to subscriber"));
        HttpEntity<?> httpEntity = new HttpEntity<Object>(message, headers);
        ResponseEntity<String> response = null;

        try {
            response = restTemplate.exchange(url, httpMethod, httpEntity, String.class);
        }
        catch (Exception e){
            log.error(String.format("Unknown Error from Subscriber: %s ", e.getMessage()));
            return;
        }

        if (response.getStatusCode() != HttpStatus.OK) {
            return;
        }

        return;
    }
}
