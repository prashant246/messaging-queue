package com.message.queue.entity;

import com.message.queue.enums.MessageStatus;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * created by prashant246 on 2021-08-01
 */

@Data
@Builder
public class SubscriptionMessage {

    String messageId;

    String subscriptionId;

    MessageStatus status;

    String response;

    HttpStatus responseStatus;

    Long createdAt;

    Long updatedAt;

    int retry;
}
