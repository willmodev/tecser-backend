package com.tecser.backend.controller;


import com.tecser.backend.dto.request.ProductRequestDTO;
import com.tecser.backend.dto.response.ProductResponseDTO;
import com.tecser.backend.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public List<ProductResponseDTO> getAll() {
        return service.findAll();
    }

    @PostMapping
    public ProductResponseDTO save(@RequestBody ProductRequestDTO product) {
        return service.save(product);
    }

    @GetMapping("/{id}")
    public ProductResponseDTO getById(@PathVariable Long id) {
        return service.findById(id).orElseThrow();
    }

    @DeleteMapping("/{id}")
    public void delete(Long id) {
        service.delete(id);
    }

    @PutMapping("/{id}")
    public ProductResponseDTO update(@RequestBody ProductRequestDTO product, @PathVariable Long id) {
        return  service.update(product, id);
    }
}
