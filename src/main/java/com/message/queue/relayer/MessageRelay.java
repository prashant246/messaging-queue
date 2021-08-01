package com.message.queue.relayer;

import com.message.queue.Database;
import com.message.queue.entity.Messages;
import com.message.queue.entity.Subscriber;
import com.message.queue.entity.SubscriptionMessage;
import com.message.queue.enums.MessageStatus;
import com.message.queue.mapper.MessageMapper;
import com.message.queue.utils.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * created by prashant246 on 2021-08-01
 */

@Component
public class MessageRelay {

    private final MessageMapper messageMapper;
    private final HttpClientUtil httpClientUtil;

    @Autowired
    public MessageRelay(MessageMapper messageMapper, HttpClientUtil httpClientUtil) {
        this.messageMapper = messageMapper;
        this.httpClientUtil = httpClientUtil;
    }

    public void relayToSubscriber(Messages message) {
        String queueId = message.getQueueId();
        List<Subscriber> subscriberList = Database.getSubscriberByQueueId(queueId);

        List<SubscriptionMessage> subscriptionMessages = new ArrayList<>();
        subscriberList.stream().forEach(subscriber -> subscriptionMessages.add(
                messageMapper.toSubscriptionMessage(subscriber, message)));

        Database.addSubscriptionMessages(subscriptionMessages);
    }

    public void relayPendingMessages(Subscriber subscriber) {
        List<SubscriptionMessage> pendingMessagesForSubscriber = Database.getPendingMessagesForSubscriber(subscriber.getId());
        relayMessage(pendingMessagesForSubscriber, subscriber);
    }

    public void unsidelineMessage(Subscriber subscriber) {
        List<SubscriptionMessage> pendingMessagesForSubscriber = Database.getSidelinedMessagesForSubscriber(subscriber.getId());
        relayMessage(pendingMessagesForSubscriber, subscriber);
    }

    private void relayMessage(List<SubscriptionMessage> pendingMessagesForSubscriber, Subscriber subscriber) {
        List<String> messageIds = pendingMessagesForSubscriber.stream().map(subscriptionMessage -> subscriptionMessage.getMessageId()).collect(Collectors.toList());

        Map<String, Messages> messageIdToMessageMap = Database.getMessageIdToMessageMap(messageIds);

        pendingMessagesForSubscriber.stream().forEach(subscriptionMessage -> {
            SubscriptionMessage publish = httpClientUtil.publish(messageIdToMessageMap.get(subscriptionMessage.getMessageId()).getMessage(),
                    new HttpHeaders(),
                    subscriber.getSubscriberEndpoint(),
                    subscriber.getHttpMethod(),
                    subscriptionMessage);
            if (subscriber.getCallbackUrl() != null) {
                httpClientUtil.publish(publish.getResponse(), new HttpHeaders(), subscriber.getCallbackUrl(), subscriber.getCallbackHttpMethod());
            }
            subscriptionMessage.setRetry(subscriptionMessage.getRetry() + 1);
            if (subscriptionMessage.getRetry() > 5) {
                subscriptionMessage.setStatus(MessageStatus.SIDELINED);
            }
        });
    }
}
