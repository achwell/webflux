package com.vinsguru.productservice.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ProductDto {

    private String id;
    private String description;
    private Double price;

    public ProductDto() {
    }

    public ProductDto(String description, Double price) {
        this.description = description;
        this.price = price;
    }
}
