package com.tecser.backend.service;

import com.tecser.backend.dto.request.SaleDetailRequestDTO;
import com.tecser.backend.dto.request.SaleRequestDTO;
import com.tecser.backend.dto.response.SaleResponseDTO;
import com.tecser.backend.exception.BusinessException;
import com.tecser.backend.exception.ResourceNotFoundException;
import com.tecser.backend.mapper.SaleDetailMapper;
import com.tecser.backend.mapper.SaleMapper;
import com.tecser.backend.model.Product;
import com.tecser.backend.model.Sale;
import com.tecser.backend.model.SaleDetail;
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
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Validated
@Slf4j
public class SaleService {
    private final SaleRepository saleRepository;
    private final ProductRepository productRepository;
    private final SellerRepository sellerRepository;
    private final SaleMapper saleMapper;
    private final SaleDetailMapper saleDetailMapper;

    public SaleService(SaleRepository saleRepository,
                       ProductRepository productRepository,
                       SellerRepository sellerRepository,
                       @Qualifier("saleMapperImpl") SaleMapper saleMapper,
                       SaleDetailMapper saleDetailMapper) {
        this.saleRepository = saleRepository;
        this.productRepository = productRepository;
        this.sellerRepository = sellerRepository;
        this.saleMapper = saleMapper;
        this.saleDetailMapper = saleDetailMapper;
    }

    public List<SaleResponseDTO> findAll() {
        log.info("Obteniendo todas las ventas");
        return saleRepository.findAll().stream()
                .map(saleMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public SaleResponseDTO createSale(@Valid SaleRequestDTO saleRequestDTO) {
        log.info("Iniciando proceso de creación de venta con número: {}", saleRequestDTO.getSaleNumber());

        // Validaciones iniciales
        validateSaleNumberUniqueness(saleRequestDTO.getSaleNumber());
        Seller seller = findAndValidateSeller(saleRequestDTO.getSellerId());

        // Obtener y validar productos
        List<Long> productIds = saleRequestDTO.getSaleDetails().stream()
                .map(SaleDetailRequestDTO::getProductId)
                .collect(Collectors.toList());

        Map<Long, Product> productsMap = findAndValidateProducts(productIds).stream()
                .collect(Collectors.toMap(Product::getId, Function.identity()));

        // Crear la venta base
        Sale sale = saleMapper.toEntity(saleRequestDTO);
        sale.setSeller(seller);

        // Procesar y validar detalles
        List<SaleDetail> details = saleDetailMapper.toEntityList(saleRequestDTO.getSaleDetails());
        BigDecimal totalAmount = BigDecimal.ZERO;

        log.info("Validando detalles de la venta {}", details);

        for (SaleDetail detail : details) {
            Product product = productsMap.get(detail.getProduct().getId());

            // Validar stock
            if (product.getStock() < detail.getQuantity()) {
                throw new BusinessException(
                        String.format("Stock insuficiente para el producto %s. Disponible: %d, Solicitado: %d",
                                product.getName(), product.getStock(), detail.getQuantity())
                );
            }

            // Configurar detalle
            detail.setSale(sale);
            detail.setProduct(product);
            detail.setSubtotal(detail.getUnitPrice()
                    .multiply(BigDecimal.valueOf(detail.getQuantity())));

            // Actualizar stock
            product.setStock(product.getStock() - detail.getQuantity());
            productRepository.save(product);

            // Acumular total
            totalAmount = totalAmount.add(detail.getSubtotal());
        }

        // Actualizar venta con detalles y total
        sale.setSaleDetails(details);
        sale.setTotalAmount(totalAmount);

        // Guardar y retornar
        Sale savedSale = saleRepository.save(sale);
        log.info("Venta creada exitosamente con ID: {}", savedSale.getId());

        return saleMapper.toResponseDto(savedSale);
    }

    private void validateSaleNumberUniqueness(String saleNumber) {
        if (saleRepository.findBySaleNumber(saleNumber).isPresent()) {
            throw new BusinessException("Ya existe una venta con el número: " + saleNumber);
        }
    }

    private Seller findAndValidateSeller(Long sellerId) {
        return sellerRepository.findById(sellerId)
                .orElseThrow(() -> new ResourceNotFoundException("Vendedor no encontrado con ID: " + sellerId));
    }

    private List<Product> findAndValidateProducts(List<Long> productIds) {
        List<Product> products = productRepository.findAllById(productIds);

        if (products.size() != productIds.size()) {
            List<Long> foundIds = products.stream()
                    .map(Product::getId)
                    .toList();
            List<Long> missingIds = productIds.stream()
                    .filter(id -> !foundIds.contains(id))
                    .toList();
            throw new ResourceNotFoundException("Productos no encontrados con IDs: " + missingIds);
        }

        validateProductsAvailability(products);
        return products;
    }

    private void validateProductsAvailability(List<Product> products) {
        List<String> unavailableProducts = products.stream()
                .filter(p -> !p.isAvailable())
                .map(Product::getName)
                .collect(Collectors.toList());

        if (!unavailableProducts.isEmpty()) {
            throw new BusinessException("Los siguientes productos no están disponibles: " +
                    String.join(", ", unavailableProducts));
        }
    }

//    private void validateTotalAmount(SaleRequestDTO saleRequestDTO) {
//        BigDecimal calculatedTotal = saleRequestDTO.getSaleDetails().stream()
//                .map(detail -> detail.getUnitPrice().multiply(BigDecimal.valueOf(detail.getQuantity())))
//                .reduce(BigDecimal.ZERO, BigDecimal::add);
//
//        if (!calculatedTotal.equals(saleRequestDTO.getTotalAmount())) {
//            throw new ValidationException("El monto total no coincide con la suma de los detalles");
//        }
//    }
}