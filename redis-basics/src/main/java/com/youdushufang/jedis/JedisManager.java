package com.youdushufang.jedis;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisException;

import java.time.Duration;
import java.util.function.Function;

@Slf4j
public final class JedisManager {

    public static final JedisManager INSTANCE = new JedisManager();

    private final JedisPool jedisPool;

    private JedisManager() {
        this.jedisPool = new JedisPool(buildPoolConfig(), "localhost");
    }

    public String set(String key, String value) {
        return tryWithPool(jedis -> jedis.set(key, value));
    }

    public String get(String key) {
        return tryWithPool(jedis -> jedis.get(key));
    }

    public void shutdown() {
        jedisPool.close();
    }

    private <T> T tryWithPool(Function<Jedis, T> func) {
        try (Jedis jedis = jedisPool.getResource()) {
            return func.apply(jedis);
        } catch (JedisException e) {
            log.error("jedis error: " + e.getMessage(), e);
        }
        return null;
    }

    private JedisPoolConfig buildPoolConfig() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(64);
        poolConfig.setMaxIdle(64);
        poolConfig.setMinIdle(8);
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(true);
        poolConfig.setTestWhileIdle(true);
        poolConfig.setMinEvictableIdleTimeMillis(Duration.ofSeconds(60).toMillis());
        poolConfig.setTimeBetweenEvictionRunsMillis(Duration.ofSeconds(30).toMillis());
        poolConfig.setNumTestsPerEvictionRun(3);
        poolConfig.setBlockWhenExhausted(true);
        return poolConfig;
    }
}
