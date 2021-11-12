package com.vinsguru.webfluxdemo;

import com.vinsguru.webfluxdemo.dto.InputFailedValidationResponse;
import com.vinsguru.webfluxdemo.dto.Response;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClientResponseException.BadRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lec06ExchangeTest extends BaseTest {

    @Test
    public void badRequestTest() {
        Mono<Object> response = webClient
                .get()
                .uri("reactive-math/square/{input}/throw", 5)
                .exchangeToMono(this::exchange)
                .doOnNext(System.out::println)
                .doOnError(err -> System.out.println(err.getMessage()));

        StepVerifier
                .create(response)
                .expectNextCount(1)
                .verifyComplete()
        ;
    }

    private Mono<Object> exchange(ClientResponse cr) {
        return cr.rawStatusCode() == 400 ? cr.bodyToMono(InputFailedValidationResponse.class) : cr.bodyToMono(Response.class);
    }

}
