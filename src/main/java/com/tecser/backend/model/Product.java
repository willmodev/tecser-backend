package com.tecser.backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    public enum ProductStatus {
        ACTIVO,
        INACTIVO,
        AGOTADO
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private Double price;
    private Integer stock;
    @Enumerated(EnumType.STRING)
    private ProductStatus status;

}
