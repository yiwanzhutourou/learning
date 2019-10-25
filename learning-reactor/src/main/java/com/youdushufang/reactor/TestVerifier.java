package com.youdushufang.reactor;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

class TestVerifier {

    void test() {

        StepVerifier.create(Flux.just("a", "b"))
                .expectNext("a")
                .expectNext("b")
                .verifyComplete();

        // 创建使用虚拟时钟的 StepVerifier
        StepVerifier.withVirtualTime(() -> Flux.interval(Duration.ofHours(4), Duration.ofDays(1)).take(2))
                .expectSubscription()
                .expectNoEvent(Duration.ofHours(4))
                .expectNext(0L)
                .thenAwait(Duration.ofDays(1))
                .expectNext(1L)
                .verifyComplete();

        final reactor.test.publisher.TestPublisher<String> testPublisher
                = reactor.test.publisher.TestPublisher.create();
        testPublisher.next("a");
        testPublisher.next("b");
        testPublisher.complete();

        StepVerifier.create(testPublisher)
                .expectNext("a")
                .expectNext("b")
                .expectComplete();
    }
}
