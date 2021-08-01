package com.message.queue.entity;

import lombok.Builder;
import lombok.Data;

/**
 * created by prashant246 on 2021-08-01
 */

@Data
@Builder
public class Queues extends BaseEntity{

    private String queueName;

    private Boolean active;

    private String createdBy;
}
