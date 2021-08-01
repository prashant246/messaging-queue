package com.message.queue.relayer;

import com.message.queue.Database;
import com.message.queue.entity.Subscriber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * created by prashant246 on 2021-08-01
 */

@Component
@Slf4j
public class Scheduler {

    private final MessageRelay messageRelay;

    @Autowired
    public Scheduler(MessageRelay messageRelay) {
        this.messageRelay = messageRelay;
    }

    @Scheduled(fixedRate = 1000)
    public void initRelayer() {
        List<Subscriber> activeSubscriptions = Database.getActiveSubscriptions();
        activeSubscriptions.stream().forEach(sub -> {
            log.info(String.format("Starting message relay for subscription: %s", sub.getSubscriberName()));
            messageRelay.relayPendingMessages(sub);
        });
    }

}
