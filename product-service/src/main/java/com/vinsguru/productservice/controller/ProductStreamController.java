package com.vinsguru.productservice.controller;

import com.vinsguru.productservice.dto.ProductDto;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import static org.springframework.http.MediaType.TEXT_EVENT_STREAM_VALUE;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("product")
public class ProductStreamController {

    private final Flux<ProductDto> flux;

    public ProductStreamController(Flux<ProductDto> flux) {
        this.flux = flux;
    }

    @GetMapping(value = "stream/{maxPrice}", produces = TEXT_EVENT_STREAM_VALUE)
    public Flux<ProductDto> getProductUpdates(@PathVariable Double maxPrice) {
        return flux.filter(dto -> dto.getPrice() <= maxPrice);
    }
}
