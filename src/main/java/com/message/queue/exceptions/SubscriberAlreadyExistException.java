package com.message.queue.exceptions;

/**
 * created by prashant246 on 2021-08-01
 */

public class SubscriberAlreadyExistException extends RuntimeException {

    public SubscriberAlreadyExistException(String queueName) {
        super(String.format("Subscriber: %s already exist in the system", queueName));
    }
}
