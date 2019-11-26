package com.youdushufang.jedis;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisException;
import redis.clients.jedis.params.SetParams;

import java.time.Duration;
import java.util.Collections;
import java.util.function.Function;

@Slf4j
public final class JedisManager {

    private static final String DELETE_IF_EXISTS_SCRIPT =
            "if redis.call(\"get\", KEYS[1]) == ARGV[1] then " +
                    "return redis.call(\"del\", KEYS[1]) else return 0 end";

    private static volatile JedisManager INSTANCE;

    private final JedisPool jedisPool;

    private JedisManager() {
        this.jedisPool = new JedisPool(buildPoolConfig(), "localhost");
    }

    public static JedisManager getInstance() {
        if (INSTANCE == null) {
            synchronized (JedisManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new JedisManager();
                }
            }
        }
        return INSTANCE;
    }

    public void shutdown() {
        jedisPool.close();
    }

    public String set(String key, String value) {
        return tryWithPool(jedis -> jedis.set(key, value));
    }

    public String get(String key) {
        return tryWithPool(jedis -> jedis.get(key));
    }

    public long del(String key) {
        Long result = tryWithPool(jedis -> jedis.del(key));
        return result != null ? result : 0;
    }

    public boolean setIfNotExists(String key, String value, int expireSeconds) {
        Boolean result = tryWithPool(jedis -> "OK".equals(jedis.set(key, value,
                SetParams.setParams().nx().ex(expireSeconds))));
        return result != null && result;
    }

    public long delIfEquals(String key, String value) {
        Object result = tryWithPool(jedis -> jedis.eval(DELETE_IF_EXISTS_SCRIPT,
                Collections.singletonList(key), Collections.singletonList(value)));
        return result instanceof Number ? ((Number) result).longValue() : 0;
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
