package com.tecser.backend.dto.response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SellerResponseDTO {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String documentNumber;
    private LocalDateTime registrationDate;
    private Boolean isActive;
}