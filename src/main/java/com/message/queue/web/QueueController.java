package com.message.queue.web;

import com.message.queue.dto.request.CreateQueueRequest;
import com.message.queue.dto.response.GetQueueResponse;
import com.message.queue.exceptions.NotImplementedException;
import com.message.queue.service.QueueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * created by prashant246 on 2021-08-01
 */

@RestController
@RequestMapping("/v1/queue")
@Slf4j
public class QueueController {

    private final QueueService queueService;

    @Autowired
    public QueueController(QueueService queueService) {
        this.queueService = queueService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createQueue(@Valid @RequestBody final CreateQueueRequest createQueueRequest) {
        queueService.createQueue(createQueueRequest);
    }

    @GetMapping(path = "/{queueName}")
    @ResponseStatus(HttpStatus.OK)
    public GetQueueResponse getQueue(@Valid @PathVariable String queueName) {
        return queueService.getQueue(queueName);
    }

}
