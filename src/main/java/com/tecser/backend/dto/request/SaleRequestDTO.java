package com.tecser.backend.dto.request;

import lombok.Data;

@Data
public class SaleRequestDTO {
    private Long productId;
    private Long sellerId;
}
