package com.youdushufang.webflux;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class HelloController {

    @GetMapping("hello")
    public Mono<String> sayHello(@RequestParam(defaultValue = "World") String name) {
        return Mono.just("Hello, " + name);
    }
}
