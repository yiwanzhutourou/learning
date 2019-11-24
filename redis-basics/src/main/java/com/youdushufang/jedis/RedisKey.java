package com.youdushufang.jedis;

public class RedisKey {

    private RedisKey() { }

    public static String getRedisKey(Long id) {
        return "com:youdushufang:model:User:" + id;
    }
}
