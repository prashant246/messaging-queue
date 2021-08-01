package com.message.queue.entity;

import com.message.queue.enums.MessageStatus;
import lombok.Builder;
import lombok.Data;

/**
 * created by prashant246 on 2021-08-01
 */

@Data
@Builder
public class Messages extends BaseEntity {

    String queueId;

    String message;

    MessageStatus status;

}
