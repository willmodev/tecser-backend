package com.tecser.backend.controller;

import com.tecser.backend.dto.request.SellerRequestDTO;
import com.tecser.backend.dto.response.SellerResponseDTO;
import com.tecser.backend.service.SellerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sellers")
public class SellerController {

    private final SellerService service;

    public SellerController(SellerService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<SellerResponseDTO>> getAll() {
        List<SellerResponseDTO> sellers = service.findAll();
        return ResponseEntity.ok().body(sellers);
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody SellerRequestDTO sellerRequestDTO, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            result.getFieldErrors().forEach(error ->
                    errors.put(error.getField(), error.getDefaultMessage())
            );
            return ResponseEntity.badRequest().body(errors);
        }
        SellerResponseDTO savedSeller = service.save(sellerRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSeller);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SellerResponseDTO> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}