package com.message.queue.dto;

import lombok.Builder;
import lombok.Data;
import org.joda.time.DateTime;

/**
 * created by prashant246 on 2021-08-01
 */

@Data
@Builder
public class QueueDto {
    String queueName;
    String createBy;
    DateTime createdAt;
}
