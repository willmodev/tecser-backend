package com.tecser.backend.dto.response;

import lombok.Data;
import lombok.Builder;

import java.math.BigDecimal;

@Data
@Builder
public class ProductResponseDTO {
    public enum ProductStatus {
        ACTIVO,
        INACTIVO,
        AGOTADO
    }

    private Long id;
    private String name;
    private BigDecimal price;
    private String description;
    private Integer stock;
    private ProductStatus status;;
}