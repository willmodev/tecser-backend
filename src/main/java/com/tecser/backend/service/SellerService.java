package com.tecser.backend.service;


import com.tecser.backend.dto.request.SellerRequestDTO;
import com.tecser.backend.dto.response.SellerResponseDTO;
import com.tecser.backend.mapper.SellerMapper;
import com.tecser.backend.model.Seller;
import com.tecser.backend.repository.SellerRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SellerService {
    private final SellerRepository repository;
    private final SellerMapper mapper;
    public SellerService(SellerRepository repository, @Qualifier("sellerMapperImpl") SellerMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<SellerResponseDTO> findAll() {
        return mapper.toRequestDtoList(repository.findAll());
    }

    public SellerResponseDTO save(SellerRequestDTO sellerRequestDTO) {
        Seller seller = mapper.toEntity(sellerRequestDTO);
        System.out.println(seller);

        return mapper.toResponseDto(repository.save(seller));
    }

    public Optional<SellerResponseDTO> findById(Long id) {
        return repository.findById(id).map(mapper::toResponseDto);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Transactional
    public SellerResponseDTO update(SellerRequestDTO sellerRequestDTO, Long id) {
        Seller seller = this.repository.findById(id).orElse(null);

        if (seller == null)  return null;

        this.mapper.updateEntityFromRequestDto(sellerRequestDTO, seller);
        return this.mapper.toResponseDto(this.repository.save(seller));
    }

}
