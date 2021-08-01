package com.message.queue.exceptions;

/**
 * created by prashant246 on 2021-08-01
 */

public class SubscriberNotFoundException extends RuntimeException {

    public SubscriberNotFoundException(String queueName) {
        super(String.format("Subscriber: %s not exist in the system", queueName));
    }
}
