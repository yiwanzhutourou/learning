# Redis 基础

## 内容

- 基础（[An introduction to Redis data types and abstractions](https://redis.io/topics/data-types-intro)）
  - 5 种基础数据结构
  - Bitmap
  - HyperLogLog
  - GeoHash
  - Stream
- 应用
  - 分布式锁（[Distributed locks with Redis](https://redis.io/topics/distlock)）
  - 消息队列
  - 布隆过滤器
  - 限流
- 原理
  - Redis 线程、I/O 模型
  - 通信协议
  - 管道（[Using pipelining to speedup Redis queries](https://redis.io/topics/pipelining)）
  - 持久化
  - 事务
  - 主从同步、哨兵模式、集群模式
  - 过期策略（[expire](https://redis.io/commands/expire)）
  - LRU / LFU 淘汰策略
- 数据结构（sds, dict, ziplist, quicklist, skiplist）

## 笔记

- [5 种基础数据结构](https://github.com/yiwanzhutourou/learning/tree/master/redis-basics/notes/5%20种基础数据结构.md)
- [用 Redis 实现分布式锁](https://github.com/yiwanzhutourou/learning/tree/master/redis-basics/notes/用%20Redis%20实现分布式锁.md)

## 参考资料

1. [《Redis 深度历险》](<https://book.douban.com/subject/30386804/>)，电子工业出版社 2019 年 1 月第 1 版
2. [redis.io](https://redis.io/)