package com.youdushufang.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.youdushufang.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
public class JsonManager {

    public static final JsonManager INSTANCE = new JsonManager();

    private final JsonMapper jsonMapper;

    private JsonManager() {
        jsonMapper = new JsonMapper();
    }

    public <T> String toJson(T object) {
        if (object == null) {
            return null;
        }
        try {
            return jsonMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("toJson failed: " + object, e);
        }
        return null;
    }

    public <T> T fromJson(String json, Class<T> clazz) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        try {
            return jsonMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            log.error("fromJson failed: " + json, e);
        }
        return null;
    }
}
