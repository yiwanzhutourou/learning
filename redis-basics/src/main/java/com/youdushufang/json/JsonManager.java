package com.youdushufang.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.youdushufang.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonManager {

    private static volatile JsonManager INSTANCE;

    private final JsonMapper jsonMapper;

    private JsonManager() {
        jsonMapper = new JsonMapper();
    }

    public static JsonManager getInstance() {
        if (INSTANCE == null) {
            synchronized (JsonManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new JsonManager();
                }
            }
        }
        return INSTANCE;
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
