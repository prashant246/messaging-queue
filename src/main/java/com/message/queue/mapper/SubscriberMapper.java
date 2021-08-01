package com.message.queue.mapper;

import com.message.queue.dto.QueueDto;
import com.message.queue.dto.SubscriberDto;
import com.message.queue.dto.request.CreateQueueRequest;
import com.message.queue.dto.request.CreateSubscriptionRequest;
import com.message.queue.entity.Queues;
import com.message.queue.entity.Subscriber;
import com.message.queue.utils.IdGenerator;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


/**
 * created by prashant246 on 2021-08-01
 */

@Component
public class SubscriberMapper {

    public SubscriberDto toSubsriberDto(Subscriber subscriber){
        return SubscriberDto.builder()
                .subscriberName(subscriber.getSubscriberName())
                .callbackUrl(subscriber.getCallbackUrl())
                .subscriberEndpoint(subscriber.getSubscriberEndpoint())
                .httpMethod(subscriber.getHttpMethod())
                .callbackHttpMethod(subscriber.getCallbackHttpMethod())
                .createAt(new DateTime(subscriber.getCreatedAt()))
                .build();
    }

    public List<SubscriberDto> toSubscriberDtos(List<Subscriber> subscribers) {
        return subscribers.stream().map(subscriber -> toSubsriberDto(subscriber)).collect(Collectors.toList());
    }

    public Subscriber toSubscriber(CreateSubscriptionRequest request, Queues queue){
        Subscriber subscriber = Subscriber.builder()
                .subscriberName(request.getSubscriberName())
                .callbackUrl(request.getCallbackUrl())
                .queueId(queue.getId())
                .httpMethod(request.getHttpMethod())
                .subscriberEndpoint(request.getSubscriberEndpoint())
                .callbackHttpMethod(request.getCallbackHttpMethod())
                .build();
        subscriber.setId(IdGenerator.getRandomString());
        subscriber.setCreatedAt(DateTime.now().getMillis());
        subscriber.setUpdateAt(DateTime.now().getMillis());
        return subscriber;
    }
}
