package com.tecser.backend.dto.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class SaleResponseDTO {
    private Long id;
    private LocalDateTime date;
    private List<ProductResponseDTO> products;
    private SellerResponseDTO seller;
}

