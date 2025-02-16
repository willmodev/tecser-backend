package com.tecser.backend.dto.response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SellerResponseDTO {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String documentId;
    private LocalDateTime registrationDate;
    private Boolean isActive;
}