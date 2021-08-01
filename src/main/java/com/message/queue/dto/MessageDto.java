package com.message.queue.dto;

import com.message.queue.enums.MessageStatus;
import lombok.Data;

/**
 * created by prashant246 on 2021-08-01
 */

@Data
public class MessageDto {

    String message;

    MessageStatus status;

    String response;
}
