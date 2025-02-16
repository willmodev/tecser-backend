package com.tecser.backend.mapper;

import com.tecser.backend.dto.request.SaleRequestDTO;
import com.tecser.backend.dto.response.SaleResponseDTO;
import com.tecser.backend.model.Sale;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

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
    @Mapping(target = "date", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "products", ignore = true)
    @Mapping(target = "seller", ignore = true)
    @Mapping(target = "saleDetails", ignore = true) // Ignorar por ahora, se manejará en el servicio
    @Mapping(source = "saleNumber", target = "saleNumber")
    @Mapping(source = "totalAmount", target = "totalAmount")
    @Mapping(source = "comments", target = "comments")
    Sale toEntity(SaleRequestDTO saleRequestDTO);

    @Mapping(source = "products", target = "products")
    @Mapping(source = "seller", target = "seller")
    @Mapping(source = "saleNumber", target = "saleNumber")
    @Mapping(source = "totalAmount", target = "totalAmount")
    @Mapping(source = "comments", target = "comments")
    @Mapping(source = "saleDetails", target = "saleDetails") // Remover qualifiedByName
    SaleResponseDTO toResponseDto(Sale sale);

    // Este método está bien, no requiere cambios
    @Named("toSaleWithoutDetails")
    @Mapping(target = "saleDetails", ignore = true)
    @Mapping(source = "products", target = "products")
    @Mapping(source = "seller", target = "seller")
    @Mapping(source = "saleNumber", target = "saleNumber")
    @Mapping(source = "totalAmount", target = "totalAmount")
    @Mapping(source = "comments", target = "comments")
    SaleResponseDTO toResponseDtoWithoutDetails(Sale sale);
}