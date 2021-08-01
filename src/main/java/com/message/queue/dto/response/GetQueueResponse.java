package com.message.queue.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.message.queue.dto.MessageDto;
import com.message.queue.dto.QueueDto;
import com.message.queue.dto.SubscriberDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * created by prashant246 on 2021-08-01
 */

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class GetQueueResponse {
    QueueDto queue;
    List<SubscriberDto> subscribers;
    List<MessageDto> messages;
}
