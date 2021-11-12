package com.vinsguru.webfluxdemo;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

@SpringBootTest(webEnvironment = DEFINED_PORT, classes = WebfluxDemoApplication.class)
@ExtendWith(SpringExtension.class)
class BaseTest {
    protected final WebClient webClient;

    BaseTest() {
        this.webClient = WebClient.builder()
                .baseUrl("http://localhost:8080")
                //.defaultHeaders(h -> h.setBasicAuth("username", "password"))
                .filter(this::sessionToken)
                .build();
    }

    private Mono<ClientResponse> sessionToken(ClientRequest request, ExchangeFunction ex) {
        final ClientRequest clientRequest = request.attribute("auth")
                .map(v -> v.equals("basic") ? withBasicAuth(request) : withOAuth(request))
                .orElse(request);
        return ex.exchange(clientRequest);
    }

    private ClientRequest withBasicAuth(ClientRequest request) {
        return ClientRequest.from(request).headers(h -> h.setBasicAuth("username", "Password")).build();
    }

    private ClientRequest withOAuth(ClientRequest request) {
        return ClientRequest.from(request).headers(h -> h.setBearerAuth("some-token")).build();
    }
}
