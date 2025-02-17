package com.tecser.backend.service;


import com.tecser.backend.dto.request.SellerRequestDTO;
import com.tecser.backend.dto.response.SellerResponseDTO;
import com.tecser.backend.exception.ResourceNotFoundException;
import com.tecser.backend.mapper.SellerMapper;
import com.tecser.backend.model.Seller;
import com.tecser.backend.repository.SellerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class SellerService {
    private final SellerRepository repository;
    private final SellerMapper mapper;
    public SellerService(SellerRepository repository, @Qualifier("sellerMapperImpl") SellerMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public List<SellerResponseDTO> findAll() {
        log.debug("Buscando todos los vendedors");
        return mapper.toRequestDtoList(repository.findAll());
    }

    @Transactional
    public SellerResponseDTO save(SellerRequestDTO sellerRequestDTO) {
        log.debug("Guardando nuevo vendedor: {}", sellerRequestDTO);
        Seller seller = mapper.toEntity(sellerRequestDTO);
        Seller savedSeller = repository.save(seller);
        log.info("vendedor guardado exitosamente con ID: {}", savedSeller.getId());
        return mapper.toResponseDto(savedSeller);
    }

    public SellerResponseDTO findById(Long id) {
        log.debug("Buscando vendedor por ID: {}", id);
        return repository.findById(id)
                .map(mapper::toResponseDto)
                .orElseThrow(() -> new ResourceNotFoundException("Vendedor no encontrado con ID: " + id));
    }

    @Transactional
    public void delete(Long id) {
        log.debug("Eliminando vendedor con ID: {}", id);

        if(!repository.existsById(id)) {
            throw new ResourceNotFoundException("Vendedor no encontrado con ID: " + id);
        }
        repository.deleteById(id);
        log.info("Vendedor eliminado exitosamente con ID: {}", id);
    }

    @Transactional
    public SellerResponseDTO update(SellerRequestDTO sellerRequestDTO, Long id) {
        log.debug("Actualizando vendedor con ID: {}", id);
        Seller seller = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vendedor no encontrado con ID: " + id));

        mapper.updateEntityFromRequestDto(sellerRequestDTO, seller);
        Seller updatedSeller = repository.save(seller);
        log.info("Vendedor actualizado exitosamente con ID: {}", id);
        return this.mapper.toResponseDto(updatedSeller);
    }

}
