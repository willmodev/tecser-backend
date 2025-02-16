package com.tecser.backend.controller;

import com.tecser.backend.dto.request.SaleRequestDTO;
import com.tecser.backend.dto.response.SaleResponseDTO;
import com.tecser.backend.service.SaleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sales")
@RequiredArgsConstructor
public class SaleController {
    private final SaleService service;

    @GetMapping
    public ResponseEntity<List<SaleResponseDTO>> getAll() {
        List<SaleResponseDTO> sales = service.findAll();
        return ResponseEntity.ok().body(sales);
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody SaleRequestDTO saleRequestDTO,
                                    BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            result.getFieldErrors().forEach(error ->
                    errors.put(error.getField(), error.getDefaultMessage())
            );
            return ResponseEntity.badRequest().body(errors);
        }

        SaleResponseDTO createdSale = service.createSale(saleRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSale);
    }
}