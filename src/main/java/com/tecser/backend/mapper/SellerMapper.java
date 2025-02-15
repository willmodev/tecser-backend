package com.tecser.backend.mapper;


import com.tecser.backend.dto.request.SellerRequestDTO;
import com.tecser.backend.dto.response.SellerResponseDTO;
import com.tecser.backend.model.Seller;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SellerMapper {

    Seller toEntity(SellerRequestDTO sellerRequestDTO);
    SellerResponseDTO toResponseDto(Seller entity);
    List<SellerResponseDTO> toRequestDtoList(List<Seller> entityList);
}
