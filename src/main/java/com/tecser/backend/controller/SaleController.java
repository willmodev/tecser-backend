package com.tecser.backend.controller;

import com.tecser.backend.dto.request.SaleRequestDTO;
import com.tecser.backend.model.Sale;
import com.tecser.backend.service.SaleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sales")
public class SaleController {
    private final SaleService service;

    public SaleController(SaleService service) {
        this.service = service;
    }

    @GetMapping
    public List<Sale> getAll() {
        return service.findAll();
    }

    @PostMapping
    public SaleRequestDTO create(@RequestBody SaleRequestDTO saleRequestDTO) {
        return service.createSale(saleRequestDTO);
    }
}