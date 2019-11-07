package com.youdushufang.concurrent.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonMapper {

    private ObjectMapper objectMapper = new ObjectMapper();

    public String toJson(Object data) {
        try {
            return objectMapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public <V> V fromJson(String data, Class<V> clazz) {
        V v = null;
        try {
            v = objectMapper.readValue(data, clazz);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return v;
    }
}
