package com.tecser.backend.dto.request;

import lombok.Data;

@Data
public class ProductRequestDTO {
    public enum ProductStatus {
        ACTIVO,
        INACTIVO,
        AGOTADO
    }

    private String name;
    private Double price;
    private String description;
    private Integer stock;
    private ProductStatus status;

}
