package com.tecser.backend.mapper;

import com.tecser.backend.dto.request.SaleRequestDTO;
import com.tecser.backend.dto.response.SaleResponseDTO;
import com.tecser.backend.model.Sale;
import com.tecser.backend.model.SaleDetail;
import org.mapstruct.*;

import java.math.BigDecimal;

@Mapper(
        componentModel = "spring",
        uses = {
                ProductMapper.class,
                SellerMapper.class,
                SaleDetailMapper.class
        }
)
public interface SaleMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "saleDate", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "seller", ignore = true)
    @Mapping(target = "saleDetails", ignore = true)
    Sale toEntity(SaleRequestDTO saleRequestDTO);

    SaleResponseDTO toResponseDto(Sale sale);

    @Named("toSaleWithoutDetails")
    @Mapping(target = "saleDetails", ignore = true)
    SaleResponseDTO toResponseDtoWithoutDetails(Sale sale);

    @AfterMapping
    default void validateTotalAmount(@MappingTarget Sale sale) {
        if (sale.getSaleDetails() != null && !sale.getSaleDetails().isEmpty()) {
            BigDecimal calculatedTotal = sale.getSaleDetails().stream()
                    .map(SaleDetail::getSubtotal)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            sale.setTotalAmount(calculatedTotal);
        }
    }
}