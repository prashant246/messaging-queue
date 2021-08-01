package com.message.queue.web;

import com.message.queue.dto.request.CreateSubscriptionRequest;
import com.message.queue.dto.request.UpdateSubscriptionRequest;
import com.message.queue.service.SubscriptionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * created by prashant246 on 2021-08-01
 */

@RestController
@RequestMapping("/v1/subscribe")
@Slf4j
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @Autowired
    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createSubscription(@Valid @RequestBody final CreateSubscriptionRequest request) {
        subscriptionService.createSubscription(request);
    }

    @PostMapping("/update")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateSubscription(@Valid @RequestBody final UpdateSubscriptionRequest updateRequest) {
        subscriptionService.updateSubscription(updateRequest);
    }

    @PostMapping("/retry/{subscriptionName}")
    @ResponseStatus(HttpStatus.OK)
    public void retrySubscription(@Valid @PathVariable String subscriptionName) {
        subscriptionService.retryMessages(subscriptionName);
    }

    @GetMapping("/{subscriptionName}/relay/start")
    @ResponseStatus(HttpStatus.OK)
    public void startRelaying(@Valid @PathVariable String subscriptionName) {
        subscriptionService.startRelay(subscriptionName);
    }

    @GetMapping("/{subscriptionName}/relay/stop")
    @ResponseStatus(HttpStatus.OK)
    public void stopRelaying(@Valid @PathVariable String subscriptionName) {
        subscriptionService.stopRelay(subscriptionName);
    }
}
