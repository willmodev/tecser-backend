package com.tecser.backend.dto.response;

import com.tecser.backend.dto.request.ProductRequestDTO;
import lombok.Data;

@Data
public class ProductResponseDTO {

    public enum ProductStatus {
        ACTIVO,
        INACTIVO,
        AGOTADO
    }

    private Long id;
    private String name;
    private Double price;
    private String description;
    private Integer stock;
    private ProductStatus status;
}
