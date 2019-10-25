package com.youdushufang.reactor;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/**
 *
 * 通过调度器（Scheduler）可以指定操作执行的方式和所在的线程。有下面几种不同的调度器实现。
 *
 * 1. 当前线程，通过 Schedulers.immediate()方法来创建。
 * 2. 单一的可复用的线程，通过 Schedulers.single()方法来创建。
 * 3. 使用弹性的线程池，通过 Schedulers.elastic()方法来创建。
 *    线程池中的线程是可以复用的。当所需要时，新的线程会被创建。如果一个线程闲置太长时间，则会被销毁
 *    该调度器适用于 I/O 操作相关的流的处理。
 * 4. 使用对并行操作优化的线程池，通过 Schedulers.parallel()方法来创建。
 *    其中的线程数量取决于 CPU 的核的数量。该调度器适用于计算密集型的流的处理。
 * 5. 使用支持任务调度的调度器，通过 Schedulers.timer()方法来创建。
 * 6. 从已有的 ExecutorService 对象中创建调度器，通过 Schedulers.fromExecutorService()方法来创建。
 *
 */
class TestScheduler {

    void test() {
        Flux.create(sink -> {
            // 发送当前线程的名字后结束
            sink.next(Thread.currentThread().getName());
            sink.complete();
        })
                // publishOn() 方法切换的是操作符的执行方式
                .publishOn(Schedulers.single())
                // 线程切换了，利用 map 命令增加当先线程的名字（[single] parallel）
                .map(x -> String.format("[%s] %s", Thread.currentThread().getName(), x))
                .publishOn(Schedulers.elastic())
                // 线程切换了，利用 map 命令增加当先线程的名字（[elastic] [single] parallel）
                .map(x -> String.format("[%s] %s", Thread.currentThread().getName(), x))
                // subscribeOn() 方法切换的是产生流中元素时的执行方式
                .subscribeOn(Schedulers.parallel())
                // 序列的生成是异步的，而转换成 Stream 对象可以保证主线程
                // 在序列生成完成之前不会退出，从而可以正确地输出序列中的所有元素
                .toStream()
                .forEach(System.out::println);
    }
}
