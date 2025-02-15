package com.tecser.backend.mapper;



import com.tecser.backend.dto.request.SaleRequestDTO;
import com.tecser.backend.model.Sale;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SaleMapper {

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "seller.id", target = "sellerId")
    SaleRequestDTO toDto(Sale sale);

    @Mapping(source = "productId", target = "product.id")
    @Mapping(source = "sellerId", target = "seller.id")
    Sale toEntity(SaleRequestDTO saleRequestDTO);
}