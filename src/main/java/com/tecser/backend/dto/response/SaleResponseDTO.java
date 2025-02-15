package com.tecser.backend.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SaleResponseDTO {

    private Long id;
    private LocalDateTime date;
    private ProductResponseDTO product;
    private SellerResponseDTO seller;
}
