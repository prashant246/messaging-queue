package com.message.queue.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpMethod;

/**
 * created by prashant246 on 2021-08-01
 */

@Data
@Builder
public class Subscriber extends BaseEntity {

    String queueId;

    String subscriberName;

    String subscriberEndpoint;

    HttpMethod httpMethod;

    String callbackUrl;

    HttpMethod callbackHttpMethod;
}
