package com.youdushufang.reactor;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

class TestSubscriber {

    void test() {
        testSubscribe();
    }

    private void testSubscribe() {
        Flux.just(1, 2)
                .concatWith(Mono.error(new IllegalStateException()))
                .subscribe(System.out::println, System.err::println);

        // 出现错误时返回默认值
        Flux.just(1, 2)
                .concatWith(Mono.error(new IllegalStateException()))
                .onErrorReturn(0)
                .subscribe(System.out::println);

        // 出现错误时使用另外的流
        Flux.just(1, 2)
                .concatWith(Mono.error(new IllegalStateException()))
                .switchOnError(Mono.just(0))
                .subscribe(System.out::println);

        // 出现错误时根据异常类型来选择流
        Flux.just(1, 2)
                .concatWith(Mono.error(new IllegalArgumentException()))
                .onErrorResumeWith(e -> {
                    if (e instanceof IllegalStateException) {
                        return Mono.just(0);
                    } else if (e instanceof IllegalArgumentException) {
                        return Mono.just(-1);
                    }
                    return Mono.empty();
                })
                .subscribe(System.out::println);

        // 当出现错误时，还可以通过 retry 操作符来进行重试
        // 重试的动作是通过重新订阅序列来实现的
        // 在使用 retry 操作符时可以指定重试的次数
        Flux.just(1, 2)
                .concatWith(Mono.error(new IllegalStateException()))
                .retry(1)
                .subscribe(System.out::println);
    }
}
