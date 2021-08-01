package com.message.queue;

import com.message.queue.entity.Messages;
import com.message.queue.entity.Queues;
import com.message.queue.entity.Subscriber;
import com.message.queue.entity.SubscriptionMessage;
import com.message.queue.enums.MessageStatus;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * created by prashant246 on 2021-08-01
 */

public class Database {

    private static List<Messages> messages = new ArrayList<>();
    private static List<Queues> queues = new ArrayList<>();
    private static List<Subscriber> subscribers = new ArrayList<>();
    private static List<SubscriptionMessage> subscriptionMessages = new ArrayList<>();
    private static List<Subscriber> activeSubscriptions = new ArrayList<>();

    public static List<Subscriber> getActiveSubscriptions() {
        return activeSubscriptions;
    }

    public static void addActiveSubscriptions(Subscriber activateSubscription) {
        activeSubscriptions.add(activateSubscription);
    }

    public static void removeActiveSubscriptionn(Subscriber deactivateSubscription){
        if (activeSubscriptions.contains(deactivateSubscription)) {
            activeSubscriptions.remove(deactivateSubscription);
        }
    }

    private static List<MessageStatus> pendingMessageStatus = Arrays.asList(MessageStatus.PENDING, MessageStatus.FAILED);

    private Database(){}

    public static List<Messages> getMessages() {
        return messages;
    }

    public static void addMessages(Messages message) {
        messages.add(message);
    }

    public static void addSubscriptionMessages(List<SubscriptionMessage> messages) {
        subscriptionMessages.addAll(messages);
    }

    public static List<Queues> getQueues() {
        return queues;
    }

    public static void addQueue(Queues queue) {
        queues.add(queue);
    }

    public static List<Subscriber> getSubscribers() {
        return subscribers;
    }

    public static List<SubscriptionMessage> getSubscriptionMessages() {
        return subscriptionMessages;
    }

    public static void addSubscribers(Subscriber subscriber) {
        subscribers.add(subscriber);
    }

    public static Queues getQueueByQueueName(String queueName){
        Optional<Queues> first = queues.stream().filter(q -> q.getQueueName().equals(queueName)).findFirst();
        if (first.isPresent()) {
            return first.get();
        }
        return null;
    }

    public static Messages getMessageByMessageId(String messageId){
        Optional<Messages> first = messages.stream().filter(q -> q.getId().equals(messageId)).findFirst();
        if (first.isPresent()) {
            return first.get();
        }
        return null;
    }

    public static Subscriber getSubscriberBySubscriberName(String subscriberName){
        Optional<Subscriber> first = subscribers.stream().filter(s -> s.getSubscriberName().equals(subscriberName)).findFirst();
        if (first.isPresent()) {
            return first.get();
        }
        return null;
    }

    public static Subscriber getSubscriberByQueueIdAndSubscriberName(String queueId, String subscriberName){
        Optional<Subscriber> first = subscribers.stream()
                .filter(s -> s.getQueueId().equals(queueId))
                .filter(s -> s.getSubscriberName().equals(subscriberName))
                .findFirst();
        if (first.isPresent()) {
            return first.get();
        }
        return null;
    }

    public static List<Subscriber> getSubscriberByQueueId(String queueId){
        return subscribers.stream().filter(subscriber -> subscriber.getQueueId().equals(queueId)).collect(Collectors.toList());
    }

    public static List<SubscriptionMessage> getPendingMessagesForSubscriber(String subscriberId) {
        return subscriptionMessages.stream().filter(subscriptionMessage -> subscriptionMessage.getSubscriptionId().equals(subscriberId))
                .filter(subscriptionMessage -> pendingMessageStatus.contains(subscriptionMessage.getStatus()))
                .collect(Collectors.toList());
    }

    public static Map<String, Messages> getMessageIdToMessageMap(List<String> messageIds) {
        return messages.stream().filter(messages1 -> messageIds.contains(messages1.getId()))
                .collect(Collectors.toMap(Messages::getId, Function.identity()));
    }

    public static List<SubscriptionMessage> getSidelinedMessagesForSubscriber(String subscriberId) {
        return subscriptionMessages.stream().filter(subscriptionMessage -> subscriptionMessage.getSubscriptionId().equals(subscriberId))
                .filter(subscriptionMessage -> MessageStatus.SIDELINED.equals(subscriptionMessage.getStatus()))
                .collect(Collectors.toList());
    }
}
