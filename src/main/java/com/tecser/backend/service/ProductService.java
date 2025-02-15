package com.tecser.backend.service;

import com.tecser.backend.dto.request.ProductRequestDTO;
import com.tecser.backend.dto.response.ProductResponseDTO;
import com.tecser.backend.mapper.ProductMapper;
import com.tecser.backend.model.Product;
import com.tecser.backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;

    public ProductService(ProductRepository repository, @Qualifier("productMapperImpl") ProductMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<ProductResponseDTO> findAll() {
        return mapper.toRequestDtoList(this.repository.findAll());
    }

    public Optional<ProductResponseDTO> findById(Long id) {
        return  repository.findById(id).map(mapper::toResponseDto);
    }

    public ProductResponseDTO save(ProductRequestDTO productRequestDTO) {
        Product product = mapper.toEntity(productRequestDTO);
        return mapper.toResponseDto(repository.save(product));
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    // Update Method
    public ProductResponseDTO update(ProductRequestDTO productRequestDTO) {
        Product product = mapper.toEntity(productRequestDTO);
        return mapper.toResponseDto(repository.save(product));
    }
}
