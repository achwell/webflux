package com.vinsguru.productservice.service;

import com.vinsguru.productservice.dto.ProductDto;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class DataSetupService implements CommandLineRunner {

    private final ProductService productService;

    public DataSetupService(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public void run(String... args) throws Exception {
        ProductDto p1 = new ProductDto("4k-tv", 1000D);
        ProductDto p2 = new ProductDto("slr-camera", 750D);
        ProductDto p3 = new ProductDto("iphone", 800D);
        ProductDto p4 = new ProductDto("headphone", 100D);

        Flux.just(p1, p2, p3, p4)
                //.concatWith(newProducts())
                .flatMap(p -> this.productService.insertProduct(Mono.just(p)))
                .subscribe(System.out::println);

    }

    private Flux<ProductDto> newProducts(){
        return Flux.range(1, 1000)
                .delayElements(Duration.ofSeconds(2))
                .map(i -> new ProductDto("product-" + i, ThreadLocalRandom.current().nextDouble(10, 100)));
    }

}
