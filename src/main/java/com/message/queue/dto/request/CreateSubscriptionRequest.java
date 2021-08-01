package com.message.queue.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpMethod;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * created by prashant246 on 2021-08-01
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CreateSubscriptionRequest {

    @Valid
    @NotBlank(message = "Queue Name can not be blank")
    String queueName;

    @Valid
    @NotBlank
    String subscriberName;

    @Valid
    @NotBlank
    String subscriberEndpoint;

    @Valid
    @NotNull
    HttpMethod httpMethod;

    String callbackUrl;

    HttpMethod callbackHttpMethod;
}
