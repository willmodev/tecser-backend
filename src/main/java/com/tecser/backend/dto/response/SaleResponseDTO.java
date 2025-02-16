package com.tecser.backend.dto.response;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class SaleResponseDTO {
    private Long id;
    private String saleNumber;
    private LocalDateTime date;
    private BigDecimal totalAmount;
    private String comments;
    private List<ProductResponseDTO> products;
    private SellerResponseDTO seller;
}