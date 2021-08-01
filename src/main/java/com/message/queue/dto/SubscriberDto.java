package com.message.queue.dto;

import lombok.Builder;
import lombok.Data;
import org.joda.time.DateTime;
import org.springframework.http.HttpMethod;

/**
 * created by prashant246 on 2021-08-01
 */

@Data
@Builder
public class SubscriberDto {

    String subscriberName;

    String callbackUrl;

    String subscriberEndpoint;

    DateTime createAt;

    HttpMethod httpMethod;

    HttpMethod callbackHttpMethod;
}
