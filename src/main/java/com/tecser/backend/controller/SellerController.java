package com.tecser.backend.controller;

import com.tecser.backend.dto.request.SellerRequestDTO;
import com.tecser.backend.dto.response.SellerResponseDTO;
import com.tecser.backend.service.SellerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sellers")
public class SellerController {

    private final SellerService service;

    public SellerController(SellerService service) {
        this.service = service;
    }

    @GetMapping
    public List<SellerResponseDTO> getAll() {
        return service.findAll();
    }

    @PostMapping
    public SellerResponseDTO save(@RequestBody SellerRequestDTO sellerRequestDTO) {
        return service.save(sellerRequestDTO);
    }

    @GetMapping("/{id}")
    public SellerResponseDTO getById(Long id) {
        return service.findById(id).orElseThrow();
    }

    @DeleteMapping("/{id}")
    public void delete(Long id) {
        service.delete(id);
    }
}
