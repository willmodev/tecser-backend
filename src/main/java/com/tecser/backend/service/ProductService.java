package com.tecser.backend.service;

import com.tecser.backend.dto.request.ProductRequestDTO;
import com.tecser.backend.dto.response.ProductResponseDTO;
import com.tecser.backend.mapper.ProductMapper;
import com.tecser.backend.model.Product;
import com.tecser.backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import com.tecser.backend.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;

    public ProductService(ProductRepository repository, @Qualifier("productMapperImpl") ProductMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public List<ProductResponseDTO> findAll() {
        log.debug("Buscando todos los productos");
        return mapper.toRequestDtoList(repository.findAll());
    }

    @Transactional(readOnly = true)
    public ProductResponseDTO findById(Long id) {
        log.debug("Buscando producto por ID: {}", id);
        return repository.findById(id)
                .map(mapper::toResponseDto)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + id));
    }

    @Transactional
    public ProductResponseDTO save(ProductRequestDTO productRequestDTO) {
        log.debug("Guardando nuevo producto: {}", productRequestDTO);

        Product product = mapper.toEntity(productRequestDTO);
        Product savedProduct = repository.save(product);
        log.info("Producto guardado exitosamente con ID: {}", savedProduct.getId());
        return mapper.toResponseDto(savedProduct);
    }

    @Transactional
    public void delete(Long id) {
        log.debug("Eliminando producto con ID: {}", id);

        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Producto no encontrado con ID: " + id);
        }

        repository.deleteById(id);
        log.info("Producto eliminado exitosamente con ID: {}", id);
    }

    @Transactional
    public ProductResponseDTO update(ProductRequestDTO productRequestDTO, Long id) {
        log.debug("Actualizando producto con ID: {}", id);

        Product product = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + id));

        mapper.updateEntityFromRequestDto(productRequestDTO, product);
        Product updatedProduct = repository.save(product);
        log.info("Producto actualizado exitosamente con ID: {}", id);
        return mapper.toResponseDto(updatedProduct);
    }


}