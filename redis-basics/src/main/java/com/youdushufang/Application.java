package com.youdushufang;

import com.youdushufang.jedis.JedisManager;
import com.youdushufang.jedis.RedisKey;
import com.youdushufang.json.JsonManager;
import com.youdushufang.model.User;

public class Application {

    private static JedisManager jedisManager = JedisManager.INSTANCE;

    private static JsonManager jsonManager = JsonManager.INSTANCE;

    public static void main(String[] args) {
        User user = new User();
        user.setId(10001L);
        user.setName("ComeRich");
        user.setEmail("ComeRich@example.com");
        String result = jedisManager.set(
                RedisKey.getRedisKey(user.getId()), jsonManager.toJson(user));
        System.out.println(result);
        System.out.println(jsonManager.fromJson(
                jedisManager.get(RedisKey.getRedisKey(user.getId())), User.class));
    }
}
