package com.tecser.backend.mapper;

import com.tecser.backend.dto.request.SellerRequestDTO;
import com.tecser.backend.dto.response.SellerResponseDTO;
import com.tecser.backend.model.Seller;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


import java.util.List;

@Mapper(componentModel = "spring")
public interface SellerMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isActive", defaultValue = "true")
    @Mapping(target = "registrationDate", expression = "java(java.time.LocalDateTime.now())")
    Seller toEntity(SellerRequestDTO sellerRequestDTO);

    SellerResponseDTO toResponseDto(Seller entity);

    List<SellerResponseDTO> toRequestDtoList(List<Seller> entityList);

    void updateEntityFromRequestDto(SellerRequestDTO sellerRequestDTO, @MappingTarget Seller seller);

}
