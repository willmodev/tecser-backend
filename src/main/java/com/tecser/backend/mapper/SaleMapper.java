package com.tecser.backend.mapper;

import com.tecser.backend.dto.request.SaleRequestDTO;
import com.tecser.backend.dto.response.SaleResponseDTO;
import com.tecser.backend.model.Sale;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ProductMapper.class, SellerMapper.class})
public interface SaleMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "date", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "products", ignore = true)
    @Mapping(target = "seller", ignore = true)
    @Mapping(source = "saleNumber", target = "saleNumber")
    @Mapping(source = "totalAmount", target = "totalAmount")
    @Mapping(source = "comments", target = "comments")
    Sale toEntity(SaleRequestDTO saleRequestDTO);

    @Mapping(source = "products", target = "products")
    @Mapping(source = "seller", target = "seller")
    @Mapping(source = "saleNumber", target = "saleNumber")
    @Mapping(source = "totalAmount", target = "totalAmount")
    @Mapping(source = "comments", target = "comments")
    SaleResponseDTO toResponseDto(Sale sale);
}