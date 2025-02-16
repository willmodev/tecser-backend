package com.tecser.backend.dto.request;

import lombok.Data;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Data
public class SellerRequestDTO {
    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El formato del email no es válido")
    private String email;

    @NotBlank(message = "El teléfono es obligatorio")
    private String phone;

    @NotBlank(message = "El documento de identidad es obligatorio")
    private String documentId;

    private Boolean isActive;

}