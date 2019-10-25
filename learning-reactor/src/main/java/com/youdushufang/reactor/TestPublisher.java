package com.youdushufang.reactor;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.Random;

class TestPublisher {

    void test() {
//        testMerge();
//        testFlatMap();
//        testConcatMap();
//        testCombineLatest();
//        testReduce();
//        testTake();
//        testFilter();
//        testBuffer();
//        testWindow();
//        testZipWith();
        testFlux();
        testMono();
    }

    // merge 和 mergeSequential 操作符用来把多个流合并成一个 Flux 序列
    // merge 按照所有流中元素的实际产生顺序来合并
    // mergeSequential 则按照所有流被订阅的顺序，以流为单位进行合并
    private void testMerge() {
        Flux.merge(Flux.intervalMillis(0, 100).take(5),
                Flux.intervalMillis(50, 100).take(5))
                .toStream().forEach(System.out::println);

        Flux.mergeSequential(Flux.intervalMillis(0, 100).take(5),
                Flux.intervalMillis(50, 100).take(5))
                .toStream().forEach(System.out::println);
    }

    // flatMap 和 flatMapSequential 操作符把流中的每个元素转换成一个流，再把所有流中的元素进行合并
    // flatMapSequential 和 flatMap 之间的区别与 mergeSequential 和 merge 之间的区别是一样的
    private void testFlatMap() {
        Flux.just(5, 10)
                .flatMap(x -> Flux.intervalMillis(x * 10, 100).take(5))
                .toStream()
                .forEach(System.out::println);

        Flux.just(5, 10)
                .flatMapSequential(x -> Flux.intervalMillis(x * 10, 100).take(5))
                .toStream()
                .forEach(System.out::println);
    }

    // concatMap 操作符的作用也是把流中的每个元素转换成一个流，再把所有流进行合并
    // 与 flatMap 不同的是，concatMap 会根据原始流中的元素顺序依次把转换之后的流进行合并
    // 与 flatMapSequential 不同的是，concatMap 对转换之后的流的订阅是动态进行的，而 flatMapSequential 在合并之前就已经订阅了所有的流
    private void testConcatMap() {
        Flux.just(5, 10)
                .concatMap(x -> Flux.intervalMillis(x * 10, 100).take(5))
                .toStream()
                .forEach(System.out::println);
    }

    // combineLatest 操作符把所有流中的最新产生的元素合并成一个新的元素，作为返回结果流中的元素
    // 只要其中任何一个流中产生了新的元素，合并操作就会被执行一次，结果流中就会产生新的元素
    private void testCombineLatest() {
        Flux.combineLatest(
                Arrays::toString,
                Flux.intervalMillis(100).take(5),
                Flux.intervalMillis(50, 100).take(5)
        ).toStream().forEach(System.out::println);
    }

    // reduce 和 reduceWith 操作符对流中包含的所有元素进行累积操作，得到一个包含计算结果的 Mono 序列
    private void testReduce() {
        Flux.range(1, 100)
                .reduce(Integer::sum)
                .subscribe(System.out::println);

        Flux.range(1, 100)
                .reduceWith(() -> 100, Integer::sum)
                .subscribe(System.out::println);
    }

    // take 系列操作符用来从当前流中提取元素
    private void testTake() {
        Flux.range(0, 100)
                .take(3)
                .subscribe(System.out::println);

        Flux.range(0, 100)
                .takeUntil(i -> i == 2)
                .subscribe(System.out::println);

        Flux.range(0, 100)
                .takeWhile(i -> i < 3)
                .subscribe(System.out::println);
    }

    private void testFilter() {
        Flux.range(1, 10)
                .filter(i -> i % 2 == 0)
                .subscribe(System.out::println);
    }

    // buffer 操作符的作用是把当前流中的元素收集到集合中，并把集合对象作为流中的新元素
    private void testBuffer() {
        Flux.range(1, 100).buffer(20)
                .subscribe(System.out::println);
        Flux.range(1, 10).bufferUntil(i -> i % 2 == 0)
                .subscribe(System.out::println);
        Flux.range(1, 10).bufferWhile(i -> i % 2 == 0)
                .subscribe(System.out::println);

        Flux.interval(Duration.of(300, ChronoUnit.MILLIS))
                .bufferTimeoutMillis(100, 1000)
                .take(3)
                .toStream()
                .forEach(System.out::println);
    }

    // window 操作符的作用类似于 buffer，所不同的是 window 操作符是把当前
    // 流中的元素收集到另外的 Flux 序列中，因此返回值类型是 Flux<Flux<T>>
    private void testWindow() {
        Flux.range(1, 100)
                .window(20)
                .subscribe(flux -> flux.buffer(20).subscribe(System.out::println));
    }

    private void testZipWith() {
        Flux.just("a", "b")
                .zipWith(Flux.just("c", "d"))
                .subscribe(System.out::println);

        Flux.just("a", "b")
                .zipWith(Flux.just("c", "d"), (s1, s2) -> String.format("%s-%s", s1, s2))
                .subscribe(System.out::println);
    }

    // Flux 表示的是包含 0 到 N 个元素的异步序列
    // 在该序列中可以包含三种不同类型的消息通知：正常的包含元素的消息、序列结束的消息和序列出错的消息
    private void testFlux() {
        Flux.just("Hello", "World")
                .subscribe(System.out::println);

        Flux.fromArray(new Integer[] { 1, 2, 3 })
                .subscribe(System.out::println);

        Flux.interval(Duration.of(10, ChronoUnit.SECONDS))
                .subscribe(System.out::println);

        Flux.generate(sink -> {
            sink.next("Hello");
            sink.complete();
        }).subscribe(System.out::println);

        final Random random = new Random();
        Flux.generate(ArrayList::new, (list, sink) -> {
            int value = random.nextInt(100);
            list.add(value);
            sink.next(value);
            if (list.size() == 10) {
                sink.complete();
            }
            return list;
        }).subscribe(System.out::println);

        Flux.create(sink -> {
            for (int i = 0; i < 10; i++) {
                sink.next(i);
            }
            sink.complete();
        }).subscribe(System.out::println);
    }

    // Mono 表示的是包含 0 或者 1 个元素的异步序列
    // 该序列中同样可以包含与 Flux 相同的三种类型的消息通知
    private void testMono() {
        Mono.fromSupplier(() -> "Hello").subscribe(System.out::println);
        Mono.justOrEmpty(Optional.of("Hello")).subscribe(System.out::println);
        Mono.create(sink -> sink.success("Hello")).subscribe(System.out::println);
    }
}
