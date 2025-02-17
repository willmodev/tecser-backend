package com.tecser.backend.mapper;

import com.tecser.backend.dto.request.SaleDetailRequestDTO;
import com.tecser.backend.dto.response.SaleDetailResponseDTO;
import com.tecser.backend.model.SaleDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface SaleDetailMapper {

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    @Mapping(source = "sale.id", target = "saleId")
    SaleDetailResponseDTO toDto(SaleDetail saleDetail);

    List<SaleDetailResponseDTO> toDtoList(List<SaleDetail> saleDetails);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "sale", ignore = true)
    @Mapping(source = "productId", target = "product.id")
    @Mapping(target = "subtotal", ignore = true)
    SaleDetail toEntity(SaleDetailRequestDTO dto);
    
    List<SaleDetail> toEntityList(List<SaleDetailRequestDTO> dtos);


}