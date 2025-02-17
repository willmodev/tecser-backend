package com.tecser.backend.controller;

import com.tecser.backend.dto.request.ProductRequestDTO;
import com.tecser.backend.dto.response.ProductResponseDTO;
import com.tecser.backend.service.ProductService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAll() {
        log.debug("REST request para obtener todos los productos");
        List<ProductResponseDTO> result = service.findAll();
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getById(@PathVariable Long id) {
        log.debug("REST request para obtener el producto con ID: {}", id);
        ProductResponseDTO result = service.findById(id);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ProductResponseDTO> save(@Valid @RequestBody ProductRequestDTO product) {
        log.debug("REST request para guardar un nuevo producto: {}", product);
        if (product == null) {
            log.error("El producto no puede ser null");
            return ResponseEntity.badRequest().build();
        }

        ProductResponseDTO result = service.save(product);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequestDTO product) {
        log.debug("REST request para actualizar el producto ID: {}, Data: {}", id, product);
        if (product == null) {
            log.error("El producto no puede ser null");
            return ResponseEntity.badRequest().build();
        }

        ProductResponseDTO result = service.update(product, id);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("REST request para eliminar el producto ID: {}", id);
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}