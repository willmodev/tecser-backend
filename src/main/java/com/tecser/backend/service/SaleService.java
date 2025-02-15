package com.tecser.backend.service;



import com.tecser.backend.dto.request.SaleRequestDTO;
import com.tecser.backend.mapper.SaleMapper;
import com.tecser.backend.model.Product;
import com.tecser.backend.model.Sale;
import com.tecser.backend.model.Seller;
import com.tecser.backend.repository.ProductRepository;
import com.tecser.backend.repository.SaleRepository;
import com.tecser.backend.repository.SellerRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaleService {
    private final SaleRepository saleRepository;
    private final ProductRepository productRepository;
    private final SellerRepository sellerRepository;
    private final SaleMapper mapper;

    public SaleService(SaleRepository saleRepository, ProductRepository productRepository, SellerRepository sellerRepository, @Qualifier("saleMapperImpl") SaleMapper mapper) {
        this.saleRepository = saleRepository;
        this.productRepository = productRepository;
        this.sellerRepository = sellerRepository;
        this.mapper = mapper;
    }

    public List<Sale> findAll() {
        return saleRepository.findAll();
    }

    public SaleRequestDTO createSale(SaleRequestDTO saleRequestDTO) {
        Sale sale = mapper.toEntity(saleRequestDTO);

        // Load and set Product and Seller manually
        Product product = productRepository.findById(saleRequestDTO.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
        Seller seller = sellerRepository.findById(saleRequestDTO.getSellerId())
                .orElseThrow(() -> new RuntimeException("Seller not found"));

        sale.setProduct(product);
        sale.setSeller(seller);

        Sale savedSale = saleRepository.save(sale);
        return mapper.toDto(savedSale);
    }
}