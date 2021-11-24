package com.vinsguru.productservice.service;

import com.vinsguru.productservice.dto.ProductDto;
import com.vinsguru.productservice.repository.ProductRepository;
import com.vinsguru.productservice.util.EntityDtoUtil;
import org.springframework.data.domain.Range;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
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
                .map(EntityDtoUtil::toDto);
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
