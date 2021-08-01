package com.message.queue.service.impl;

import com.message.queue.Database;
import com.message.queue.dto.request.CreateQueueRequest;
import com.message.queue.dto.response.GetQueueResponse;
import com.message.queue.entity.Queues;
import com.message.queue.entity.Subscriber;
import com.message.queue.exceptions.QueueAlreadyExistException;
import com.message.queue.exceptions.QueueNotFoundException;
import com.message.queue.mapper.QueueMapper;
import com.message.queue.mapper.SubscriberMapper;
import com.message.queue.service.QueueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * created by prashant246 on 2021-08-01
 */

@Slf4j
@Service
public class QueueServiceImpl implements QueueService {

    private final QueueMapper queueMapper;
    private final SubscriberMapper subscriberMapper;

    @Autowired
    public QueueServiceImpl(QueueMapper queueMapper, SubscriberMapper subscriberMapper) {
        this.queueMapper = queueMapper;
        this.subscriberMapper = subscriberMapper;
    }

    @Override
    public void createQueue(CreateQueueRequest request) {
        validateQueueCreation(request);
        Queues queues = queueMapper.toQueue(request);
        Database.addQueue(queues);
    }

    @Override
    public GetQueueResponse getQueue(String queueName) {
        Queues queues = Database.getQueueByQueueName(queueName);
        if (queues == null) {
            throw new QueueNotFoundException(queueName);
        }

        List<Subscriber> subscriberList = Database.getSubscriberByQueueId(queues.getId());
        return GetQueueResponse.builder()
                .queue(queueMapper.toQueueDto(queues))
                .subscribers(subscriberMapper.toSubscriberDtos(subscriberList))
                .build();
    }

    private void validateQueueCreation(CreateQueueRequest request) {
        Queues queue = Database.getQueueByQueueName(request.getQueueName());
        if (queue != null) {
            throw new QueueAlreadyExistException(request.getQueueName());
        }
    }
}
