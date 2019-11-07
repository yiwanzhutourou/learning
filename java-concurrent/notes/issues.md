## 基础

- 可见性、原子性、有序性
- Java 内存模型（volatile 关键字，先行发生原则）
- synchronize、wait、notify / notifyAll

- 管程（Monitor）是什么？解决的是什么问题？
- Java 线程（线程的生命周期，与操作系统线程的关系）
- CAS 是什么？
- Java 并发工具包（原理是什么？适用场景是什么？限制条件是什么？）

  - 锁相关

    - Lock, Condition
    - Semaphore
    - ReadWriteLock 和 StampedLock
    - CountDownLatch 和 CyclicBarrier
  - 原子类（Atomic~）
  - 线程安全的容器

    - Concurrent~ (ConcurrentHashMap, ConcurrentSkipListMap, ConcurrentLinkedQueue, ConcurrentLinkedDeque)
    - CopyOnWrite~ (CopyOnWriteArrayList , CopyOnWriteArrayList)
    - BlockingQueue 和 BlockingDeque (SynchronousQueue, ArrayBlockingQueue, LinkedBlockingQueue, LinkedBlockingDeque...)
  - 线程池

    - Executor, Executors

    - ForkJoinPool
  - Future, CompletableFuture, CompletionService
- 并发设计模式
  - 利用不变性解决并发问题（String、Integer 等 Java 类）
  - Copy on Write
  - 线程本地存储（ThreadLocal）

## 扩展

- synchronize 实现原理

- Java 并发工具包源码