package com.youdushufang;

import com.youdushufang.jedis.JedisManager;
import com.youdushufang.jedis.RedisKey;
import com.youdushufang.json.JsonManager;
import com.youdushufang.model.User;

public class Application {

    public static void main(String[] args) {
        User user = new User();
        user.setId(10001L);
        user.setName("ComeRich");
        user.setEmail("ComeRich@example.com");
        JedisManager jedisManager = JedisManager.getInstance();
        JsonManager jsonManager = JsonManager.getInstance();
        String result = jedisManager.set(
                RedisKey.getRedisKey(user.getId()), jsonManager.toJson(user));
        System.out.println(result);
        System.out.println(jsonManager.fromJson(
                jedisManager.get(RedisKey.getRedisKey(user.getId())), User.class));
        jedisManager.shutdown();
    }
}
