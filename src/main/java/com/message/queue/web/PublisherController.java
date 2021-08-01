package com.message.queue.web;

import com.message.queue.dto.request.PublishMessageRequest;
import com.message.queue.service.PublisherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * created by prashant246 on 2021-08-01
 */

@RestController
@RequestMapping("/v1/publish")
@Slf4j
public class PublisherController {

    private final PublisherService publisherService;

    @Autowired
    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void publishMessage(@Valid @RequestBody PublishMessageRequest publishMessageRequest) {
        publisherService.publishMessage(publishMessageRequest);

    }
}
