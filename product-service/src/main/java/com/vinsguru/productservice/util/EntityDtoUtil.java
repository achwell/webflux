package com.vinsguru.productservice.util;

import com.vinsguru.productservice.dto.ProductDto;
import com.vinsguru.productservice.entity.Product;
import org.springframework.beans.BeanUtils;

public class EntityDtoUtil {

    public static ProductDto toDto(Product product) {
        final ProductDto dto = new ProductDto();
        BeanUtils.copyProperties(product, dto);
        return dto;
    }

    public static Product toEntity(ProductDto dto) {
        final Product product = new Product();
        BeanUtils.copyProperties(dto, product);
        return product;
    }
}
