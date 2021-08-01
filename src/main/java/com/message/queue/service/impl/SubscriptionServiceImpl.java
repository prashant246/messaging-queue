package com.message.queue.service.impl;

import com.message.queue.Database;
import com.message.queue.dto.request.CreateSubscriptionRequest;
import com.message.queue.dto.request.UpdateSubscriptionRequest;
import com.message.queue.entity.Queues;
import com.message.queue.entity.Subscriber;
import com.message.queue.exceptions.QueueNotFoundException;
import com.message.queue.exceptions.SubscriberAlreadyExistException;
import com.message.queue.exceptions.SubscriberNotFoundException;
import com.message.queue.mapper.SubscriberMapper;
import com.message.queue.relayer.MessageRelay;
import com.message.queue.service.SubscriptionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * created by prashant246 on 2021-08-01
 */

@Slf4j
@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriberMapper subscriberMapper;
    private final MessageRelay messageRelay;

    @Autowired
    public SubscriptionServiceImpl(SubscriberMapper subscriberMapper, MessageRelay messageRelay) {
        this.subscriberMapper = subscriberMapper;
        this.messageRelay = messageRelay;
    }

    @Override
    public void createSubscription(CreateSubscriptionRequest request) {
        Queues queues = Database.getQueueByQueueName(request.getQueueName());
        if (queues == null) {
            throw new QueueNotFoundException(request.getQueueName());
        }
        validateQueueCreation(request);
        Subscriber subscriber = subscriberMapper.toSubscriber(request, queues);
        Database.addSubscribers(subscriber);
        Database.addActiveSubscriptions(subscriber);
    }

    @Override
    public void updateSubscription(UpdateSubscriptionRequest updateRequest) {
        Subscriber subscriber = Database.getSubscriberBySubscriberName(updateRequest.getSubscriberName());
        if (subscriber == null) {
            throw new SubscriberNotFoundException(updateRequest.getSubscriberName());
        }
        if (updateRequest.getCallbackUrl() != null) {
            subscriber.setCallbackUrl(updateRequest.getCallbackUrl());
        }

        if (updateRequest.getSubscriberEndpoint() != null) {
            subscriber.setSubscriberEndpoint(updateRequest.getSubscriberEndpoint());
        }
    }

    @Override
    public void retryMessages(String subscriptionName) {
        Subscriber subscriber = Database.getSubscriberBySubscriberName(subscriptionName);
        if (subscriber == null) {
            throw new SubscriberNotFoundException(subscriptionName);
        }
        messageRelay.unsidelineMessage(subscriber);
    }

    @Override
    public void startRelay(String subscriptionName) {
        Subscriber subscriber = Database.getSubscriberBySubscriberName(subscriptionName);
        if (subscriber == null) {
            throw new SubscriberNotFoundException(subscriptionName);
        }
        Database.addActiveSubscriptions(subscriber);
    }

    @Override
    public void stopRelay(String subscriptionName) {
        Subscriber subscriber = Database.getSubscriberBySubscriberName(subscriptionName);
        if (subscriber == null) {
            throw new SubscriberNotFoundException(subscriptionName);
        }
        Database.removeActiveSubscriptionn(subscriber);
    }

    private void validateQueueCreation(CreateSubscriptionRequest request) {
        Subscriber subscriber = Database.getSubscriberBySubscriberName(request.getSubscriberName());
        if (subscriber != null) {
            throw new SubscriberAlreadyExistException(request.getQueueName());
        }
    }
}
