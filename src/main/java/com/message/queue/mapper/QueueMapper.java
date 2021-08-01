package com.message.queue.mapper;

import com.message.queue.dto.QueueDto;
import com.message.queue.dto.request.CreateQueueRequest;
import com.message.queue.entity.Queues;
import com.message.queue.utils.IdGenerator;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

/**
 * created by prashant246 on 2021-08-01
 */

@Component
public class QueueMapper {

    public Queues toQueue(CreateQueueRequest request){
        Queues queues = Queues.builder()
                .active(Boolean.TRUE)
                .createdBy(request.getRequestedBy())
                .queueName(request.getQueueName())
                .build();
        queues.setId(IdGenerator.getRandomString());
        queues.setCreatedAt(DateTime.now().getMillis());
        queues.setUpdateAt(DateTime.now().getMillis());
        return queues;
    }

    public QueueDto toQueueDto(Queues queues){
        return QueueDto.builder()
                .createBy(queues.getCreatedBy())
                .queueName(queues.getQueueName())
                .createdAt(new DateTime(queues.getCreatedAt()))
                .build();
    }
}
