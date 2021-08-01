package com.message.queue.utils;

import java.util.UUID;

/**
 * created by prashant246 on 2021-08-01
 */

public class IdGenerator {

    public static String getRandomString() {
        return UUID.randomUUID().toString();
    }
}
