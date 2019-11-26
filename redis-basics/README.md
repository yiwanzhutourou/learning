# Redis 基础

## 5 种基础数据结构

### string（字符串）

类似于 Java `ArrayList` 的动态字符数组，采用预分配冗余空间的方式来减少内存的频繁分配。当长度小于 1M 时，扩容都是加倍现有空间，超过 1MB 后，扩容只会一次多扩 1MB 的空间。字符串最大长度是 512 M。

**常用命令**

```
set, get, exists, get
mset, mget
setex
setnx
incr, incrby
```

### list（列表）

类似于 Java `LinkedList` 的双向链表，插入和删除的复杂度为 `O(1)`，索引定位的复杂度为 `O(n)`。列表支持左进左出以及右进右出操作，因此可以用列表实现队列和栈。

列表的底层数据结构不是一个简单的双向链表，而是 quicklist，quicklist 的每一个节点仍然是双向链接的，但是每一个节点不是一个简单元素，而是一个 ziplist，ziplist 内存储多个内存地址连续分配的元素。使用多个 ziplist 双向链接组成一个 quicklist，这样既节省了内存空间（一个双向链表的每一个节点都需要额外的两个指针），又缓解了内存碎片化问题（链表的每一个节点在内存上是不连续的）。quicklist 相当于是时间和空间权衡的结果。

