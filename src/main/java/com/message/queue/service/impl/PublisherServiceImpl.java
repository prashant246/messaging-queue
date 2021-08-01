package com.message.queue.service.impl;

import com.message.queue.Database;
import com.message.queue.dto.request.PublishMessageRequest;
import com.message.queue.entity.Messages;
import com.message.queue.entity.Queues;
import com.message.queue.enums.MessageStatus;
import com.message.queue.exceptions.QueueNotFoundException;
import com.message.queue.mapper.MessageMapper;
import com.message.queue.relayer.MessageRelay;
import com.message.queue.service.PublisherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * created by prashant246 on 2021-08-01
 */

@Slf4j
@Service
public class PublisherServiceImpl implements PublisherService {

    private final MessageMapper messageMapper;
    private final MessageRelay messageRelay;

    @Autowired
    public PublisherServiceImpl(MessageMapper messageMapper, MessageRelay messageRelay) {
        this.messageMapper = messageMapper;
        this.messageRelay = messageRelay;
    }

    @Override
    public void publishMessage(PublishMessageRequest publishMessageRequest) {
        Queues queue = validateRequest(publishMessageRequest);
        Messages message = messageMapper.toMessage(publishMessageRequest, queue);
        Database.addMessages(message);
        messageRelay.relayToSubscriber(message);
        Messages messages = Database.getMessageByMessageId(message.getId());
        messages.setStatus(MessageStatus.PUBLISHED);
    }

    private Queues validateRequest(PublishMessageRequest publishMessageRequest) {
        Queues queues = Database.getQueueByQueueName(publishMessageRequest.getQueueName());
        if (queues == null) {
            throw new QueueNotFoundException(publishMessageRequest.getQueueName());
        }
        return queues;
    }
}
