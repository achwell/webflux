package com.vinsguru.webfluxdemo;

import com.vinsguru.webfluxdemo.dto.Response;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClientResponseException.BadRequest;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class Lec05BadRequestTest extends BaseTest {

    @Test
    public void badRequestTest() {
        Flux<Response> response = webClient
                .get()
                .uri("reactive-math/square/{input}/throw", 5)
                .retrieve()
                .bodyToFlux(Response.class)
                .doOnNext(System.out::println)
                .doOnError(err -> System.out.println(err.getMessage()));

        StepVerifier
                .create(response)
                .verifyError(BadRequest.class);
    }

}
