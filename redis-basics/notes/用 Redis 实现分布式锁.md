# 用 Redis 实现分布式锁

## `setnx`

锁的本质是管程模型，在操作共享资源前，首先需要申明使用权，只有在竞争到使用权后才能操作共享资源。Redis 中的 `setnx` 命令可以确保同时只有一个请求设置成功，设置成功的线程就获取到了操作共享资源的权利，执行完相应的逻辑后，这个线程还需要显示地释放它所占有的锁。没有抢占到锁的线程可以考虑 `sleep` 一段时间后重试，或者直接抛出错误，由后续逻辑或者前端处理。

在分布式环境中，必须为分布式锁设置超时时间，避免客户端因为逻辑错误或者宕机等原因造成锁永远得不到释放的情况，当然 `setnx` 和设置超时时间必须用一条命令设置。

使用 Redis 实现的分布式锁有一个超时问题，如果某个线程获取锁之后，执行时间过长，以至于锁已经过期了，那么其他线程在此时是可以再次获取到锁的，这就有可能导致两个线程同时操作共享资源的情况。使用 `setnx` 实现的分布式锁是无法解决锁超时问题的，为了避免这个问题，不要在获取 Redis 分布式锁之后执行过长时间的任务。

对于 Redis 分布式锁的超时问题，还有一种错误是可以避免的。在第一个线程执行结束时，如果锁已经超时且被另一个线程获取，此时第一个线程执行到释放锁的逻辑，就有可能错误地释放并不是由自己获取的锁。解决的方法是，在获取锁 `setnx` 时设置一个独特的 value，只有在确定 value 相同时才可以释放锁。Redis 没有提供原子的 `delIfEquals` 命令，可以通过 lua 脚本来执行相关的逻辑。

使用 jedis（3.1.0 版本）实现的加锁和解锁代码如下：

```java
// delIfEquals 脚本
private static final String DELETE_IF_EXISTS_SCRIPT =
    "if redis.call(\"get\", KEYS[1]) == ARGV[1] then " +
    "return redis.call(\"del\", KEYS[1]) else return 0 end";
// JedisPool 对象，初始化代码略
private final JedisPool jedisPool;

// 加锁需要用到的 setIfNotExists 方法
public boolean setIfNotExists(String key, String value, int expireSeconds) {
    Boolean result = tryWithPool(jedis -> "OK".equals(jedis.set(key, value,
            SetParams.setParams().nx().ex(expireSeconds))));
    return result != null && result;
}

// 解锁需要用到的 delIfEquals 方法
public int delIfEquals(String key, String value) {
    Object result = tryWithPool(jedis -> jedis.eval(DELETE_IF_EXISTS_SCRIPT,
            Collections.singletonList(key), Collections.singletonList(value)));
    return result instanceof Integer ? (Integer) result : 0;
}

// try with resources
private <T> T tryWithPool(Function<Jedis, T> func) {
    try (Jedis jedis = jedisPool.getResource()) {
        return func.apply(jedis);
    } catch (JedisException e) {
        log.error("jedis error: " + e.getMessage(), e);
    }
    return null;
}
```

使用锁的样板代码如下：

```java
private static final int TIMEOUT = 10;

// runnable.run 是需要被锁住的代码逻辑
public boolean tryLock(String key, String value, Runnable runnable) {
    long startTime = System.currentTimeMillis();
    long timeoutMillis = TIMEOUT * 1000L;
    do {
        // 获取分布式锁
        boolean locked = lock(key, value);
        if (locked) {
            // 获取成功后执行逻辑，finally 代码块里释放锁
            try {
                runnable.run();
                return true;
            } finally {
                unlock(key, value);
            }
        } else {
            // 获取锁失败后睡一秒再尝试
            sleep(1);
        }
    } while (startTime + timeoutMillis > System.currentTimeMillis());
    // 直到超时都没有获取到锁
    return false;
}

private void sleep(long seconds) {
    try {
        TimeUnit.SECONDS.sleep(seconds);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
}

private boolean lock(String key, String value) {
    return jedisManager.setIfNotExists(key, value, TIMEOUT);
}

private void unlock(String key, String value) {
    jedisManager.delIfEquals(key, value);
}
```

完整的实现见 [RedisLock](<https://github.com/yiwanzhutourou/learning/blob/master/redis-basics/src/main/java/com/youdushufang/lock/RedisLock.java>) 和 [JedisManager](<https://github.com/yiwanzhutourou/learning/blob/master/redis-basics/src/main/java/com/youdushufang/jedis/JedisManager.java>)。

## 可重入锁

Java 中的 `synchronized` 锁和 `ReentrantLock` 都是可重入锁，可重入锁是指持有锁的线程请求锁时仍能成功的锁。实现原理基本是维护一个进入次数的变量，同一个线程获取时加一，释放时减一，只有这个值被减为 0 才认为锁被释放了。使用上一节的方法实现的分布式锁如果要支持可重入性，并不需要 Redis 再额外维护什么数据，因为可重入的永远是相同的线程，因此只需要在 `ThreadLocal` 中维护一个锁的 key 值和重入次数的字典，只有第一次 lock 时真正执行 `setnx`，重入时计数加一，释放锁时计数减一，在计数为 0 时才真正释放分布式锁。

具体的 Java 实现可以参考 [ReentrantRedisLock](<https://github.com/yiwanzhutourou/learning/blob/master/redis-basics/src/main/java/com/youdushufang/lock/ReentrantRedisLock.java>)。

当然，这样实现的可重入锁仍然有超时问题，一旦第一次申请的锁已经超时，就不应该让同一个线程再次获取这个锁了，因此 `ThreadLocal` 中维护的计数可能也需要引入超时机制。因为可重入锁在使用时完全可以通过逻辑来规避，且实现成本较大，一般情况下不推荐在业务中使用。

## Redlock 算法

前面讨论的分布式锁方案在集群环境下是有问题的。例如在 Sentinel 集群中，当主节点挂掉而客户端在主节点上申请的锁还没有同步到从节点，此时从节点变成了新的主节点，另一个客户端申请加锁是可以成功的，这就造成了同一把锁被两个客户端持有的情况。为了解决这个问题，Antirez 发明了 Redlock 算法，这个算法的基本原理是在存在 `N` 个实例的集群中，一个客户端只有在超过绝对多数（`N / 2 + 1`）个实例上获取到锁（使用 `setnex`）后才认为这个客户端成功地获取到了锁，如果一个客户端成功获取到了锁，则意味着不再可能有另外一个客户端同时获取到相同的锁，因为已经有绝对多数的实例被其锁定了。当然这个算法还要考虑时钟漂移、失败重试等问题，具体可以参考 [Distributed locks with Redis](<https://redis.io/topics/distlock>)。

Java redis 客户端中，redisson 实现了 RedLock，redisson 还实现了读写锁、信号量等高级同步工具，具体参见 [redisson 文档](<https://github.com/redisson/redisson/wiki/8.-distributed-locks-and-synchronizers#84-redlock>)。

使用 Redlock 算法的分布式锁因为要在集群中的所有实例上申请锁，因此性能相对于单实例版的分布式锁肯定是要低一些的，使用时需要考虑是否真的需要保证绝对的高可用性。