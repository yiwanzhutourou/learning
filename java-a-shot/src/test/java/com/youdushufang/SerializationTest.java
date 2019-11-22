package com.youdushufang;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.youdushufang.serialization.User;
import org.junit.jupiter.api.Test;

class SerializationTest {

    @Test
    void testTransient() throws JsonProcessingException {
        User user = new User();
        user.setId(10001L);
        user.setName("wanger");
        user.setPassword("password");

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(user);
        System.out.println(jsonString);
    }
}
