package com.message.queue.service;

import com.message.queue.dto.request.CreateQueueRequest;
import com.message.queue.dto.response.GetQueueResponse;

/**
 * created by prashant246 on 2021-08-01
 */

public interface QueueService {

    void createQueue(CreateQueueRequest request);

    GetQueueResponse getQueue(String queueName);
}
