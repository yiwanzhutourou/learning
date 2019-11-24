package com.youdushufang.serialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JacksonTest {

    public static void main(String[] args) throws JsonProcessingException {
        JsonMapper jsonMapper = new JsonMapper();
        System.out.println(jsonMapper.writeValueAsString(Arrays.asList("1", "2", "3")));
        System.out.println(jsonMapper.writeValueAsString(new String[] {"1", "2", "3"}));
        // 并没有序列化为 ["1", "2", "3", null, null, ...]
        List<String> strings = new ArrayList<>(10);
        strings.add("1");
        strings.add("2");
        strings.add("3");
        System.out.println(jsonMapper.writeValueAsString(strings));
    }
}
