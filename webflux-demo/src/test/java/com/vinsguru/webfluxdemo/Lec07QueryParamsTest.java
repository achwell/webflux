package com.vinsguru.webfluxdemo;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Map;

public class Lec07QueryParamsTest extends BaseTest {

    Map<String, Integer> map = Map.of(
            "count", 10,
            "page", 20
    );

    @Test
    public void queryParamsTest() {
        final Flux<Integer> integerFlux = webClient
                .get()
                .uri(b -> b.path("jobs/search").query("count={count}&page={page}").build(map))
                .retrieve()
                .bodyToFlux(Integer.class)
                .doOnNext(System.out::println);

        StepVerifier
                .create(integerFlux)
                .expectNextCount(2)
                .verifyComplete()
        ;
    }

}
