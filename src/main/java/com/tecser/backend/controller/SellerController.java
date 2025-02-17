package com.tecser.backend.controller;

import com.tecser.backend.dto.request.SellerRequestDTO;
import com.tecser.backend.dto.response.SellerResponseDTO;
import com.tecser.backend.service.SellerService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/sellers")
public class SellerController {

    private final SellerService service;

    public SellerController(SellerService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<SellerResponseDTO>> getAll() {
        log.debug("REST request para obtener todos los vendedores");
        List<SellerResponseDTO> result = service.findAll();
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SellerResponseDTO> getById(@PathVariable Long id) {
        log.debug("REST request para obtener el vendedor con ID: {}", id);
        SellerResponseDTO result = service.findById(id);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping
    public ResponseEntity<SellerResponseDTO> save(@Valid @RequestBody SellerRequestDTO seller) {
       log.debug("REST request para guardar un nuevo vendedor: {}", seller);
       if (seller == null) {
           log.error("El vendedor no puede ser null");
           return ResponseEntity.badRequest().build();
       }
       SellerResponseDTO result = service.save(seller);
       return ResponseEntity.status(HttpStatus.CREATED)
               .body(result);

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("REST request para eliminar el vendedor con ID: {}", id);
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<SellerResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody SellerRequestDTO seller) {
       log.debug("REST request para actualizar el vendedor con ID: {}", id);

       if (seller == null) {
           log.error("El vendedor no puede ser null");
           return ResponseEntity.badRequest().build();
       }

       SellerResponseDTO result = service.update(seller, id);
       return ResponseEntity.ok().body(result);
    }

}