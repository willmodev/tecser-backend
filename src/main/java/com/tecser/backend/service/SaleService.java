package com.tecser.backend.service;



import com.tecser.backend.dto.request.SaleRequestDTO;
import com.tecser.backend.dto.response.SaleResponseDTO;
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

    public SaleService(SaleRepository saleRepository,
                       ProductRepository productRepository,
                       SellerRepository sellerRepository,
                       @Qualifier("saleMapperImpl") SaleMapper mapper) {
        this.saleRepository = saleRepository;
        this.productRepository = productRepository;
        this.sellerRepository = sellerRepository;
        this.mapper = mapper;
    }

    public List<Sale> findAll() {
        return saleRepository.findAll();
    }

    public SaleResponseDTO createSale(SaleRequestDTO saleRequestDTO) {
        validateSaleRequest(saleRequestDTO);

        Sale sale = mapper.toEntity(saleRequestDTO);

        // Cargar y validar el vendedor
        Seller seller = sellerRepository.findById(saleRequestDTO.getSellerId())
                .orElseThrow(() -> new RuntimeException("No se encontró el vendedor con ID: " + saleRequestDTO.getSellerId()));
        sale.setSeller(seller);

        // Cargar y validar los productos
        List<Product> products = productRepository.findAllById(saleRequestDTO.getProductIds());
        validateProducts(products, saleRequestDTO.getProductIds());
        sale.setProducts(products);

        Sale savedSale = saleRepository.save(sale);
        return mapper.toResponseDto(savedSale);
    }

    private void validateSaleRequest(SaleRequestDTO saleRequestDTO) {
        if (saleRequestDTO == null) {
            throw new IllegalArgumentException("La venta no puede ser nula");
        }
        if (saleRequestDTO.getSellerId() == null) {
            throw new IllegalArgumentException("El ID del vendedor es requerido");
        }
        if (saleRequestDTO.getProductIds() == null || saleRequestDTO.getProductIds().isEmpty()) {
            throw new IllegalArgumentException("Debe incluir al menos un producto en la venta");
        }
    }

    private void validateProducts(List<Product> foundProducts, List<Long> requestedProductIds) {
        if (foundProducts.size() != requestedProductIds.size()) {
            List<Long> foundProductIds = foundProducts.stream()
                    .map(Product::getId)
                    .toList();

            List<Long> notFoundProductIds = requestedProductIds.stream()
                    .filter(id -> !foundProductIds.contains(id))
                    .toList();

            throw new RuntimeException("No se encontraron los siguientes productos: " + notFoundProductIds);
        }
    }
}
