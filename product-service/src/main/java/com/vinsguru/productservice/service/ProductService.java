package com.vinsguru.productservice.service;

import com.vinsguru.productservice.dto.ProductDto;
import com.vinsguru.productservice.repository.ProductRepository;
import com.vinsguru.productservice.util.EntityDtoUtil;
import org.springframework.data.domain.Range;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    private final Sinks.Many<ProductDto> sink;

    public ProductService(ProductRepository productRepository, Sinks.Many<ProductDto> sink) {
        this.productRepository = productRepository;
        this.sink = sink;
    }

    public Flux<ProductDto> getAll() {
        return this.productRepository.findAll().map(EntityDtoUtil::toDto);
    }

    public Mono<ProductDto> getProductById(String id) {
        return productRepository.findById(id).map(EntityDtoUtil::toDto);
    }

    public Mono<ProductDto> insertProduct(Mono<ProductDto> productDtoMono) {
        return productDtoMono
                .map(EntityDtoUtil::toEntity)
                .flatMap(productRepository::insert)
                .map(EntityDtoUtil::toDto)
                .doOnNext(sink::tryEmitNext);
    }

    public Mono<ProductDto> updateProduct(String id, Mono<ProductDto> productDtoMono) {
        return productRepository.findById(id)
                .flatMap(p -> productDtoMono
                        .map(EntityDtoUtil::toEntity)
                        .doOnNext(e -> e.setId(id)))
                .flatMap(productRepository::save)
                .map(EntityDtoUtil::toDto);
    }

    public Mono<Void> deleteProduct(String id) {
        return productRepository.deleteById(id);
    }

    public Flux<ProductDto> getProductByPriceRange(Double min, Double max) {
        return this.productRepository
                .findByPriceBetween(Range.closed(min, max))
                .map(EntityDtoUtil::toDto);
    }
}
