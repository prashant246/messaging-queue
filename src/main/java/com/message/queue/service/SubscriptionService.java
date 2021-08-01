package com.message.queue.service;

import com.message.queue.dto.request.CreateSubscriptionRequest;
import com.message.queue.dto.request.UpdateSubscriptionRequest;

/**
 * created by prashant246 on 2021-08-01
 */

public interface SubscriptionService {
    void createSubscription(CreateSubscriptionRequest request);

    void updateSubscription(UpdateSubscriptionRequest updateRequest);

    void retryMessages(String subscriptionName);

    void startRelay(String subscriptionName);

    void stopRelay(String subscriptionName);
}