![列表数据结构](https://youdu-markdown.oss-cn-shanghai.aliyuncs.com/20191126144421.png)

**常用命令**

```
rpush, lpush, rpop, lpop
llen, lindex, lrange, ltrim
```

### hash（字典）

类似于 Java `HashMap` 的无序字典结构，实现上是一个“数组 + 链表”的二维结构，将 hash 碰撞的元素用链表串起来。与 Java 不同的是，为了不阻塞服务，Redis 的字典在扩容时采用渐进式的方式，会先保留新旧两个字典，在定时任务中渐进式地将数据从旧字典迁移到新字典。因为新旧字典同时存在，因此在查询时会同时查询两个字典。

**常用命令**

```
hset, hget, hgetall
hmset, hmget
hincrby
```

### set（集合）

类似于 Java `HashSet` 的无序唯一集合结构，相当于一个元素都是 `NULL` 的特殊的字典。

**常用命令**

```
sadd, spop
smembers, sismember, scard
```

### zset（有序集合）

类似于 Java `ConcurrentSkipListSet` 的有序的数据结构，一方面它是一个保证内部元素唯一性的集合，另一方面它可以给每一个元素赋予一个分数（score），内部维护了一个按元素的分数排序的有序的数据结构。

Redis 有序集合底层是用“跳跃列表（Skip List）”实现的。因为有序集合要支持随机的插入和删除，因此不适合用数组来实现，如果采用有序链表来实现，那么在插入新数据时需要对链表做遍历，时间复杂度不能让人接受。跳跃链表也是一个链表结构，每隔一定数量的链表节点就会挑选出一个节点作为“代表”，将这些代表再链接起来，在这些代表中同样每隔一段距离再次挑选出“代表的代表”，将这些代表的代表再链接起来，以此类推，最终形成一个层级结构。定位一个跳跃链表中的元素时，需要从顶层代表开始定位，一直遍历到最底层的链表，例如如果要在地图上定位一个地点，我们往往会采取从大洲到国家到省份到城市到街道这样的顺序一层一层定位。

![跳跃列表数据结构](https://youdu-markdown.oss-cn-shanghai.aliyuncs.com/20191126151249.png)

**常用命令**

```
zadd, zrem
zrange, zrevrange, zrangebyscore, zrevrangebyscore
zcard
zscore, zrank
```

### 容器型数据结构的通用规则

1. create if not exists：指定 key 的容器如果不存在就自动创建一个
2. drop if no elements：容器里的最后一个元素被删除后，立即删除容器，释放内存

### 过期时间

Redis 所有数据结构都可以设置过期时间，过期时间是以对象为单位的，例如 hash 结构的过期是整个 hash 对象，而不是其中的某个子 key。调用 set 修改字符串后，字符串的过期时间会消失。

**常用命令**

```
expire, ttl
```

## 用 Redis 实现分布式锁

### 单实例 `setnx` 方案

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

### 可重入锁

Java 中的 `synchronized` 锁和 `ReentrantLock` 都是可重入锁，可重入锁是指持有锁的线程请求锁时仍能成功的锁。实现原理基本是维护一个进入次数的变量，同一个线程获取时加一，释放时减一，只有这个值被减为 0 才认为锁被释放了。使用上一节的方法实现的分布式锁如果要支持可重入性，并不需要 Redis 再额外维护什么数据，因为可重入的永远是相同的线程，因此只需要在 `ThreadLocal` 中维护一个锁的 key 值和重入次数的字典，只有第一次 lock 时真正执行 `setnx`，重入时计数加一，释放锁时计数减一，在计数为 0 时才真正释放分布式锁。

具体的 Java 实现可以参考 [ReentrantRedisLock](<https://github.com/yiwanzhutourou/learning/blob/master/redis-basics/src/main/java/com/youdushufang/lock/ReentrantRedisLock.java>)。

当然，这样实现的可重入锁仍然有超时问题，一旦第一次申请的锁已经超时，就不应该让同一个线程再次获取这个锁了，因此 `ThreadLocal` 中维护的计数可能也需要引入超时机制。因为可重入锁在使用时完全可以通过逻辑来规避，且实现成本较大，一般情况下不推荐在业务中使用。

### Redlock 算法

前面讨论的分布式锁方案在集群环境下是有问题的。例如在 Sentinel 集群中，当主节点挂掉而客户端在主节点上申请的锁还没有同步到从节点，此时从节点变成了新的主节点，另一个客户端申请加锁是可以成功的，这就造成了同一把锁被两个客户端持有的情况。为了解决这个问题，Antirez 发明了 Redlock 算法，这个算法的基本原理是在存在 `N` 个实例的集群中，一个客户端只有在超过绝对多数（`N / 2 + 1`）个实例上获取到锁（使用 `setnex`）后才认为这个客户端成功地获取到了锁，如果一个客户端成功获取到了锁，则意味着不再可能有另外一个客户端同时获取到相同的锁，因为已经有绝对多数的实例被其锁定了。当然这个算法还要考虑时钟漂移、失败重试等问题，具体可以参考 [Distributed locks with Redis](<https://redis.io/topics/distlock>)。

Java redis 客户端中，redisson 实现了 RedLock，redisson 还实现了读写锁、信号量等高级同步工具，具体参见 [redisson 文档](<https://github.com/redisson/redisson/wiki/8.-distributed-locks-and-synchronizers#84-redlock>)。

使用 Redlock 算法的分布式锁因为要在集群中的所有实例上申请锁，因此性能相对于单实例版的分布式锁肯定是要低一些的，使用时需要考虑是否真的需要保证绝对的高可用性。

## 思考

### 平衡树（Balanced Tree） vs 跳跃链表（Skip List）

Java 中的 `TreeMap` 底层其实是一个红黑树，那么为什么 Java 中没有实现一个线程安全的 `ConcurrentTreeMap` 呢？是因为红黑树在插入或者删除元素时，经常要涉及到节点的旋转，这会牵涉到大量的节点，因此线程安全版的红黑树在插入时会涉及到大量节点的锁定，使得插入操作的效率不高。相对而言，跳跃链表在插入时，并不会涉及到很多节点的变动，因此在多线程的情况下性能更好。Java 中线程安全的跳跃链表实现可以查看 `ConcurrentSkipListMap` 类。跳跃链表数据结构参见[维基百科](<https://en.wikipedia.org/wiki/Skip_list>)。跳跃链表相对于各类平衡树的优势之一就是插入和删除操作效率更高。

不过话又说回来了，Redis 为什么不用平衡树（红黑树、B 树）实现有序集合呢？可以从跳跃链表和平衡树如何实现 `zrange`、`zrank` 等命令、它们支持插入、删除、遍历操作的时间复杂度以及内存占用情况等角度考虑这个问题。

## 参考资料

1. [《Redis 深度历险》](<https://book.douban.com/subject/30386804/>)，电子工业出版社 2019 年 1 月第 1 版

2. [Distributed locks with Redis](<https://redis.io/topics/distlock>)