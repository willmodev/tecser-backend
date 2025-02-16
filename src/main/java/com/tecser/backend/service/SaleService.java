package com.tecser.backend.service;

import com.tecser.backend.dto.request.SaleRequestDTO;
import com.tecser.backend.dto.response.SaleResponseDTO;
import com.tecser.backend.exception.BusinessException;
import com.tecser.backend.exception.ResourceNotFoundException;
import com.tecser.backend.mapper.SaleMapper;
import com.tecser.backend.model.Product;
import com.tecser.backend.model.Sale;
import com.tecser.backend.model.Seller;
import com.tecser.backend.repository.ProductRepository;
import com.tecser.backend.repository.SaleRepository;
import com.tecser.backend.repository.SellerRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Validated
@Slf4j
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

    public List<SaleResponseDTO> findAll() {
        log.info("Obteniendo todas las ventas");
        return saleRepository.findAll().stream()
                .map(mapper::toResponseDto)
                .toList();
    }

    @Transactional
    public SaleResponseDTO createSale(@Valid SaleRequestDTO saleRequestDTO) {
        log.info("Iniciando proceso de creación de venta con número: {}", saleRequestDTO.getSaleNumber());

        // Validar si ya existe una venta con el mismo número
        validateSaleNumberUniqueness(saleRequestDTO.getSaleNumber());

        Sale sale = mapper.toEntity(saleRequestDTO);

        // Cargar y validar el vendedor
        Seller seller = findAndValidateSeller(saleRequestDTO.getSellerId());
        sale.setSeller(seller);

        // Cargar y validar los productos
        List<Product> products = findAndValidateProducts(saleRequestDTO.getProductIds());
        sale.setProducts(products);


        Sale savedSale = saleRepository.save(sale);
        log.info("Venta creada exitosamente con ID: {}", savedSale.getId());

        return mapper.toResponseDto(savedSale);
    }

    private void validateSaleNumberUniqueness(String saleNumber) {
        Optional<Sale> existingSale = saleRepository.findBySaleNumber(saleNumber);
        if (existingSale.isPresent()) {
            log.error("Intento de crear venta con número duplicado: {}", saleNumber);
            throw new BusinessException("Ya existe una venta con el número: " + saleNumber);
        }
    }

    private Seller findAndValidateSeller(Long sellerId) {
        return sellerRepository.findById(sellerId)
                .orElseThrow(() -> {
                    log.error("Vendedor no encontrado con ID: {}", sellerId);
                    return new ResourceNotFoundException("Vendedor", "id", sellerId);
                });
    }

    private List<Product> findAndValidateProducts(List<Long> productIds) {
        List<Product> foundProducts = productRepository.findAllById(productIds);

        if (foundProducts.size() != productIds.size()) {
            List<Long> foundProductIds = foundProducts.stream()
                    .map(Product::getId)
                    .toList();

            List<Long> notFoundProductIds = productIds.stream()
                    .filter(id -> !foundProductIds.contains(id))
                    .toList();

            log.error("Productos no encontrados: {}", notFoundProductIds);
            throw new ResourceNotFoundException("No se encontraron los siguientes productos: " + notFoundProductIds);
        }

        validateProductsAvailability(foundProducts);
        return foundProducts;
    }

    private void validateProductsAvailability(List<Product> products) {
        List<Product> unavailableProducts = products.stream()
                .filter(product -> !product.isAvailable())
                .toList();

        if (!unavailableProducts.isEmpty()) {
            List<String> unavailableProductNames = unavailableProducts.stream()
                    .map(Product::getName)
                    .toList();

            log.error("Productos no disponibles: {}", unavailableProductNames);
            throw new BusinessException("Los siguientes productos no están disponibles: " + unavailableProductNames);
        }
    }
}