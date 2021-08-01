package com.message.queue.service;

import com.message.queue.dto.request.PublishMessageRequest;

/**
 * created by prashant246 on 2021-08-01
 */

public interface PublisherService {
    void publishMessage(PublishMessageRequest publishMessageRequest);
}
