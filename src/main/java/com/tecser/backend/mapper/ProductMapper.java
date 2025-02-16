package com.tecser.backend.mapper;

import com.tecser.backend.dto.request.ProductRequestDTO;
import com.tecser.backend.dto.response.ProductResponseDTO;
import com.tecser.backend.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toEntity(ProductRequestDTO productRequestDTO);
    ProductResponseDTO toResponseDto(Product entity);
    void updateEntityFromRequestDto(ProductRequestDTO productRequestDTO, @MappingTarget Product product);
    List<ProductResponseDTO> toRequestDtoList(List<Product> entityList);
}
