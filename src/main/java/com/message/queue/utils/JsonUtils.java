package com.message.queue.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.message.queue.exceptions.JsonParseException;

import java.io.IOException;

/**
 * created by prashant246 on 2021-08-01
 */

public class JsonUtils {

    public static String convertObjectToJson(Object obj) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JodaModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new JsonParseException("Json Parse Exception");
        }
    }

    public static <T> T convertJsonToObject(String json, Class<T> objectClass) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, objectClass);
        } catch (IOException e) {
            throw new JsonParseException("Json Parse Exception");
        }
    }
}
