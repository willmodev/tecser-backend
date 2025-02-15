package com.tecser.backend.mapper;

import com.tecser.backend.dto.request.ProductRequestDTO;
import com.tecser.backend.dto.response.ProductResponseDTO;
import com.tecser.backend.model.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toEntity(ProductRequestDTO productRequestDTO);
    ProductResponseDTO toResponseDto(Product entity);
    List<ProductResponseDTO> toRequestDtoList(List<Product> entityList);
}
