package com.tecser.backend.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class SaleRequestDTO {
    private List<Long> productIds;
    private Long sellerId;
}
