package com.message.queue.entity;

import lombok.Builder;
import lombok.Data;

/**
 * created by prashant246 on 2021-08-01
 */

@Data
public class BaseEntity {

    String id;

    Long createdAt;

    Long updateAt;
}
