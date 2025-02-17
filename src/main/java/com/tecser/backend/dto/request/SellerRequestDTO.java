package com.tecser.backend.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class SellerRequestDTO {
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    private String name;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El formato del email no es válido")
    @Size(max = 100, message = "El email no puede exceder los 100 caracteres")
    private String email;

    @NotBlank(message = "El teléfono es obligatorio")
    @Pattern(regexp = "^\\+?[0-9]{6,15}$", message = "El formato del teléfono no es válido")
    private String phone;

    @NotBlank(message = "El documento de identidad es obligatorio")
    @Pattern(regexp = "^[0-9]{8,12}$", message = "El documento debe tener entre 8 y 12 dígitos")
    private String documentNumber;

    @NotNull(message = "El estado es obligatorio")
    private Boolean isActive = true;
}