package com.message.queue.exceptions;

/**
 * created by prashant246 on 2021-08-01
 */

public class QueueNotFoundException extends RuntimeException {

    public QueueNotFoundException(String queueName) {
        super(String.format("Queue: %s not exist in the system", queueName));
    }
}
