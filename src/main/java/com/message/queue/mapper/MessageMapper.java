package com.message.queue.mapper;

import com.message.queue.dto.request.PublishMessageRequest;
import com.message.queue.entity.Messages;
import com.message.queue.entity.Queues;
import com.message.queue.entity.Subscriber;
import com.message.queue.entity.SubscriptionMessage;
import com.message.queue.enums.MessageStatus;
import com.message.queue.utils.IdGenerator;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

/**
 * created by prashant246 on 2021-08-01
 */

@Component
public class MessageMapper {

    public Messages toMessage(PublishMessageRequest request, Queues queue){
        Messages messages = Messages.builder()
                .status(MessageStatus.PENDING)
                .queueId(queue.getId())
                .message(request.getMessage())
                .build();
        messages.setId(IdGenerator.getRandomString());
        messages.setCreatedAt(DateTime.now().getMillis());
        messages.setUpdateAt(DateTime.now().getMillis());
        return messages;
    }

    public SubscriptionMessage toSubscriptionMessage(Subscriber subscriber, Messages messages){
        SubscriptionMessage subscriptionMessage = SubscriptionMessage.builder()
                .messageId(messages.getId())
                .status(MessageStatus.PENDING)
                .subscriptionId(subscriber.getId())
                .retry(0)
                .build();
        subscriptionMessage.setCreatedAt(DateTime.now().getMillis());
        subscriptionMessage.setUpdatedAt(DateTime.now().getMillis());
        return subscriptionMessage;
    }
}
