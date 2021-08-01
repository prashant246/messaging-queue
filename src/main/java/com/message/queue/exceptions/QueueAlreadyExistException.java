package com.message.queue.exceptions;

/**
 * created by prashant246 on 2021-08-01
 */

public class QueueAlreadyExistException extends RuntimeException {

    public QueueAlreadyExistException(String queueName) {
        super(String.format("Queue: %s already exist in the system", queueName));
    }
}
